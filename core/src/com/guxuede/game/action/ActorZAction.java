package com.guxuede.game.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/11/2 .
 */
public class ActorZAction extends RelativeTemporalAction {
    private float amountZ;

    public ActorZAction(float amountZ, float duration, Interpolation interpolation){
        this.amountZ = amountZ;
        this.setDuration(duration);
        this.setInterpolation(interpolation);
    }

    protected void updateRelative (float percentDelta) {
        AnimationEntity entity = ((AnimationEntity)target);
        entity.drawOffSetY +=amountZ * percentDelta;
    }

    public void setAmount (float z) {
        amountZ = z;
    }


}