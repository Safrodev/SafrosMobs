package safro.mobs.entity;

import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class FlaphawkEntity extends PathAwareEntity implements GeoEntity {
    public static final TrackedData<Boolean> CLOSED = DataTracker.registerData(FlaphawkEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FlaphawkEntity(EntityType<? extends FlaphawkEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createFlaphawkAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 38.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
    }

    protected void initGoals() {
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (this.age % 20 == 0) {
            List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(6.0D, 2.0D, 6.0D));
            list.remove(this);
            if (list.isEmpty() && !this.isClosed()) {
                this.setClosed(true);
                this.triggerAnim("State", "state.close");
            } else if (!list.isEmpty() && this.isClosed() && !this.hasPassengers()) {
                this.setClosed(false);
            }

            if (!this.isClosed() && !this.hasPassengers()) {
                LivingEntity inRange = this.getWorld().getClosestEntity(LivingEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getY(), this.getBoundingBox().expand(1.75D, 1.75D, 1.75D));
                if (inRange != null) {
                    inRange.startRiding(this, true);
                    this.setClosed(true);
                    this.triggerAnim("State", "state.close");
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getFirstPassenger() instanceof LivingEntity entity) {
            float f = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            if (entity.damage(this.getDamageSources().mobAttack(this), f)) {
                this.applyDamageEffects(this, entity);
                this.getWorld().sendEntityStatus(this, (byte)13);
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isClosed() && (source.getSource() instanceof ProjectileEntity || source.isIn(DamageTypeTags.IS_PROJECTILE))) {
            return false;
        }
        return super.damage(source, amount);
    }

    public boolean isClosed() {
        return this.getDataTracker().get(CLOSED);
    }

    public void setClosed(boolean bl) {
        this.getDataTracker().set(CLOSED, bl);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CLOSED, true);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().isEmpty();
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 13) {
            for(int i = 0; i < 5; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), d, e, f);
            }
        } else {
            super.handleStatus(status);
        }
    }

    protected static boolean isFoliageAbove(ServerWorldAccess world, BlockPos pos) {
        for (int i = 1; i <= 6; i++) {
            BlockPos check = pos.up(i);
            if (world.getBlockState(check).isIn(BlockTags.LEAVES) || world.getBlockState(check.east()).isIn(BlockTags.LEAVES) ||
                    world.getBlockState(check.west()).isIn(BlockTags.LEAVES) || world.getBlockState(check.north()).isIn(BlockTags.LEAVES) || world.getBlockState(check.south()).isIn(BlockTags.LEAVES)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    @Override
    public Vec3d getVelocity() {
        return Vec3d.ZERO;
    }

    @Override
    public void setVelocity(Vec3d velocity) {
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this));
        controllers.add(new AnimationController<>(this, "State", 0, state -> {
            if (this.isClosed()) {
                return state.setAndContinue(RawAnimation.begin().thenPlay("state.close"));
            } else {
                return state.setAndContinue(RawAnimation.begin().thenPlayAndHold("state.open"));
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
