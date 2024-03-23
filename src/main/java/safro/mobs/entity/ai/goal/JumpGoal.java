package safro.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import safro.mobs.entity.PumpFrogEntity;

import java.util.EnumSet;

public class JumpGoal extends Goal {
    private final PumpFrogEntity entity;

    public JumpGoal(PumpFrogEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return this.entity.getJumpCooldown() <= 0;
    }

    @Override
    public boolean shouldContinue() {
        return !this.entity.isOnGround();
    }

    @Override
    public void tick() {
        this.entity.setNoDrag(true);
        if (this.entity.isOnGround()) {
            this.entity.setYaw(this.entity.bodyYaw);
            Vec3d vec3d = this.entity.getRotationVector();
            double m = MathHelper.nextDouble(this.entity.getRandom(), 0.05D, 0.07D);
            this.entity.addVelocity(vec3d.x * m + (vec3d.x - this.entity.getVelocity().x), 0.3D, vec3d.z * m + (vec3d.z - this.entity.getVelocity().z));
            this.entity.getWorld().playSoundFromEntity(null, this.entity, SoundEvents.ENTITY_FROG_LONG_JUMP, SoundCategory.NEUTRAL, 2.5F, 1.0F);
        }
    }

    @Override
    public void stop() {
        this.entity.setNoDrag(false);
        this.entity.setJumpCooldown(160);
    }
}
