package safro.mobs.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.mobs.CommonEvents;
import safro.mobs.registry.BlockItemRegistry;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void smPlayerAttack(Entity target, CallbackInfo ci, @Local(ordinal = 0) float f) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        CommonEvents.onPlayerAttack(player, target, f);
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void blazeRunnerJump(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getEquippedStack(EquipmentSlot.FEET).isOf(BlockItemRegistry.BLAZE_RUNNERS)) {
            Vec3d vec3d = player.getVelocity();
            player.setVelocity(vec3d.x, this.getJumpVelocity() * 1.5F, vec3d.z);
            if (this.isSprinting()) {
                float f = this.getYaw() * 0.017453292F;
                this.setVelocity(this.getVelocity().add((-MathHelper.sin(f) * 0.8F), 0.0, (MathHelper.cos(f) * 0.8F)));
            }
            player.velocityDirty = true;

            player.incrementStat(Stats.JUMP);
            if (player.isSprinting()) {
                player.addExhaustion(0.2F);
            } else {
                player.addExhaustion(0.05F);
            }
            ci.cancel();
        }
    }
}
