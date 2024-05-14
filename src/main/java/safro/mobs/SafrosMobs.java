package safro.mobs;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import safro.mobs.config.SMConfig;
import safro.mobs.registry.*;
import safro.saflib.SafLib;
import software.bernie.geckolib.GeckoLib;

public class SafrosMobs implements ModInitializer {
	public static final String MODID = "safros-mobs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final RegistryKey<ItemGroup> ITEM_GROUP = SafLib.createGroup(MODID);

	@Override
	public void onInitialize() {
		// Config
		AutoConfig.register(SMConfig.class, JanksonConfigSerializer::new);

		// Registry
		GeckoLib.initialize();
		EntityRegistry.init();
		ItemRegistry.init();
		SoundRegistry.init();
		EffectRegistry.init();
		TagRegistry.init();

		// Events

		SafLib.registerAll(ITEM_GROUP, Items.CREEPER_HEAD);
	}
}