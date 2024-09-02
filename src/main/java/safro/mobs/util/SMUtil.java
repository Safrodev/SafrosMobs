package safro.mobs.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class SMUtil {

    public static boolean isValidNaturalSpawn(EntityType<? extends PathAwareEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && isValidSpawn(type, world, spawnReason, pos, random);
    }

    public static boolean isValidSpawn(EntityType<? extends PathAwareEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBaseLightLevel(pos, 0) > 8;
    }

    public static boolean canLeviathanSpawn(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return WaterCreatureEntity.canSpawn(type, world, reason, pos, random) && random.nextFloat() < 0.08;
    }

    public static boolean canMockerSpawn(EntityType<? extends HostileEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return HostileEntity.canSpawnIgnoreLightLevel(type, world, spawnReason, pos, random) && random.nextFloat() < 0.05F;
    }

    public static float capMax(float amount, float max) {
        if (amount <= max) {
            return amount;
        }
        return max + ((amount + max) / (amount - max));
    }
}
