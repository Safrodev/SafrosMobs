package safro.mobs.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import safro.mobs.SafrosMobs;
import safro.saflib.registry.BaseTagRegistry;

public class TagRegistry extends BaseTagRegistry {
    public static final TagKey<Biome> HAS_FLAPHAWK = biome(SafrosMobs.MODID, "has_flaphawk");
    public static final TagKey<Biome> HAS_THUNDIZARD = biome(SafrosMobs.MODID, "has_thundizard");

    public static final TagKey<Item> TREATS = item(SafrosMobs.MODID, "treats");

    public static void init() {}
}
