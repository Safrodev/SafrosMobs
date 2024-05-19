package safro.mobs.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import safro.mobs.config.SMConfig;
import safro.mobs.registry.BlockItemRegistry;
import safro.saflib.block.entity.BasicBlockEntity;

import java.util.List;

public class HealingCubeBlockEntity extends BasicBlockEntity {

    public HealingCubeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockItemRegistry.HEALING_CUBE_BLOCK_ENTITY, pos, state);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, HealingCubeBlockEntity be) {
        if (arePlayersNearby(world, pos)) {
            world.addParticle(ParticleTypes.WITCH, (pos.getX() + 0.5) + world.random.nextGaussian() * 0.12999999523162842D, pos.getY() + 0.5D + world.random.nextGaussian() * 0.12999999523162842D, (pos.getZ() + 0.5) + world.random.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, HealingCubeBlockEntity be) {
        if (arePlayersNearby(world, pos)) {
            List<PlayerEntity> list = getPlayersNearby(world, pos);

            for (PlayerEntity playerEntity : list) {
                if (pos.isWithinDistance(playerEntity.getBlockPos(), SMConfig.get().healingCubeRange)) {
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 30, 2, true, false, true));
                }
            }
        }
    }

    public static List<PlayerEntity> getPlayersNearby(World world, BlockPos pos) {
        int k = pos.getX();
        int l = pos.getY();
        int m = pos.getZ();
        Box box = (new Box(k, l, m, k + 1, l + 1, m + 1)).expand(32).stretch(0.0D, world.getHeight(), 0.0D);
        return world.getNonSpectatingEntities(PlayerEntity.class, box);
    }

    public static boolean arePlayersNearby(World world, BlockPos pos) {
        return !getPlayersNearby(world, pos).isEmpty();
    }
}
