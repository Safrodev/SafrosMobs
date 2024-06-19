package safro.mobs.client;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import safro.mobs.registry.BlockItemRegistry;

import java.util.List;

public class ClientEvents {

    public static void init() {
        ItemTooltipCallback.EVENT.register(ClientEvents::getItemTooltips);
    }

    private static void getItemTooltips(ItemStack stack, TooltipContext context, List<Text> lines) {
        if (stack.isOf(BlockItemRegistry.HEALING_CUBE.asItem())) {
            lines.add(Text.translatable("text.safros-mobs.healing_cube").formatted(Formatting.ITALIC));
        } else if (stack.isOf(BlockItemRegistry.REAPING_RING.asItem())) {
            lines.add(Text.translatable("text.safros-mobs.reaping_ring").formatted(Formatting.ITALIC));
        } else if (stack.isOf(BlockItemRegistry.SLAM_HAMMER.asItem())) {
            lines.add(Text.translatable("text.safros-mobs.slam_hammer").formatted(Formatting.ITALIC));
        } else if (stack.isOf(BlockItemRegistry.BLAZE_RUNNERS.asItem())) {
            lines.add(Text.translatable("text.safros-mobs.blaze_runners").formatted(Formatting.ITALIC));
        }
    }
}
