package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by guxuede on 2016/7/16 .
 */
public abstract class ActorMoveToMutilAction  extends ActorMoveToAction{


    public abstract boolean haveNextTarget();
    public abstract void moveToNextTarget();
    public abstract Vector2 getCurrentTarget();

    @Override
    protected boolean isArrive() {
        boolean isArrive = super.isArrive();
        if(isArrive){
            onArrived();
        }
        if(isArrive && haveNextTarget()){
            moveToNextTarget();
            isArrive = false;
        }
        return isArrive;
    }

    @Override
    public void onArrived() {
        if(actorMoveListener!=null){
            actorMoveListener.onArrived(getCurrentTarget(),null);
        }
    }

    @Override
    public Vector2 getTargetPoint() {
        return getCurrentTarget();
    }

}
