package com.guxuede.game.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class ActorRLShakeAction extends TemporalAction {

    float lastWorkTime = 0;

    public ActorRLShakeAction() {
    }

    public ActorRLShakeAction(float duration) {
        super(duration);
    }

    public ActorRLShakeAction(float duration, Interpolation interpolation) {
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
        actor.drawOffSetX = MathUtils.random(0, 5);;
    }

    protected void end () {
        AnimationEntity actor = ((AnimationEntity)target);
        actor.drawOffSetX = 0;
        actor.drawOffSetY = 0;
    }

}
