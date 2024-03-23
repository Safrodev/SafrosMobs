package safro.mobs.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import safro.mobs.client.model.GoblinGruntEntityModel;
import safro.mobs.entity.GoblinGruntEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GoblinGruntEntityRenderer extends GeoEntityRenderer<GoblinGruntEntity> {

    public GoblinGruntEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GoblinGruntEntityModel());
    }

    public float getMotionAnimThreshold(GoblinGruntEntity animatable) {
        return 0.01F;
    }
}
