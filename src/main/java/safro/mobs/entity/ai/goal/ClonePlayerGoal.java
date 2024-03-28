package safro.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import safro.mobs.entity.MockerEntity;

public class ClonePlayerGoal extends Goal {
    private final MockerEntity entity;

    public ClonePlayerGoal(MockerEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canStart() {
        return !this.entity.hasCloned() && this.entity.getTarget() instanceof PlayerEntity;
    }

    @Override
    public void start() {
        if (this.entity.getTarget() instanceof PlayerEntity player) {
            this.entity.clonePlayer(player);
            this.entity.getWorld().playSound(null, this.entity.getBlockPos(), SoundEvents.ENTITY_WITCH_CELEBRATE, SoundCategory.HOSTILE, 1.0F, 0.7F);
        }
    }
}
