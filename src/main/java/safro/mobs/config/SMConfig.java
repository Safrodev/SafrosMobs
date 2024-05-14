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

    @Comment("Default: 14")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int pumpFrogWeight = 10;

    @Comment("Default: 30")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int flaphawkWeight = 30;

    @Comment("Default: 4")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int mockerWeight = 4;

    @Comment("Default: 0.01 (Must be between 0.01 and 1)")
    public float mockerDropChance = 0.01F;

    @Comment("Default: 9")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int leviathanWeight = 9;

    @Comment("Default: 10")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int thundizardWeight = 10;

    public static SMConfig get() {
        return AutoConfig.getConfigHolder(SMConfig.class).get();
    }
}
