package safro.mobs.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.projectile.SmallFireballEntity;
import safro.mobs.entity.AscendantEntity;

import java.util.EnumSet;

public class FirestormGoal extends Goal {
    private final AscendantEntity ascendant;
    private int fireballCooldown;
    private int targetNotVisibleTicks;

    public FirestormGoal(AscendantEntity entity) {
        this.ascendant = entity;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.ascendant.getTarget();
        return livingEntity != null && livingEntity.isAlive() && this.ascendant.canTarget(livingEntity);
    }

    public void start() {
        this.fireballCooldown = 0;
        this.ascendant.setAttacking(true);
    }

    public void stop() {
        this.targetNotVisibleTicks = 0;
        this.ascendant.setAttacking(false);
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        --this.fireballCooldown;
        LivingEntity livingEntity = this.ascendant.getTarget();
        if (livingEntity != null) {
            boolean bl = this.ascendant.getVisibilityCache().canSee(livingEntity);
            if (bl) {
                this.targetNotVisibleTicks = 0;
            } else {
                ++this.targetNotVisibleTicks;
            }

            double d = this.ascendant.squaredDistanceTo(livingEntity);
            if (d < 4.0) {
                if (!bl) {
                    return;
                }

                if (this.fireballCooldown <= 0) {
                    this.fireballCooldown = 20;
                    this.ascendant.tryAttack(livingEntity);
                    livingEntity.setOnFireFor(5);
                }

                this.ascendant.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
            } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                double e = livingEntity.getX() - this.ascendant.getX();
                double f = livingEntity.getBodyY(0.5) - this.ascendant.getBodyY(0.5);
                double g = livingEntity.getZ() - this.ascendant.getZ();

                if (this.fireballCooldown <= 0) {
                    double h = Math.sqrt(Math.sqrt(d)) * 0.5;
                    if (!this.ascendant.isSilent()) {
                        this.ascendant.getWorld().syncWorldEvent(null, 1018, this.ascendant.getBlockPos(), 0);
                    }

                    for (int i = 0; i < 6; i++) {
                        float offset = i % 2 == 0 ? 0.5F : -0.5F;
                        SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.ascendant.getWorld(), this.ascendant, this.ascendant.getRandom().nextTriangular(e, 2.297 * h), f, this.ascendant.getRandom().nextTriangular(g, 2.297 * h));
                        smallFireballEntity.setPosition(smallFireballEntity.getX() + offset, this.ascendant.getBodyY(0.5), smallFireballEntity.getZ());
                        this.ascendant.getWorld().spawnEntity(smallFireballEntity);
                    }
                    this.fireballCooldown = 60;
                }

                this.ascendant.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
            } else if (this.targetNotVisibleTicks < 5) {
                this.ascendant.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
            }

            super.tick();
        }
    }

    private double getFollowRange() {
        return this.ascendant.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
    }
}
