package safro.mobs.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;
import safro.mobs.entity.FairyEntity;

import java.util.EnumSet;
import java.util.List;

public class FlyingEscapeGoal<T extends LivingEntity> extends Goal {
    private final float distance;
    private final Class<T> targetAvoid;
    protected FairyEntity fairy;
    protected T livingEntity;
    private Vec3d hidePlace;

    public FlyingEscapeGoal(FairyEntity fairy, Class<T> targetAvoid, float distance) {
        this.fairy = fairy;
        this.targetAvoid = targetAvoid;
        this.distance = distance;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        List<T> list = this.fairy.getWorld().getNonSpectatingEntities(this.targetAvoid, this.fairy.getBoundingBox().expand(this.distance, 3.0D, this.distance));

        if (!list.isEmpty()) {
            this.livingEntity = list.get(0);
            if (livingEntity != null) {
                Vec3d Vec3d = NoPenaltyTargeting.findFrom(this.fairy, 16, 4, new Vec3d(this.livingEntity.getX(), this.livingEntity.getY(), this.livingEntity.getZ()));

                if (Vec3d == null) {
                    return false;
                } else {
                    Vec3d = Vec3d.add(0, 1, 0);
                    this.fairy.getMoveControl().moveTo(Vec3d.x, Vec3d.y, Vec3d.z, 1D);
                    this.fairy.getLookControl().lookAt(Vec3d.x, Vec3d.y, Vec3d.z, 180.0F, 20.0F);
                    hidePlace = Vec3d;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shouldContinue() {
        return hidePlace != null && this.fairy.squaredDistanceTo(hidePlace.add(0.5, 0.5, 0.5)) < 2;
    }

    public void start() {
        this.fairy.getMoveControl().moveTo(hidePlace.x, hidePlace.y, hidePlace.z, 1D);
        this.fairy.getLookControl().lookAt(hidePlace.x, hidePlace.y, hidePlace.z, 180.0F, 20.0F);
    }

    public void stop() {
        this.livingEntity = null;
    }
}
