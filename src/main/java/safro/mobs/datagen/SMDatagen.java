package safro.mobs.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import safro.mobs.registry.ItemRegistry;

import java.util.stream.Stream;

public class SMDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
    }

    static class ModelProvider extends FabricModelProvider {

        public ModelProvider(FabricDataOutput dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator gen) {
            ItemRegistry.SPAWN_EGGS.forEach(item -> gen.registerParentedItemModel(item, ModelIds.getMinecraftNamespacedItem("template_spawn_egg")));
        }

        @Override
        public void generateItemModels(ItemModelGenerator gen) {
            Stream<Item> stream = Stream.of(ItemRegistry.FROG_EYE, ItemRegistry.HAMMER_SHARD);
            stream.forEach(item -> gen.register(item, Models.GENERATED));
        }
    }
}
