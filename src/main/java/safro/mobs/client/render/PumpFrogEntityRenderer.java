package safro.mobs.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import safro.mobs.client.model.PumpFrogEntityModel;
import safro.mobs.entity.PumpFrogEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PumpFrogEntityRenderer extends GeoEntityRenderer<PumpFrogEntity> {

    public PumpFrogEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PumpFrogEntityModel());
    }

    public float getMotionAnimThreshold(PumpFrogEntity animatable) {
        return 0.01F;
    }
}
