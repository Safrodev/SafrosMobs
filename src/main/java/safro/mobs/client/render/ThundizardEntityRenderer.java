package safro.mobs.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import safro.mobs.client.model.ThundizardEntityModel;
import safro.mobs.entity.ThundizardEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ThundizardEntityRenderer extends GeoEntityRenderer<ThundizardEntity> {

    public ThundizardEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ThundizardEntityModel());
    }
}
