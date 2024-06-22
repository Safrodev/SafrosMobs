package safro.mobs.entity.ai.goal;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import safro.mobs.entity.FairyEntity;

import java.util.EnumSet;
import java.util.List;

public class FlyingHealGoal extends Goal {
    private final float range;
    protected final FairyEntity fairy;
    private final EntityNavigation navigation;
    protected LivingEntity target;

    public FlyingHealGoal(FairyEntity fairy, float range) {
        this.fairy = fairy;
        this.navigation = fairy.getNavigation();
        this.range = range;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        if (this.fairy.getHealCooldown() > 0) {
            return false;
        }

        List<LivingEntity> list = this.fairy.getWorld().getEntitiesByClass(LivingEntity.class, this.fairy.getBoundingBox().expand(this.range, 8.0D, this.range), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
        list = list.stream().filter(this::isValid).toList();

        if (!list.isEmpty()) {
            this.target = list.get(0);
            return true;
        }
        return false;
    }

    public boolean shouldContinue() {
        return !this.navigation.isIdle() && this.target != null && this.target.isAlive() && this.fairy.squaredDistanceTo(this.target) > 4.0D;
    }

    public void tick() {
        this.navigation.startMovingTo(this.target, 1.2D);
        this.fairy.getLookControl().lookAt(this.target, 180.0F, 20.0F);
    }

    public void stop() {
        this.navigation.stop();
        if (this.fairy.squaredDistanceTo(this.target) <= 4.25D) {
            this.target.heal(4.0F);
            this.fairy.getWorld().sendEntityStatus(this.fairy, (byte) 13);
        }
        this.target = null;
        this.fairy.setHealCooldown(200);
    }

    private boolean isValid(LivingEntity entity) {
        if (entity instanceof HostileEntity || entity instanceof FairyEntity || entity.getGroup().equals(EntityGroup.UNDEAD)) {
            return false;
        }
        return entity.isAlive() && entity.getHealth() < entity.getMaxHealth();
    }
}
