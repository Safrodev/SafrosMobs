package safro.mobs.entity;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import safro.mobs.api.SimpleAnimatable;
import safro.mobs.entity.ai.goal.JumpGoal;
import safro.mobs.registry.SoundRegistry;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PumpFrogEntity extends PathAwareEntity implements SimpleAnimatable {
    private static final TrackedData<Integer> JUMP_COOLDOWN = DataTracker.registerData(PumpFrogEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PumpFrogEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setStepHeight(1.0F);
    }

    public static DefaultAttributeContainer.Builder createPumpFrogAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.265D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new JumpGoal(this));
        this.goalSelector.add(3, new WanderNearTargetGoal(this, 0.8D, 16.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.4D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, FrogEntity.class, 11.0F));
        this.targetSelector.add(1, new RevengeGoal(this));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(JUMP_COOLDOWN, 160);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getJumpCooldown() > 0) {
            this.setJumpCooldown(this.getJumpCooldown() - 1);
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        float f = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (target instanceof LivingEntity) {
            f += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            target.setOnFireFor(i * 4);
        }

        boolean bl = target.damage(this.getDamageSources().mobAttack(this), f);
        if (bl) {
            if (target instanceof LivingEntity) {
                ((LivingEntity)target).takeKnockback(25.0D, MathHelper.sin(this.getYaw() * 0.017453292F), -MathHelper.cos(this.getYaw() * 0.017453292F));
                target.addVelocity(0.0D, 0.9D, 0.0D);
                this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
            }

            this.applyDamageEffects(this, target);
            this.onAttacking(target);
        }

        return bl;
    }

    public int getJumpCooldown() {
        return this.dataTracker.get(JUMP_COOLDOWN);
    }

    public void setJumpCooldown(int c) {
        this.dataTracker.set(JUMP_COOLDOWN, c);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) ? 10.0F : world.getPhototaxisFavor(pos);
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (fallDistance >= 6.0F) {
            this.addVelocity(0.0D, 0.2D, 0.0D);
            return false;
        }
        return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    public int getMinAmbientSoundDelay() {
        return 120;
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    public int getXpToDrop() {
        return 1 + this.getWorld().random.nextInt(3);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.PUMP_FROG_AMBIENT;
    }

    @Override
    public boolean isAttackingAnim() {
        return this.isAttacking();
    }

    @Override
    public RawAnimation getAttackType() {
        return DefaultAnimations.ATTACK_BITE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getMoveAnimSpeed() {
        return 2.0D;
    }
}
