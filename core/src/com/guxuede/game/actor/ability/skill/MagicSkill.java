package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.utils.Pool;
import com.guxuede.game.action.ActorThrowProjectionAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;

/**
 * Created by guxuede on 2016/9/27 .
 */
public class MagicSkill extends Skill{

    AnimationEntity target;
    public float animationDuration;
    AnimationEffect animationEffect;

    int step = 0;


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
            owner.addAction(ta);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        target = null;
        animationDuration = 0;
        animationEffect = null;
    }

    @Override
    public void exit() {
        step = 0;
        stateTime = 0;
        target = null;
        animationDuration = 0;
        if(animationEffect!=null){
            animationEffect.end();
            owner.removeAction(animationEffect);
        }
        animationEffect = null;
    }
}
