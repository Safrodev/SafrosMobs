package safro.mobs.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SpitParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import safro.mobs.SafrosMobs;

public class ParticleRegistry {
    public static final DefaultParticleType THUNDERBOLT = register("thunderbolt", true);

    private static DefaultParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(SafrosMobs.MODID, name), FabricParticleTypes.simple(alwaysShow));
    }

    public static void init() {
        ParticleFactoryRegistry.getInstance().register(THUNDERBOLT, SpitParticle.Factory::new);
    }
}
