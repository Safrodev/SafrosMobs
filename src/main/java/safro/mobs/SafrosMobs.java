package safro.mobs;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import safro.mobs.config.SMConfig;
import safro.mobs.registry.*;
import safro.saflib.SafLib;
import software.bernie.geckolib.GeckoLib;

// TODO: Add back Trinkets compat when it is out for 1.21
public class SafrosMobs implements ModInitializer {
	public static final String MODID = "safros-mobs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final RegistryKey<ItemGroup> ITEM_GROUP = SafLib.createGroup(MODID);

	@Override
	public void onInitialize() {
		// Config
		AutoConfig.register(SMConfig.class, JanksonConfigSerializer::new);

		// Registry
		MaterialRegistry.init();
		EntityRegistry.init();
		BlockItemRegistry.init();
		SoundRegistry.init();
		EffectRegistry.init();
		TagRegistry.init();

		// Events
		CommonEvents.init();

		SafLib.registerAll(ITEM_GROUP, BlockItemRegistry.PIXIE_DUST);
	}
}