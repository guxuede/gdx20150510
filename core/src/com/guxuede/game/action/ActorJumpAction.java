
package com.guxuede.game.action;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;
import com.guxuede.game.tools.MathUtils;
import com.guxuede.game.tools.TempObjects;

import static java.lang.Math.max;

/**
 * 无视物理，直接改变位置
 */
public class ActorJumpAction extends GdxAction {
    public static final float SCALE = 0.5F;
    public static final float HEIGHT = 100F;

    public static final int IS_ARRIVE_RADIO = 10;
    public static final float JUMP_SPEED = 100F;
    public static final double RISE_DURATION = 0.8;
    public static final double DOWN_DURATION = 1-RISE_DURATION;
    private Vector2 targetPoint = new Vector2();
    private Vector2 oldPoint = new Vector2();//记录起点位置
    private float totalLen = 0;//记录总长度
    private float lastPercent;
    private float lastPlusPercent = 0;
    private float totalScale;
    private float totalHight;


    @Override
    protected void begin() {
        lastPercent = 0;
        AnimationEntity actor = ((AnimationEntity)target);
        actor.collisionSize = 0;
        float degrees = MathUtils.getAngle(actor.getCenterX(),actor.getCenterY(),targetPoint.x,targetPoint.y);
        actor.turnDirection(degrees);
        oldPoint.set(actor.getPhysicsPosition());
        totalLen = oldPoint.dst2(targetPoint);
    }

    @Override
    protected boolean update(float delta) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            entity.doMoveAnimation();
            Vector2 targetP = TempObjects.temp0Vector2.set(targetPoint);
            Vector2 entityP = entity.getPhysicsPosition();
            Vector2 newP = targetP.sub(entityP)//得到实体到目标的向量
                    .nor()//归一化
                    .scl(JUMP_SPEED*delta)//增加速度
                    .add(entityP);//得到最终位置
            //速度太快可能穿过，做一次检查,如穿过则直接设置到目的地
            if(MathUtils.isBetween(oldPoint,targetPoint,newP)){
                newP.set(targetPoint.x,targetPoint.y);
                updateRelative(1-newP.dst2(targetPoint)/totalLen);
                entity.setPhysicsPosition(newP.x,newP.y);
                return true;
            }
            updateRelative(1-newP.dst2(targetPoint)/totalLen);
            entity.setPhysicsPosition(newP.x,newP.y);
            return false;
        }
        return true;
    }


    protected void updateRelative (float percent) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(percent < RISE_DURATION){
            float percentDelta = percent - lastPercent;
            float height = HEIGHT * percentDelta;
            float scale = SCALE * percentDelta;
            totalScale = totalScale+ scale;
            totalHight = totalHight+ height;
            entity.drawOffSetY = entity.drawOffSetY+ height;
            target.scaleBy(scale);
            lastPercent = percent;
        }else{
            float newPercent = (float) ((percent-RISE_DURATION)/DOWN_DURATION);
            float newPercentDelta = newPercent - lastPlusPercent;
            entity.drawOffSetY = entity.drawOffSetY - totalHight* (newPercentDelta);
            target.scaleBy(-totalScale * (newPercentDelta));
            lastPlusPercent = newPercent;
        }
    }

    /**
     * 0.7
     * 0.8  10
     *
     * 0.9
     */

    @Override
    protected void end() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        updateRelative(1);//确保执行完毕
        entity.stop();
        entity.collisionSize = 99;
        lastPercent = 0;
        lastPlusPercent = 0;
        totalScale = 0;
        totalHight = 0;
        //System.out.println("Current Scale:"+entity.getScaleX());//执行多次Scale会变化，虽然变化极小,误差是会累计
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
