package safro.mobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import safro.mobs.registry.SoundRegistry;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class LeviathanEntity extends WaterCreatureEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public LeviathanEntity(EntityType<? extends LeviathanEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2000000476837158D, false));
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(4, new ChaseBoatGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, GuardianEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, SchoolingFishEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createLeviathanAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(this.getDamageSources().mobAttack(this), (float) ((int) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            this.applyDamageEffects(this, target);
            this.playSound(SoundRegistry.LEVIATHAN_ATTACK, 0.8F, 1.0F);
        }
        return bl;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAiDisabled()) {
            this.setAir(this.getMaxAir());
        } else {
            if (this.isOnGround()) {
                this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F));
                this.setYaw(this.random.nextFloat() * 360.0F);
                this.setOnGround(false);
                this.velocityDirty = true;
            }

            if (this.getWorld().isClient && this.isTouchingWater() && this.getVelocity().lengthSquared() > 0.03D) {
                Vec3d vec3d = this.getRotationVec(0.0F);
                float f = MathHelper.cos(this.getYaw() * 0.017453292F) * 0.3F;
                float g = MathHelper.sin(this.getYaw() * 0.017453292F) * 0.3F;
                float h = 1.2F - this.random.nextFloat() * 0.7F;

                for (int i = 0; i < 2; ++i) {
                    this.getWorld().addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3d.x * (double) h + (double) f, this.getY() - vec3d.y, this.getZ() - vec3d.z * (double) h + (double) g, 0.0D, 0.0D, 0.0D);
                    this.getWorld().addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3d.x * (double) h - (double) f, this.getY() - vec3d.y, this.getZ() - vec3d.z * (double) h - (double) g, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void mobTick() {
        super.mobTick();
        List<BoatEntity> list = this.getWorld().getNonSpectatingEntities(BoatEntity.class, this.getBoundingBox().expand(1.0D));
        for (BoatEntity entity : list) {
            if (entity.getControllingPassenger() instanceof PlayerEntity) {
                entity.emitGameEvent(GameEvent.ENTITY_DAMAGE, this);
                if (this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    entity.dropItem(entity.asItem());
                }
                entity.discard();
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.LEVIATHAN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.LEVIATHAN_DEATH;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericSwimController(this));
        controllers.add(new AnimationController<>(this, "Attack", 5, state -> {
            if (this.isAttacking()) {
                return state.setAndContinue(DefaultAnimations.ATTACK_BITE);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
