package com.guxuede.game.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class ActorXXXTracksAction extends TemporalAction {

    public static final float shortShaft = 30;
    public static final float longShaft = 30;
    public float degree = 0;

    @Override
    protected void update(float percent) {
        AnimationEntity actor = ((AnimationEntity)target);
        actor.drawOffSetX = getX(percent);
        actor.drawOffSetY = getY(percent);
    }

    protected void end () {
        AnimationEntity actor = ((AnimationEntity)target);
        actor.drawOffSetX = 0;
        actor.drawOffSetY = 0;
    }


    public float getX(float percent) {
        return longShaft* MathUtils.cosDeg(degree-90+180 * percent);
    }

    public float getY(float percent) {
        return shortShaft*MathUtils.sinDeg(degree-90+180 * percent);
    }

}
