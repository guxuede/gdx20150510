package com.guxuede.game.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guxuede.game.action.move.ActorMoveToActorAction;
import com.guxuede.game.action.move.ActorPathMoveAction;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.AnimationProjection;
import com.guxuede.game.action.move.ActorMoveToAction;
import com.guxuede.game.action.move.ActorMoveToMutilActorRandomAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.action.effects.LightningEffect;
import com.guxuede.game.action.effects.LightningEffectMutilActor;
import com.guxuede.game.action.effects.LightningEffectMutilPoint;
import com.guxuede.game.actor.EffectsEntity;
import com.guxuede.game.tools.MathUtils;
import com.guxuede.game.tools.TempObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.guxuede.game.action.ActionsFactory.*;
import static com.guxuede.game.actor.ActorFactory.createProjectionActor;

/**
 * Created by guxuede on 2016/6/15 .
 */
public class ActorThrowProjectionAction extends Action {

    public static final int DEFAULT_PROJECT_RANGE = 400;
    public AnimationEntity targetEntity;
    public Vector2 targetPos;

    @Override
    public boolean act(float delta) {
        if(targetEntity!=null){
            throwProjectionHackToTarget(targetEntity);
        }else if(targetPos != null){
            throwProjection(targetPos.x,targetPos.y);
        }else{
            throwProjection();
        }
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
        float dx;
        float dy;
        if(targetEntity==null){
            dx = (float) (animationEntity.getCenterX()+l*Math.cos(radians));
            dy = (float) (animationEntity.getCenterY()+l*Math.sin(radians));
        }else {
            dy = targetEntity.getCenterY();
            dx = targetEntity.getCenterX();
        }
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
        AnimationProjection projection = createProjectionActor("bullet1", animationEntity.getWorld());
        projection.sourceActor = animationEntity;
        projection.setCenterPosition(fx, fy);
        //projection.turnDirection(animationEntity.degrees);
        //ActorMoveToAction actorMoveToAction = new ActorMoveToMutilPointAction(Arrays.asList(new Vector2(dx,dy)));
        ActorPathMoveAction actorJumpAction1 = new ActorPathMoveAction(dx,dy);
        projection.addAction(
                sequence(scaleBy(5, 5, 0.1f),
                        scaleBy(-5, -5, 0.1f),
                        actorJumpAction1
                        , actorDeathAnimation()
                ));
        //projection.moveToPoint(dx,dy);

        //projection.moveToTarget();
        animationEntity.getStage().addActor(projection);
    }

    /**
     * 发射抛射物到目标单位，有追踪效果
     * @param target
     */
    public void throwProjectionToTarget(AnimationEntity target){
        AnimationEntity owner = (AnimationEntity) getActor();
        float degree = MathUtils.getAngle(owner.getCenterX(),owner.getCenterY(),target.getCenterX(),target.getCenterY());
        owner.turnDirection(degree);
        final AnimationProjection projection = createProjectionActor("bullet1", owner.getWorld());
        projection.collisionSize = 0;
        projection.sourceActor = owner;
        projection.setCenterPosition(owner.getCenterX(), owner.getCenterY());
        ActorMoveToActorAction actorMoveToAction = new ActorMoveToActorAction(target);
        projection.addAction(sequence(
                actorMoveToAction,
                actorDeathAnimation()));
        actorMoveToAction.actorMoveListener = new ActorMoveToAction.ActorMoveListener() {
            @Override
            public void onArrived(Vector2 target, Actor actor) {
                AnimationEntity targe = (AnimationEntity) actor;
                projection.doShowDamageEffect(targe, TempObjects.temp1Vector2.set(targe.getX(),targe.getY()));
            }
        };
        owner.getStage().addActor(projection);
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
        AnimationProjection projection = createProjectionActor("bullet1", animationEntity.getWorld());
        projection.sourceActor = animationEntity;
        projection.setCenterPosition(fx, fy);
        //projection.turnDirection(animationEntity.degrees);
        ActorMoveToAction actorMoveToAction = //new ActorMoveToActorAction(animationEntity.findClosestEntry()); new ActorMoveToMutilPointAction(Arrays.asList(new Vector2(100,100),new Vector2(200,200),new Vector2(500,200)));
                new ActorMoveToMutilActorRandomAction(5);
        projection.addAction(
                sequence(scaleBy(5, 5, 0.1f),
                        scaleBy(-5, -5, 0.1f),
                        actorMoveToAction
                        , actorDeathAnimation()
                ));
        //projection.moveToPoint(dx,dy);

    //projection.moveToTarget();
        animationEntity.getStage().addActor(projection);
    }

    /**
     * 发射抛射物到目标单位，有追踪效果
     * @param target
     */
    public void throwProjectionHackToTarget(AnimationEntity target) {
        createProjectionActor("project1", (AnimationEntity) getActor())
                .faceToTarget(target)
                .addAllAction(
                        sequence(
                            scaleBy(5, 5, 0.2f),
                            parallel(
                                    scaleBy(-2, -2, 0.1f),
                                    sequence(
                                            new ActorMoveToActorAction(target),
                                            parallel(
                                                    scaleBy(5, 5, 0.2f),
                                                    fadeOut(2),
                                                    actorDeathAnimation()
                                            )
                                    )
                            )
                        )
                )
                .addToStage();
    }

    /**
     * 发射闪电
     */
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

    @Override
    public void reset() {
        super.reset();
        targetEntity = null;
        targetPos = null;
    }
}
