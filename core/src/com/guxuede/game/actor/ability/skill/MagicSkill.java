package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Pool;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.ActorThrowProjectionAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.EffectsEntity;
import com.guxuede.game.tools.MathUtils;

/**
 * Created by guxuede on 2016/9/27 .
 */
public class MagicSkill extends Skill{

    public float animationDuration;
    AnimationEffect animationEffect;

    int step = 0;

    public int getHotKey(){
        return Input.Keys.B;
    }

    public int getTargetType(){
        return TARGET_TYPE_TARGET;
    }


    @Override
    public boolean update(float delta) {
        stateTime += delta;
        if(stateTime > 0 && step == 0){
            step ++;
            if(target!=null){
                float degree = MathUtils.getAngle(owner.getCenterX(),owner.getCenterY(),target.getCenterX(),target.getCenterY());
                owner.turnDirection(degree);
            }
            animationEffect = new AnimationEffect("lightningSpell");
            owner.addAction(animationEffect);
            animationDuration =animationEffect.getDuration();
        }else if(stateTime >= animationDuration && step == 1){
            step ++;
            ActorThrowProjectionAction ta = new ActorThrowProjectionAction();
            ta.targetEntity = target;
            ta.targetPos = targetPos==null?null:targetPos.cpy();
            owner.addAction(ta);
            if(targetPos!=null){
                EffectsEntity effectsEntity = ActorFactory.createEffectsActor("wind2",owner.getWorld());
                effectsEntity.setCenterPosition(targetPos.x,targetPos.y);
                effectsEntity.addAction(ActionsFactory.delay(2f,ActionsFactory.actorDeathAnimation()));
                owner.getWorld().getStage().addActor(effectsEntity);
            }
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        animationDuration = 0;
        animationEffect = null;
    }

    @Override
    public void exit() {
        super.exit();
        step = 0;
        animationDuration = 0;
        if(animationEffect!=null){
            animationEffect.end();
            owner.removeAction(animationEffect);
        }
        animationEffect = null;
    }
}
