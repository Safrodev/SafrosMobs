package safro.mobs.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import safro.mobs.SafrosMobs;
import safro.mobs.entity.MockerEntity;

import java.util.Optional;

public class MockerEntityRenderer extends LivingEntityRenderer<MockerEntity, PlayerEntityModel<MockerEntity>> {
    private static final Identifier TEXTURE = new Identifier(SafrosMobs.MODID, "textures/entity/mocker.png");

    public MockerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER), false), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new ArmorEntityModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new ArmorEntityModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
        this.addFeature(new PlayerHeldItemFeatureRenderer(this, ctx.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer(this, ctx.getModelLoader()));
    }

    @Override
    public Identifier getTexture(MockerEntity entity) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (entity.getPlayerId().isEmpty() || world == null) {
            return TEXTURE;
        }

        Optional<AbstractClientPlayerEntity> player = world.getPlayers().stream().filter(p -> p.getUuid().equals(entity.getPlayerId().get())).findFirst();
        return player.isPresent() ? player.get().getSkinTexture() : TEXTURE;
    }

    @Override
    protected boolean hasLabel(MockerEntity entity) {
        return entity.hasCustomName() && super.hasLabel(entity);
    }
}
