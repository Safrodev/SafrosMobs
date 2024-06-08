package safro.mobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import safro.mobs.api.SimpleAnimatable;
import safro.mobs.registry.SoundRegistry;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GoblinGruntEntity extends HostileEntity implements SimpleAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int attackTicksLeft;

    public GoblinGruntEntity(EntityType<? extends GoblinGruntEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.0F);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.4D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createGoblinGruntAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D);
    }

    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }
    }

    public boolean tryAttack(Entity target) {
        this.attackTicksLeft = 20;
        this.getWorld().sendEntityStatus(this, (byte)4);
        float f = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        float g = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean bl = target.damage(this.getDamageSources().mobAttack(this), g);
        if (bl) {
            target.setVelocity(target.getVelocity().add(0.0D, 0.2D, 0.0D));
            this.applyDamageEffects(this, target);
        }
        return bl;
    }

    public void handleStatus(byte status) {
        if (status == 4) {
            this.attackTicksLeft = 20;
        } else {
            super.handleStatus(status);
        }
    }

    public static boolean canSpawn(EntityType<GoblinGruntEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() <= 5 && canSpawnInDark(type, world, spawnReason, pos, random);
    }

    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.GOBLIN_GRUNT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.GOBLIN_GRUNT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.GOBLIN_GRUNT_DEATH;
    }

    @Override
    public boolean isAttackingAnim() {
        return this.getAttackTicksLeft() > 0 && this.isAlive();
    }

    @Override
    public RawAnimation getAttackType() {
        return DefaultAnimations.ATTACK_STRIKE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getAttackAnimSpeed() {
        return 4.0D;
    }
}