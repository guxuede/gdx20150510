package com.guxuede.game.animation.move;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;

/**
 * Created by guxuede on 2016/7/14 .
 */
public abstract class ActorMoveToAction extends Action {
    public static final int IS_ARRIVE_RADIO = 10;

    protected boolean isArrive() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        final Vector2 target = getTargetPoint();
        if(target == null){
            return true;
        }else{
            float dist = target.dst2(entity.getEntityX(), entity.getEntityY());
            return dist < IS_ARRIVE_RADIO;
        }
    }

    @Override
    public boolean act(float delta) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            final Vector2 target = getTargetPoint();
            final float degrees = MathUtils.getAngle(entity.getEntityX(),entity.getEntityY(),target.x,target.y);
            entity.turnDirection(degrees);
            entity.isMoving = true;
            return false;
        }
        entity.stop();
        return true;
    }


    public abstract Vector2 getTargetPoint();
}
