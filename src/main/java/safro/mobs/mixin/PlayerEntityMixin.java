package safro.mobs.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.mobs.event.CommonEvents;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "attack", at = @At("TAIL"))
    private void smPlayerAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (target.isAttackable() && target.handleAttack(player)) {
            float f = (float)player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            float g;
            if (target instanceof LivingEntity) {
                g = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), ((LivingEntity)target).getGroup());
            } else {
                g = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), EntityGroup.DEFAULT);
            }
            float h = player.getAttackCooldownProgress(0.5F);

            f *= 0.2F + h * h * 0.8F;
            g *= h;
            if (f > 0.0F || g > 0.0F) {
                boolean bl3 = (h > 0.9F) && player.fallDistance > 0.0F && !player.isOnGround() && !player.isClimbing() && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.BLINDNESS) && !player.hasVehicle() && target instanceof LivingEntity;
                bl3 = bl3 && !player.isSprinting();
                if (bl3) {
                    f *= 1.5F;
                }
            }
            f += g;

            CommonEvents.onPlayerAttack(player, target, f);
        }
    }
}
