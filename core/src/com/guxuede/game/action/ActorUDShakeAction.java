package com.guxuede.game.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class ActorUDShakeAction extends TemporalAction {

    float lastWorkTime = 0;

    public ActorUDShakeAction() {
    }

    public ActorUDShakeAction(float duration) {
        super(duration);
    }

    public ActorUDShakeAction(float duration, Interpolation interpolation) {
        super(duration, interpolation);
    }

    @Override
    protected void update(float percent) {
        AnimationEntity actor = ((AnimationEntity)target);
        if(getTime()-lastWorkTime > 0.1){
            lastWorkTime = getTime();
            updateTime(actor,percent);
        }
    }

    protected void updateTime(AnimationEntity actor,float percent) {
        actor.drawOffSetY = MathUtils.random(0, 5);;
    }

    protected void end () {
        AnimationEntity actor = ((AnimationEntity)target);
        actor.drawOffSetX = 0;
        actor.drawOffSetY = 0;
    }

}
