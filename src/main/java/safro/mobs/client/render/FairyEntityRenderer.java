package safro.mobs.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import safro.mobs.client.model.FairyEntityModel;
import safro.mobs.entity.FairyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FairyEntityRenderer extends GeoEntityRenderer<FairyEntity> {

    public FairyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FairyEntityModel());
    }
}
