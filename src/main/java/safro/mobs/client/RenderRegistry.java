package safro.mobs.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import safro.mobs.client.render.*;
import safro.mobs.registry.EntityRegistry;

public class RenderRegistry {

    public static void init() {
        EntityRendererRegistry.register(EntityRegistry.GOBLIN_GRUNT, GoblinGruntEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.PUMP_FROG, PumpFrogEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.FLAPHAWK, FlaphawkEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MOCKER, MockerEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LEVIATHAN, LeviathanEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.THUNDERBOLT, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.THUNDIZARD, ThundizardEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.FAIRY, FairyEntityRenderer::new);
    }
}
