package com.guxuede.game.animation.move;

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
        if(isArrive && haveNextTarget()){
            moveToNextTarget();
            isArrive = false;
        }
        return isArrive;
    }

    @Override
    public Vector2 getTargetPoint() {
        return getCurrentTarget();
    }

}
