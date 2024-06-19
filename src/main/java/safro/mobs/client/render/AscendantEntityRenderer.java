package safro.mobs.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import safro.mobs.client.model.AscendantEntityModel;
import safro.mobs.entity.AscendantEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AscendantEntityRenderer extends GeoEntityRenderer<AscendantEntity> {

    public AscendantEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AscendantEntityModel());
        this.scaleWidth = 1.5F;
        this.scaleHeight = 1.5F;
    }
}
