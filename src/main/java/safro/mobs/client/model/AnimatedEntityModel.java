package safro.mobs.client.model;


import net.minecraft.util.Identifier;
import safro.mobs.SafrosMobs;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class AnimatedEntityModel<T extends GeoAnimatable> extends DefaultedEntityGeoModel<T> {

    public AnimatedEntityModel(String modelName) {
        super(new Identifier(SafrosMobs.MODID, modelName), false);
    }
}
