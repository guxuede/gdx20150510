package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;

/**
 * Created by guxuede on 2016/7/14 .
 */
public abstract class ActorMoveToAction extends Action {
    public static final int IS_ARRIVE_RADIO = 10;
    public ActorMoveListener actorMoveListener;

    protected boolean isArrive() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        final Vector2 target = getTargetPoint();
        if(target == null){
            return true;
        }else{
            float dist = target.dst2(entity.getCenterX(), entity.getCenterY());
            return dist < IS_ARRIVE_RADIO;
        }
    }

    @Override
    public boolean act(float delta) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            final Vector2 target = getTargetPoint();
            final float degrees = MathUtils.getAngle(entity.getCenterX(),entity.getCenterY(),target.x,target.y);
            entity.turnDirection(degrees);
            entity.isMoving = true;
            return false;
        }
        entity.stop();
        return true;
    }

    public void onArrived(){

    }


    public abstract Vector2 getTargetPoint();

    public static interface ActorMoveListener{
        void onArrived(Vector2 target,Actor actor);
    }
}
