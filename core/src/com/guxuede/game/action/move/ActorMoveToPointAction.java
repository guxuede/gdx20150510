package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by guxuede on 2016/7/14 .
 */
public class ActorMoveToPointAction extends ActorMoveToAction {

    protected Vector2 targetPoint;

    public ActorMoveToPointAction(Vector2 targetPoint) {
        this.targetPoint = targetPoint;
    }

    public ActorMoveToPointAction(float targetX, float targetY) {
        this.targetPoint = new Vector2(targetX, targetY);
    }

    @Override
    public Vector2 getTargetPoint() {
        return targetPoint;
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
            actorMoveListener.onArrived(getTargetPoint(),null);
        }
    }

}
