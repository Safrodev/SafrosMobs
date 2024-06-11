package safro.mobs.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import safro.mobs.api.SimpleAnimatable;
import safro.mobs.entity.ai.goal.FlyingEscapeGoal;
import safro.mobs.entity.ai.goal.FlyingHealGoal;
import safro.mobs.entity.ai.goal.FlyingWanderGoal;
import safro.mobs.registry.SoundRegistry;
import safro.mobs.registry.TagRegistry;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FairyEntity extends PassiveEntity implements SimpleAnimatable {
    private static final TrackedData<Integer> HEAL_COOLDOWN = DataTracker.registerData(FairyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public int fedTicks;

    public FairyEntity(EntityType<? extends FairyEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlyControl(this);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FlyingEscapeGoal<>(this, PlayerEntity.class, 10.0F));
        this.goalSelector.add(2, new TemptGoal(this, 1.1D, Ingredient.fromTag(TagRegistry.TREATS), false));
        this.goalSelector.add(3, new FlyingHealGoal(this, 10.0F));
        this.goalSelector.add(4, new FlyingWanderGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createFairyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.29D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HEAL_COOLDOWN, 0);
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.isHighUp()) {
            this.setVelocity(this.getVelocity().add(0, 0.08, 0));
        }
    }

    public void tick() {
        super.tick();
        if (this.fedTicks > 0) {
            --this.fedTicks;
        } else {
            this.fedTicks = 0;
        }

        if (this.getHealCooldown() > 0) {
            this.setHealCooldown(this.getHealCooldown() - 1);
        }
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isIn(TagRegistry.TREATS) && this.fedTicks <= 0) {
            if (!this.getWorld().isClient) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 3, true, false));
                player.heal(10.0F);
                stack.decrement(1);
            }

            this.getWorld().sendEntityStatus(this, (byte)13);
            this.fedTicks = 100;
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private boolean isHighUp() {
        if (this.getY() > this.getWorld().getHeight()) {
            return true;
        }
        BlockPos height = this.getWorld().getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, this.getBlockPos());
        int maxY = 20 + height.getY();
        return this.getY() > maxY;
    }

    public static BlockPos getPosFromGround(FairyEntity entity, World world, double x, double z) {
        BlockPos pos = BlockPos.ofFloored(x, entity.getY(), z);
        for (int yDown = 0; yDown < 3; yDown++) {
            if (!world.isAir(pos.down(yDown))) {
                return pos.up(yDown);
            }
        }
        return pos;
    }

    public void slowMovement(BlockState state, Vec3d multiplier) {
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("FedTicks", this.fedTicks);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.fedTicks = nbt.getInt("FedTicks");
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 13) {
            for (int i = 0; i < 15; i++) {
                this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
            }
            this.getWorld().playSound(null, this.getBlockPos(), SoundRegistry.FAIRY_HEAL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        } else {
            super.handleStatus(status);
        }
    }

    public int getHealCooldown() {
        return this.dataTracker.get(HEAL_COOLDOWN);
    }

    public void setHealCooldown(int c) {
        this.dataTracker.set(HEAL_COOLDOWN, c);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean isAttackingAnim() {
        return false;
    }

    @Override
    public boolean hasMoveAnim() {
        return false;
    }

    @Override
    public RawAnimation getAttackType() {
        return null;
    }

    class FlyControl extends MoveControl {

        public FlyControl(FairyEntity entity) {
            super(entity);
        }

        public void tick() {
            float speedModifier = 1;
            if (this.state == State.MOVE_TO) {
                if (FairyEntity.this.horizontalCollision) {
                    float yaw = FairyEntity.this.getYaw();
                    FairyEntity.this.setYaw(yaw + 180.0F);
                    speedModifier = 0.1F;
                    BlockPos target = FairyEntity.getPosFromGround(FairyEntity.this, FairyEntity.this.getWorld(), FairyEntity.this.getX() + FairyEntity.this.random.nextInt(15) - 7, FairyEntity.this.getZ() + FairyEntity.this.random.nextInt(15) - 7);
                    this.targetX = target.getX();
                    this.targetY = target.getY();
                    this.targetZ = target.getZ();
                }
                double d0 = this.targetX - FairyEntity.this.getX();
                double d1 = this.targetY - FairyEntity.this.getY();
                double d2 = this.targetZ - FairyEntity.this.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = MathHelper.sqrt((float)d3);

                if (d3 < FairyEntity.this.getBoundingBox().getAverageSideLength()) {
                    this.state = State.WAIT;
                    FairyEntity.this.setVelocity(FairyEntity.this.getVelocity().multiply(0.5D, 0.5D, 0.5D));
                } else {
                    FairyEntity.this.setVelocity(FairyEntity.this.getVelocity().add(d0 / d3 * 0.05D * this.speed * speedModifier, d1 / d3 * 0.05D * this.speed * speedModifier, d2 / d3 * 0.05D * this.speed * speedModifier));

                    if (FairyEntity.this.getTarget() == null) {
                        FairyEntity.this.setYaw(-((float) MathHelper.atan2(FairyEntity.this.getVelocity().x, FairyEntity.this.getVelocity().z)) * (180F / (float) Math.PI));
                    } else {
                        double d4 = FairyEntity.this.getTarget().getX() - FairyEntity.this.getX();
                        double d5 = FairyEntity.this.getTarget().getZ() - FairyEntity.this.getZ();
                        FairyEntity.this.setYaw(-((float) MathHelper.atan2(d4, d5)) * (180F / (float) Math.PI));
                    }
                    FairyEntity.this.bodyYaw = FairyEntity.this.getYaw();
                }
            }
        }
    }
}
