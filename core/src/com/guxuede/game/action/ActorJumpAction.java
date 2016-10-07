
package com.guxuede.game.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;
import com.guxuede.game.tools.MathUtils;
import com.guxuede.game.tools.TempObjects;

/**
 * 无视物理，直接改变位置
 */
public class ActorJumpAction extends GdxAction {
    public static final int IS_ARRIVE_RADIO = 10;
    public static final float JUMP_SPEED = 10F;
	private Vector2 targetPoint = new Vector2();

	private static final float duration = 5f;
    Vector2 v = new Vector2(0.1f, 0.1f);

    @Override
    protected void begin() {
        AnimationEntity actor = ((AnimationEntity)target);

        actor.addAction(ActionsFactory.sequence(ActionsFactory.scaleBy(0.2f, 0.2f, duration / 2), ActionsFactory.scaleBy(-0.2f, -0.2f, duration / 2)));

        float degrees = MathUtils.getAngle(actor.getCenterX(),actor.getCenterY(),targetPoint.x,targetPoint.y);

        actor.turnDirection(degrees);
        v.setAngle(degrees);
    }

    @Override
    protected boolean update(float time) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            entity.doMoveAnimation();
            final Vector2 target = getActorLinearVelocity();
            TempObjects.temp0Vector2.set(entity.getCenterX(),entity.getCenterY()).dot(tmpVector2);
            entity.setEntityPosition(TempObjects.temp0Vector2.x,TempObjects.temp0Vector2.y);
            return false;
        }
        return true;
    }

    @Override
    protected void end() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        entity.stop();
    }

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


    Vector2 tmpVector2 = new Vector2();
    Vector2 getActorLinearVelocity(){
        AnimationEntity actor = ((AnimationEntity)target);
        float speed = 1f;
        float degrees = MathUtils.getAngle(actor.getCenterX(),actor.getCenterY(),targetPoint.x,targetPoint.y);
        tmpVector2.set(com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * speed, com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * speed);
        System.out.println("getActorLinearVelocity = [" + tmpVector2 + "]");
        return tmpVector2;
    }

    public Vector2 getTargetPoint() {
        return targetPoint;
    }



    @Override
	public void reset () {
		super.reset();
	}

	public void setPosition (float x, float y) {
        targetPoint.set(x,y);
	}


}
