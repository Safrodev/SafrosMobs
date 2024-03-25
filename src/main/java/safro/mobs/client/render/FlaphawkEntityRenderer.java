package safro.mobs.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import safro.mobs.client.model.FlaphawkEntityModel;
import safro.mobs.entity.FlaphawkEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FlaphawkEntityRenderer extends GeoEntityRenderer<FlaphawkEntity> {

    public FlaphawkEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FlaphawkEntityModel());
    }
}
