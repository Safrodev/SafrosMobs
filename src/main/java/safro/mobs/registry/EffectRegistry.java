package safro.mobs.registry;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import safro.mobs.SafrosMobs;
import safro.mobs.api.SMStatusEffect;

public class EffectRegistry {
    public static final StatusEffect STUNNED = register("stunned", StatusEffectCategory.HARMFUL, 0xFFF828)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "e06f82ba-d8e6-4c51-a523-441d830e9241", -1.0D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, "fb3bb3b1-002c-460a-96a2-885ea2687c6f", -1.0D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    private static StatusEffect register(String name, StatusEffectCategory category, int color) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(SafrosMobs.MODID, name), new SMStatusEffect(category, color));
    }

    public static void init() {}
}
