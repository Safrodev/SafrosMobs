package safro.mobs.config;

import blue.endless.jankson.Comment;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import safro.mobs.SafrosMobs;

@Config(name = SafrosMobs.MODID)
public class SMConfig implements ConfigData {
    @Comment("""
            The spawn weight for: Goblin Grunt
            Default: 30
            """)
    public int goblinGruntWeight = 30;

    @Comment("""
            The spawn weight for: Pump Frog
            Default: 8
            """)
    public int pumpFrogWeight = 8;

    public static SMConfig get() {
        return AutoConfig.getConfigHolder(SMConfig.class).get();
    }
}
