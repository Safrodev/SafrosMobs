package safro.mobs.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import safro.mobs.compat.TrinketsCompat;
import safro.mobs.registry.BlockItemRegistry;
import safro.mobs.registry.EntityRegistry;

import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected abstract boolean shouldSwimInFluids();

    @Inject(method = "getHandSwingDuration", at = @At("HEAD"), cancellable = true)
    private void getSMHandSwing(CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getType().equals(EntityRegistry.GOBLIN_GRUNT)) {
            cir.setReturnValue(20);
        }
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V", shift = At.Shift.AFTER))
    private void glideBeltBoost(Vec3d movementInput, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity player && this.hasBelt(player)) {
            FluidState fluidState = entity.getWorld().getFluidState(entity.getBlockPos());
            if (entity.isTouchingWater() && this.shouldSwimInFluids() && !entity.canWalkOnFluid(fluidState)) {
                float boost = 0.02F + (entity.getMovementSpeed() - 0.02F);
                entity.updateVelocity(boost, movementInput);
                entity.move(MovementType.SELF, entity.getVelocity());
                Vec3d vec3d = entity.getVelocity();
                if (entity.horizontalCollision && entity.isClimbing()) {
                    vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
                }
                entity.setVelocity(vec3d.multiply(0.54600006F, 0.800000011920929, 0.54600006F));
            }
        }
    }

    @Unique
    private boolean hasBelt(PlayerEntity player) {
        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            if (TrinketsCompat.hasTrinket(player, BlockItemRegistry.GLIDE_BELT)) {
                return true;
            }
        }
        return player.getInventory().containsAny(Set.of(BlockItemRegistry.GLIDE_BELT));
    }
}
