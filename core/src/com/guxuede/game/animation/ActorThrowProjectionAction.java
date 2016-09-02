package com.guxuede.game.animation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.AnimationProjection;
import com.guxuede.game.animation.move.ActorMoveToAction;
import com.guxuede.game.animation.move.ActorMoveToActorAction;
import com.guxuede.game.animation.move.ActorMoveToMutilActorRandomAction;
import com.guxuede.game.animation.move.ActorMoveToMutilPointAction;
import com.guxuede.game.effects.AnimationEffect;
import com.guxuede.game.effects.LightningEffect;
import com.guxuede.game.effects.LightningEffectMutilActor;
import com.guxuede.game.effects.LightningEffectMutilPoint;
import com.guxuede.game.tools.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guxuede on 2016/6/15 .
 */
public class ActorThrowProjectionAction extends Action {

    public static final int DEFAULT_PROJECT_RANGE = 400;

    @Override
    public boolean act(float delta) {
        throwProjection();
        //throwLightProjection();
        //throwLightProjectionMutilPoint();
        //throwLightProjectionMutilActor();
        return true;
    }


    /**************************************Projection control**************************************************/
    public void throwDivergenceProjection(){
        for(float d=0f;d < 360f;d=d+30){
            throwProjection(d);
        }
    }

    /**
     * 朝施法者方抛射子弹到鼠标指向的地方
     */
    public void throwProjectionToMouse(){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        throwProjection(animationEntity.degrees);
    }

    /**
     * 朝施法者面向的一方抛射子弹
     */
    public void throwProjection(){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        throwProjection(animationEntity.degrees);
    }
    /**
     * 朝指定的角度抛射子弹
     */
    public void throwProjection(float degrees){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        float l = DEFAULT_PROJECT_RANGE;
        double radians = (float) (2*Math.PI * degrees / 360);
        float dx=(float) (animationEntity.getEntityX()+l*Math.cos(radians));
        float dy=(float) (animationEntity.getEntityY()+l*Math.sin(radians));
        throwProjection(dx,dy);
    }

    /**
     * 向指定的坐标抛射子弹
     * @param dx
     * @param dy
     */
    public void throwProjection(float dx,float dy){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        throwProjection(animationEntity.getEntityX(), animationEntity.getEntityY(),dx,dy);
    }

    /**
     * 从指定位置抛射一个子弹发射到目标位置
     * @param fx
     * @param fy
     * @param dx
     * @param dy
     */
    public void throwProjection(float fx,float fy,float dx,float dy){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        AnimationProjection projection = ActorFactory.createProjectionActor("bullet1", animationEntity.body.getWorld(), null);
        projection.sourceActor = animationEntity;
        projection.setEntityPosition(fx, fy);
        //projection.turnDirection(animationEntity.degrees);
        ActorMoveToAction actorMoveToAction = new ActorMoveToMutilPointAction(Arrays.asList(new Vector2(dx,dy)));
        projection.addAction(
                ActionsFactory.sequence(ActionsFactory.scaleBy(5, 5, 0.1f),
                        ActionsFactory.scaleBy(-5, -5, 0.1f),
                        actorMoveToAction
                        , ActionsFactory.actorDeathAnimation()
                ));
        //projection.moveToPoint(dx,dy);

        //projection.moveToTarget();
        animationEntity.getStage().addActor(projection);
    }

    /**
     * 在多个单位来回多次的的子弹
     * @param fx
     * @param fy
     * @param dx
     * @param dy
     */
    public void throwProjection1(float fx,float fy,float dx,float dy){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        AnimationProjection projection = ActorFactory.createProjectionActor("bullet1", animationEntity.body.getWorld(), null);
        projection.sourceActor = animationEntity;
        projection.setEntityPosition(fx, fy);
        //projection.turnDirection(animationEntity.degrees);
        ActorMoveToAction actorMoveToAction = //new ActorMoveToActorAction(animationEntity.findClosestEntry()); new ActorMoveToMutilPointAction(Arrays.asList(new Vector2(100,100),new Vector2(200,200),new Vector2(500,200)));
                new ActorMoveToMutilActorRandomAction(5);
        projection.addAction(
                ActionsFactory.sequence(ActionsFactory.scaleBy(5, 5, 0.1f),
                        ActionsFactory.scaleBy(-5, -5, 0.1f),
                        actorMoveToAction
                        , ActionsFactory.actorDeathAnimation()
                ));
        //projection.moveToPoint(dx,dy);

    //projection.moveToTarget();
        animationEntity.getStage().addActor(projection);
    }

    public void throwLightProjection(){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        AnimationEntity targetEntity = animationEntity.findClosestEntry(null);
        LightningEffect lightningEffect = new LightningEffect("lightningLine",targetEntity);
        animationEntity.addAction(lightningEffect);
        targetEntity.addAction(new AnimationEffect("lightningAttacked"));
    }
    public void throwLightProjectionMutilPoint(){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        LightningEffectMutilPoint lightningEffect = new LightningEffectMutilPoint("lightningLine",Arrays.asList(new Vector2(100,100),new Vector2(200,200)));
        animationEntity.addAction(lightningEffect);
        //targetEntity.addAction(new AnimationEffect("lightningAttacked"));
    }

    public void throwLightProjectionMutilActor(){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        List<AnimationEntity> findList = new ArrayList<AnimationEntity>();
        MathUtils.findClosestEntry(animationEntity.getStage().getActors(),findList,animationEntity);
        LightningEffectMutilActor lightningEffect = new LightningEffectMutilActor("lightningLine1",findList);
        animationEntity.addAction(lightningEffect);
        //targetEntity.addAction(new AnimationEffect("lightningAttacked"));
    }

}
