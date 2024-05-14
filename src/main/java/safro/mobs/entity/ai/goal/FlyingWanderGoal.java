package safro.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import safro.mobs.entity.FairyEntity;

import java.util.EnumSet;
import net.minecraft.util.math.random.Random;

public class FlyingWanderGoal extends Goal {
    private BlockPos target;
    private final FairyEntity fairy;
    private final Random random;

    public FlyingWanderGoal(FairyEntity e) {
        this.fairy = e;
        this.random = e.getRandom();
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        target = FairyEntity.getPosFromGround(this.fairy, this.fairy.getWorld(), this.fairy.getX() + this.random.nextInt(15) - 7, this.fairy.getZ() + this.random.nextInt(15) - 7);
        return this.fairy.fedTicks <= 0 && isDirectPathBetweenPoints(this.fairy.getBlockPos(), target) && !this.fairy.getMoveControl().isMoving() && this.random.nextInt(4) == 0;
    }

    protected boolean isDirectPathBetweenPoints(BlockPos posVec31, BlockPos posVec32) {
        return this.fairy.getWorld().raycast(new RaycastContext(new Vec3d(posVec31.getX() + 0.5D, posVec31.getY() + 0.5D, posVec31.getZ() + 0.5D), new Vec3d(posVec32.getX() + 0.5D, posVec32.getY() + (double) this.fairy.getHeight() * 0.5D, posVec32.getZ() + 0.5D), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this.fairy)).getType() == HitResult.Type.MISS;
    }

    public boolean shouldContinue() {
        return false;
    }

    public void tick() {
        if (!isDirectPathBetweenPoints(this.fairy.getBlockPos(), target)) {
            target = FairyEntity.getPosFromGround(this.fairy, this.fairy.getWorld(), this.fairy.getX() + this.random.nextInt(15) - 7, this.fairy.getZ() + this.random.nextInt(15) - 7);
        }
        if (this.fairy.getWorld().isAir(target)) {
            this.fairy.getMoveControl().moveTo((double) target.getX() + 0.5D, (double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 0.25D);
            if (this.fairy.getTarget() == null) {
                this.fairy.getLookControl().lookAt((double) target.getX() + 0.5D, (double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 180.0F, 20.0F);
            }
        }
    }
}
