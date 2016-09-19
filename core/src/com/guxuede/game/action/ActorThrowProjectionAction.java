package com.guxuede.game.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.AnimationProjection;
import com.guxuede.game.action.move.ActorMoveToAction;
import com.guxuede.game.action.move.ActorMoveToMutilActorRandomAction;
import com.guxuede.game.action.move.ActorMoveToMutilPointAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.action.effects.LightningEffect;
import com.guxuede.game.action.effects.LightningEffectMutilActor;
import com.guxuede.game.action.effects.LightningEffectMutilPoint;
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
        throwHacking();
        //throwProjection();
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

    public void throwHacking(){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        float degrees = animationEntity.degrees;
        float l = 30;
        double radians = (float) (2*Math.PI * degrees / 360);
        float dx=(float) (animationEntity.getCenterX()+l*Math.cos(radians));
        float dy=(float) (animationEntity.getCenterY()+l*Math.sin(radians));
        throwHackingProjection(animationEntity.getCenterX(),animationEntity.getCenterY(),dx,dy);
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
        float dx=(float) (animationEntity.getCenterX()+l*Math.cos(radians));
        float dy=(float) (animationEntity.getCenterY()+l*Math.sin(radians));
        throwProjection(dx,dy);
    }

    /**
     * 向指定的坐标抛射子弹
     * @param dx
     * @param dy
     */
    public void throwProjection(float dx,float dy){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        throwProjection(animationEntity.getCenterX(), animationEntity.getCenterY(),dx,dy);
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
        AnimationProjection projection = ActorFactory.createProjectionActor("bullet1", animationEntity.getWorld());
        projection.sourceActor = animationEntity;
        projection.setCenterPosition(fx, fy);
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
     * 从指定位置抛射一个子弹发射到目标位置
     * @param fx
     * @param fy
     * @param dx
     * @param dy
     */
    public void throwHackingProjection(float fx,float fy,float dx,float dy){
        AnimationEntity animationEntity = (AnimationEntity) getActor();
        AnimationProjection projection = ActorFactory.createProjectionActor("BTNGhoulFrenzy", animationEntity.getWorld());
        projection.sourceActor = animationEntity;
        projection.setCenterPosition(fx, fy);
        //ActorJumpAction jumpAction = ActionsFactory.actorJumpAction(dx,dy);
        ActorXXXTracksAction jumpAction = new ActorXXXTracksAction();
        float duration = 1;
        jumpAction.setDuration(duration);
        jumpAction.degree = animationEntity.degrees;
        projection.addAction(
                ActionsFactory.parallel(
                        ActionsFactory.sequence(
                                ActionsFactory.scaleTo(0.4f,0.4f),
                                ActionsFactory.scaleTo(1f,1f, duration/2),
                                ActionsFactory.scaleTo(0f, 0f, duration/2)
                        ),
                        ActionsFactory.sequence(
                                jumpAction,
                                ActionsFactory.actorDeathAnimation())
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
        AnimationProjection projection = ActorFactory.createProjectionActor("bullet1", animationEntity.getWorld());
        projection.sourceActor = animationEntity;
        projection.setCenterPosition(fx, fy);
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
