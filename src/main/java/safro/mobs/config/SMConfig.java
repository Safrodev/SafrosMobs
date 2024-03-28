package safro.mobs.config;

import blue.endless.jankson.Comment;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import safro.mobs.SafrosMobs;

@Config(name = SafrosMobs.MODID)
public class SMConfig implements ConfigData {
    @Comment("Default: 35")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int goblinGruntWeight = 35;

    @Comment("Default: 10")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int pumpFrogWeight = 10;

    @Comment("Default: 20")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int flaphawkWeight = 25;

    @Comment("Default: 8")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int mockerWeight = 8;

    public static SMConfig get() {
        return AutoConfig.getConfigHolder(SMConfig.class).get();
    }
}
