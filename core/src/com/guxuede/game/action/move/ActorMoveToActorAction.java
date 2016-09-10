package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/7/14 .
 */
public class ActorMoveToActorAction extends ActorMoveToAction {

    protected AnimationEntity targetActor;

    public ActorMoveToActorAction(AnimationEntity targetActor) {
        this.targetActor = targetActor;
    }


    @Override
    public Vector2 getTargetPoint() {
        return targetActor==null?null:new Vector2(targetActor.getCenterX(),targetActor.getCenterY());
    }
}
