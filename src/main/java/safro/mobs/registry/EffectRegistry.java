package safro.mobs.registry;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import safro.mobs.SafrosMobs;
import safro.mobs.api.SMStatusEffect;

public class EffectRegistry {
    public static final RegistryEntry<StatusEffect> STUNNED = register("stunned", new SMStatusEffect(StatusEffectCategory.HARMFUL, 0xFFF828)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.of(SafrosMobs.MODID, "effect.stunned.movement"), -1.0D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, Identifier.of(SafrosMobs.MODID, "effect.stunned.flying"), -1.0D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    private static RegistryEntry<StatusEffect> register(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(SafrosMobs.MODID, name), effect);
    }

    public static void init() {}
}
