package safro.mobs.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import safro.mobs.SafrosMobs;
import safro.mobs.item.SlamHammerItem;
import safro.saflib.registry.BaseBlockItemRegistry;

import java.util.ArrayList;

public class ItemRegistry extends BaseBlockItemRegistry {
    static { MODID = SafrosMobs.MODID; }
    public static final ArrayList<Item> SPAWN_EGGS = new ArrayList<>();

    public static final Item SLAM_HAMMER = register("slam_hammer", new SlamHammerItem(ToolMaterials.IRON, 6, -3.1F, settings()));

    public static final Item FROG_EYE = register("frog_eye", new Item(settings()));
    public static final Item HAMMER_SHARD = register("hammer_shard", new Item(settings()));
    public static final Item LEECHING_SPORE = register("leeching_spore", new Item(settings()));
    public static final Item LEVIATHAN_FIN = register("leviathan_fin", new Item(settings()));
    public static final Item PIXIE_DUST = register("pixie_dust", new Item(settings()));

    public static void init() {
        spawnEgg(EntityRegistry.GOBLIN_GRUNT, 0x0A2D14, 0x40D586);
        spawnEgg(EntityRegistry.PUMP_FROG, 0x952350, 0xAA67A6);
        spawnEgg(EntityRegistry.FLAPHAWK, 0x1C6C22, 0xB332D9);
        spawnEgg(EntityRegistry.MOCKER, 0x111111, 0x9C2F2F);
        spawnEgg(EntityRegistry.LEVIATHAN, 0x2F749C, 0x39E38E);
        spawnEgg(EntityRegistry.THUNDIZARD, 0xC8A964, 0xF1D548);
        spawnEgg(EntityRegistry.FAIRY, 0xF75B94, 0xFFDF7B);
    }

    private static <T extends MobEntity> void spawnEgg(EntityType<T> type, int primaryColor, int secondaryColor) {
        String id = Registries.ENTITY_TYPE.getId(type).getPath();
        Item item = register(id + "_spawn_egg", new SpawnEggItem(type, primaryColor, secondaryColor, settings()));
        SPAWN_EGGS.add(item);
    }
}
