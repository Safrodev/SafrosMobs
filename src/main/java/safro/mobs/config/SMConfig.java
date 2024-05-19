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
    @ConfigEntry.Category("spawn_weights")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int goblinGruntWeight = 35;

    @Comment("Default: 10")
    @ConfigEntry.Category("spawn_weights")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int pumpFrogWeight = 10;

    @Comment("Default: 30")
    @ConfigEntry.Category("spawn_weights")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int flaphawkWeight = 30;

    @Comment("Default: 4")
    @ConfigEntry.Category("spawn_weights")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int mockerWeight = 4;

    @Comment("Default: 9")
    @ConfigEntry.Category("spawn_weights")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int leviathanWeight = 9;

    @Comment("Default: 10")
    @ConfigEntry.Category("spawn_weights")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int thundizardWeight = 10;

    @Comment("Default: 12")
    @ConfigEntry.Category("spawn_weights")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int fairyWeight = 12;


    @Comment("Default: 0.01 (Must be greater than 0 and at most 1)")
    @ConfigEntry.Category("misc")
    public float mockerDropChance = 0.02F;

    @Comment("Default: 32")
    @ConfigEntry.Category("misc")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int healingCubeRange = 32;

    public static SMConfig get() {
        return AutoConfig.getConfigHolder(SMConfig.class).get();
    }
}
