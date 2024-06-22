package safro.mobs.compat;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;

public class TrinketsCompat {

    public static boolean hasTrinket(LivingEntity entity, Item item) {
        return TrinketsApi.getTrinketComponent(entity).isPresent() && TrinketsApi.getTrinketComponent(entity).get().isEquipped(item);
    }
}
