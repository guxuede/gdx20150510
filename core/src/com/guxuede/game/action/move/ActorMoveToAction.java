package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;
import com.guxuede.game.tools.MathUtils;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/7/14 .
 */
public abstract class ActorMoveToAction extends GdxAction {
    public static final int IS_ARRIVE_RADIO = 10;
    public ActorMoveListener actorMoveListener;
    private Vector2 oldPoint = new Vector2();


    @Override
    protected void begin() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(entity.getPhysicsPlayer()!=null){
            oldPoint.set(entity.getPhysicsPosition());
        }else{
            oldPoint.set(entity.getCenterX(),entity.getCenterY());
        }
    }

    @Override
    protected boolean update(float delta) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            final Vector2 target = getTargetPoint();
            final Vector2 entryPos = entity.getPhysicsPosition();
            final float degrees = MathUtils.getAngle(entryPos,target);
            entity.isMoving = true;
            entity.turnDirection(degrees);
            entity.doMoveAnimation();
//            entity.doMoveAnimation();
//            Vector2 targetP = TempObjects.temp0Vector2.set(targetEntry);
//            Vector2 entityP = entity.getPhysicsPosition();
//            Vector2 newP = targetP.sub(entityP)//得到实体到目标的向量
//                    .nor()//归一化
//                    .scl(20f)//增加速度
//                    .add(entityP);//得到最终位置
//            entity.setPhysicsPosition(newP.x,newP.y);
//            //速度太快可能穿过，做一次检查
//            if(MathUtils.isBetween(oldPoint,targetEntry,newP)){
//                return true;
//            }
            return false;
        }
        entity.stop();
        return true;
    }

    @Override
    protected void end() {
        super.end();
    }

    protected boolean isArrive() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        final Vector2 target = getTargetPoint();
        if(target == null){
            return true;
        }else{
            float dist = target.dst(entity.getPhysicsPosition());
            return dist < IS_ARRIVE_RADIO;
        }
    }

    public void onArrived(){

    }


    public abstract Vector2 getTargetPoint();

    public static interface ActorMoveListener{
        void onArrived(Vector2 target,Actor actor);
    }
}
