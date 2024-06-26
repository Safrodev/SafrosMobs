package safro.mobs.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import safro.mobs.SafrosMobs;

public class SoundRegistry {
    // Goblin Grunt
    public static final SoundEvent GOBLIN_GRUNT_AMBIENT = register("goblin_grunt_ambient");
    public static final SoundEvent GOBLIN_GRUNT_HURT = register("goblin_grunt_hurt");
    public static final SoundEvent GOBLIN_GRUNT_DEATH = register("goblin_grunt_death");

    // Pump Frog
    public static final SoundEvent PUMP_FROG_AMBIENT = register("pump_frog_ambient");

    // Leviathan
    public static final SoundEvent LEVIATHAN_ATTACK = register("leviathan_attack");
    public static final SoundEvent LEVIATHAN_HURT = register("leviathan_hurt");
    public static final SoundEvent LEVIATHAN_DEATH = register("leviathan_death");


    // Fairy
    public static final SoundEvent FAIRY_HEAL = register("fairy_heal");

    static SoundEvent register(String id) {
        SoundEvent sound = SoundEvent.of(new Identifier(SafrosMobs.MODID, id));
        Registry.register(Registries.SOUND_EVENT, new Identifier(SafrosMobs.MODID, id), sound);
        return sound;
    }

    public static void init() {}
}
