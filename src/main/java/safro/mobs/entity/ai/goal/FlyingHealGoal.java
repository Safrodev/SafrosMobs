package safro.mobs.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;
import safro.mobs.entity.FairyEntity;

import java.util.EnumSet;
import java.util.List;

public class FlyingHealGoal extends Goal {
    private final float range;
    protected final FairyEntity fairy;
    protected LivingEntity target;
    protected Vec3d targetPos;

    public FlyingHealGoal(FairyEntity fairy, float range) {
        this.fairy = fairy;
        this.range = range;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        if (this.fairy.getHealCooldown() > 0) {
            return false;
        }

        List<LivingEntity> list = this.fairy.getWorld().getNonSpectatingEntities(LivingEntity.class, this.fairy.getBoundingBox().expand(this.range, 8.0D, this.range));
        list = list.stream().filter(entity -> !(entity instanceof HostileEntity)).filter(entity -> entity.getHealth() < entity.getMaxHealth()).filter(entity -> entity != this.fairy).toList();

        if (!list.isEmpty()) {
            this.target = list.get(0);
            if (this.target != null) {
                Vec3d vec3d = NoPenaltyTargeting.findFrom(this.fairy, 20, 6, new Vec3d(this.target.getX(), this.target.getY(), this.target.getZ()));

                if (vec3d == null) {
                    return false;
                } else {
                    vec3d = vec3d.add(0, 1, 0);
                    this.fairy.getMoveControl().moveTo(vec3d.x, vec3d.y, vec3d.z, 1D);
                    this.fairy.getLookControl().lookAt(vec3d.x, vec3d.y, vec3d.z, 180.0F, 20.0F);
                    this.targetPos = vec3d;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shouldContinue() {
        return this.targetPos != null && this.target != null && this.target.isAlive() && this.fairy.squaredDistanceTo(this.target) > 2;
    }

    public void start() {
        this.fairy.getMoveControl().moveTo(this.targetPos.x, this.targetPos.y, this.targetPos.z, 1D);
        this.fairy.getLookControl().lookAt(this.targetPos.x, this.targetPos.y, this.targetPos.z, 180.0F, 20.0F);
    }

    public void tick() {
        this.fairy.getMoveControl().moveTo(this.targetPos.x, this.targetPos.y, this.targetPos.z, 1D);
        this.fairy.getLookControl().lookAt(this.targetPos.x, this.targetPos.y, this.targetPos.z, 180.0F, 20.0F);
    }

    public void stop() {
        if (this.fairy.squaredDistanceTo(this.target) <= 3) {
            this.target.heal(4.0F);
            this.fairy.getWorld().sendEntityStatus(this.fairy, (byte) 13);
        }
        this.target = null;
        this.fairy.setHealCooldown(200);
    }
}
