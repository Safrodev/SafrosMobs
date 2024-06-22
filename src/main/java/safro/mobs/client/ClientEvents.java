package safro.mobs.client;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import safro.mobs.registry.BlockItemRegistry;

import java.util.List;

public class ClientEvents {

    public static void init() {
    }

    public static void getItemTooltips(ItemStack stack, List<Text> lines) {
        if (stack.isOf(BlockItemRegistry.HEALING_CUBE.asItem())) {
            lines.add(Text.translatable("text.safros-mobs.healing_cube").formatted(Formatting.ITALIC, Formatting.GRAY));
        } else if (stack.isOf(BlockItemRegistry.REAPING_RING)) {
            lines.add(Text.translatable("text.safros-mobs.reaping_ring").formatted(Formatting.ITALIC, Formatting.GRAY));
        } else if (stack.isOf(BlockItemRegistry.SLAM_HAMMER)) {
            lines.add(Text.translatable("text.safros-mobs.slam_hammer").formatted(Formatting.ITALIC, Formatting.GRAY));
        } else if (stack.isOf(BlockItemRegistry.BLAZE_RUNNERS)) {
            lines.add(Text.translatable("text.safros-mobs.blaze_runners").formatted(Formatting.ITALIC, Formatting.GRAY));
        } else if (stack.isOf(BlockItemRegistry.GLIDE_BELT)) {
            lines.add(Text.translatable("text.safros-mobs.glide_belt").formatted(Formatting.ITALIC, Formatting.GRAY));
        }
    }
}
