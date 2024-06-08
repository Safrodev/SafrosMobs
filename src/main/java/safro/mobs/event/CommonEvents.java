package safro.mobs.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import safro.mobs.registry.BlockItemRegistry;
import safro.mobs.util.SMUtil;
import safro.saflib.event.EntityTickEvents;

import java.util.Set;

public class CommonEvents {

    public static void init() {
        EntityTickEvents.PLAYER.register(CommonEvents::playerTick);
    }

    public static void playerTick(PlayerEntity player) {
    }

    public static void onPlayerAttack(PlayerEntity player, Entity target, float damage) {
        if (player.getInventory().containsAny(Set.of(BlockItemRegistry.REAPING_RING))) {
            player.heal(SMUtil.capMax(damage * 0.5F, 16.0F));
        }
    }
}
