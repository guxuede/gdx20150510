package com.guxuede.game.actor.state;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.action.ActorThrowProjectionAction;
import com.guxuede.game.action.effects.AnimationEffect;

/**
 * Created by guxuede on 2016/6/16 .
 */
public class AttackState extends StandState {


    public AttackState(int direction){
        super(direction);
    }

    public float animationDuration;
    public float stateTime;
    AnimationEffect animationEffect;

    @Override
    public void enter(AnimationEntity entity, InputEvent event) {
        entity.stop();
        //entity.animationPlayer.doAttackAnimation();
        //animationDuration = entity.animationPlayer.currentAnimation.getAnimationDuration();
        animationEffect = new AnimationEffect("lightningSpell");
        entity.addAction(animationEffect);
        animationDuration =animationEffect.getDuration();
    }

//    @Override
//    public ActorState handleInput(AnimationEntity entity, InputEvent event){
//        return super.handleInput(entity,event);
//    }

    @Override
    public ActorState update(AnimationEntity entity,float delta) {
        stateTime += delta;
        if(stateTime >= animationDuration){
            entity.addAction(new ActorThrowProjectionAction());
            return new StandState(direction);
        }
        return null;
    }

    @Override
    public void exit(AnimationEntity entity) {
        stateTime = 0;
        animationDuration = 0;
        entity.removeAction(animationEffect);
        animationEffect = null;
    }
}
