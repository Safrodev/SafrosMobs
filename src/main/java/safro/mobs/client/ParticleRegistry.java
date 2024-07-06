package safro.mobs.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SpitParticle;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import safro.mobs.SafrosMobs;

public class ParticleRegistry {
    public static final SimpleParticleType THUNDERBOLT = register("thunderbolt", true);

    private static SimpleParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(SafrosMobs.MODID, name), FabricParticleTypes.simple(alwaysShow));
    }

    public static void init() {
        ParticleFactoryRegistry.getInstance().register(THUNDERBOLT, SpitParticle.Factory::new);
    }
}
