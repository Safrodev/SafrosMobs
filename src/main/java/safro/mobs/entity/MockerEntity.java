package safro.mobs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import safro.mobs.config.SMConfig;
import safro.mobs.entity.ai.goal.ClonePlayerGoal;

import java.util.Optional;
import java.util.UUID;

public class MockerEntity extends HostileEntity {
    public static final TrackedData<Boolean> CLONED = DataTracker.registerData(MockerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Optional<UUID>> PLAYER_ID = DataTracker.registerData(MockerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public MockerEntity(EntityType<? extends MockerEntity> entityType, World world) {
        super(entityType, world);
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
    }

    public static DefaultAttributeContainer.Builder createMockerAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3499999940395355).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ClonePlayerGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(5, new WanderAroundGoal(this, 0.6D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 15.0F, 1.0F));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public boolean hasCloned() {
        return this.getDataTracker().get(CLONED);
    }

    public Optional<UUID> getPlayerId() {
        return this.dataTracker.get(PLAYER_ID);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CLONED, false);
        this.dataTracker.startTracking(PLAYER_ID, Optional.empty());
    }

    public void clonePlayer(PlayerEntity player) {
        if (!this.hasCloned()) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!player.getEquippedStack(slot).isEmpty()) {
                    this.equipStack(slot, player.getEquippedStack(slot).copy());
                    this.setEquipmentDropChance(slot, SMConfig.get().mockerDropChance);
                }
            }

            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setFrom(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH));
            this.setHealth(this.getMaxHealth());
            this.setCustomName(player.getCustomName());
            this.dataTracker.set(PLAYER_ID, Optional.of(player.getUuid()));
            this.dataTracker.set(CLONED, true);
        }
    }
}
