package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/7/14 .
 */
public class ActorMoveToActorAction extends ActorMoveToAction {

    protected AnimationEntity targetActor;

    public ActorMoveToActorAction(){

    }

    public ActorMoveToActorAction(AnimationEntity targetActor) {
        this.targetActor = targetActor;
    }

    @Override
    protected boolean isArrive() {
        boolean is= super.isArrive();
        if(is){
            onArrived();
        }
        return is;
    }

    @Override
    public void onArrived() {
        if(actorMoveListener!=null){
            actorMoveListener.onArrived(null,targetActor);
        }
    }

    @Override
    public Vector2 getTargetPoint() {
        return targetActor==null?null:new Vector2(targetActor.getCenterX(),targetActor.getCenterY());
    }

    public void setTargetActor(AnimationEntity targetActor) {
        this.targetActor = targetActor;
    }
}
