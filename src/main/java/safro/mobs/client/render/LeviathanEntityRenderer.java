package safro.mobs.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import safro.mobs.client.model.LeviathanEntityModel;
import safro.mobs.entity.LeviathanEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LeviathanEntityRenderer extends GeoEntityRenderer<LeviathanEntity> {

    public LeviathanEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LeviathanEntityModel());
    }
}
