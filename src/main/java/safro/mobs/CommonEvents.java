package safro.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import safro.mobs.registry.BlockItemRegistry;
import safro.mobs.util.SMUtil;
import safro.saflib.event.EntityTickEvents;

import java.util.Set;

public class CommonEvents {

    public static void init() {
        EntityTickEvents.PLAYER.register(CommonEvents::playerTick);
    }

    public static void playerTick(PlayerEntity player) {
        if (player.shouldSpawnSprintingParticles() && player.getEquippedStack(EquipmentSlot.FEET).isOf(BlockItemRegistry.BLAZE_RUNNERS)) {
            Vec3d vec3d = player.getVelocity();
            BlockPos blockPos = player.getLandingPos();
            BlockPos blockPos2 = player.getBlockPos();
            double d = player.getX() + (player.getRandom().nextDouble() - 0.5) * 0.6;
            double e = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 0.6;
            if (blockPos2.getX() != blockPos.getX()) {
                d = MathHelper.clamp(d, blockPos.getX(), (double)blockPos.getX() + 1.0);
            }

            if (blockPos2.getZ() != blockPos.getZ()) {
                e = MathHelper.clamp(e, blockPos.getZ(), (double)blockPos.getZ() + 1.0);
            }

            player.getWorld().addParticle(ParticleTypes.SMALL_FLAME, d, player.getY() + 0.1, e, vec3d.x * -4.0, 1.5, vec3d.z * -4.0);
        }
    }

    public static void onPlayerAttack(PlayerEntity player, Entity target, float damage) {
        if (player.getInventory().containsAny(Set.of(BlockItemRegistry.REAPING_RING))) {
            player.heal(SMUtil.capMax(damage * 0.5F, 16.0F));
        }
    }
}
