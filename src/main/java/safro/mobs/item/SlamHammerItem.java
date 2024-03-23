package safro.mobs.item;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class SlamHammerItem extends SwordItem {

    public SlamHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (super.postHit(stack, target, attacker)) {
            target.addVelocity(0.0D, -5.0D, 0.0D);
            if (target instanceof PlayerEntity player) {
                player.setPose(EntityPose.SWIMMING);
            } else {
                target.setPose(EntityPose.DYING);
            }
            return true;
        }
        return false;
    }
}
