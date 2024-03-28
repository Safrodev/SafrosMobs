package safro.mobs.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import safro.mobs.registry.EntityRegistry;

public class RenderRegistry {

    public static void init() {
        EntityRendererRegistry.register(EntityRegistry.GOBLIN_GRUNT, GoblinGruntEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.PUMP_FROG, PumpFrogEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.FLAPHAWK, FlaphawkEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MOCKER, MockerEntityRenderer::new);
    }
}
