
package com.guxuede.game.action;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;
import com.guxuede.game.tools.MathUtils;
import com.guxuede.game.tools.TempObjects;

import static java.lang.Float.min;
import static java.lang.Math.max;

/**
 * 无视物理，直接改变位置
 */
public class ActorJumpAction extends GdxAction {
    public static final int IS_ARRIVE_RADIO = 10;
    public static final float JUMP_SPEED = 10F;
	private Vector2 targetPoint = new Vector2();

	private static final float duration = 5f;
    private Vector2 oldPoint = new Vector2();

    @Override
    protected void begin() {
        AnimationEntity actor = ((AnimationEntity)target);
        actor.collisionSize = 0;
        actor.addAction(ActionsFactory.sequence(ActionsFactory.scaleBy(0.2f, 0.2f, duration / 2), ActionsFactory.scaleBy(-0.2f, -0.2f, duration / 2)));

        float degrees = MathUtils.getAngle(actor.getCenterX(),actor.getCenterY(),targetPoint.x,targetPoint.y);
        actor.turnDirection(degrees);
        oldPoint.set(actor.getPhysicsPosition());
    }

    @Override
    protected boolean update(float time) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            entity.doMoveAnimation();
            Vector2 targetP = TempObjects.temp0Vector2.set(targetPoint);
            Vector2 entityP = entity.getPhysicsPosition();
            Vector2 newP = targetP.sub(entityP)//得到实体到目标的向量
                    .nor()//归一化
                    .scl(20f)//增加速度
                    .add(entityP);//得到最终位置
            entity.setPhysicsPosition(newP.x,newP.y);
            //速度太快可能穿过，做一次检查
            if(MathUtils.isBetween(oldPoint,targetPoint,newP)){
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    protected void end() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        entity.stop();
        entity.collisionSize = 99;
    }

    protected boolean isArrive() {
        final AnimationEntity entity = (AnimationEntity) getTarget();

        final Vector2 target = getTargetPoint();
        if(target == null){
            return true;
        }else{
            float dist = target.dst2(entity.getPhysicsPosition());
            return dist < IS_ARRIVE_RADIO;
        }
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
