package safro.mobs.api;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public interface SimpleAnimatable extends GeoEntity {

    boolean isAttackingAnim();

    RawAnimation getAttackType();

    default double getMoveAnimSpeed() {
        return 1.0D;
    }

    default boolean hasMoveAnim() {
        return true;
    }

    default double getAttackAnimSpeed() {
        return 1.0D;
    }

    @Override
    default void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(customAttack(this, getAttackType()));
        registrar.add(customWalkIdle(this));
    }

    static <T extends SimpleAnimatable> AnimationController<T> customAttack(T animatable, RawAnimation attackAnimation) {
        return new AnimationController<>(animatable, "Attack", 5, state -> {
            if (animatable.isAttackingAnim()) {
                state.getController().setAnimationSpeed(animatable.getAttackAnimSpeed());
                return state.setAndContinue(attackAnimation);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        });
    }

    static <T extends SimpleAnimatable> AnimationController<T> customWalkIdle(T animatable) {
        return new AnimationController<T>(animatable, "Walk/Idle", 0, state -> {
            if (state.isMoving() && animatable.hasMoveAnim()) {
                state.getController().setAnimationSpeed(animatable.getMoveAnimSpeed());
                return state.setAndContinue(DefaultAnimations.WALK);
            }
            return animatable.isAttackingAnim() ? state.setAndContinue(animatable.getAttackType()) : state.setAndContinue(DefaultAnimations.IDLE);
        });
    }
}
