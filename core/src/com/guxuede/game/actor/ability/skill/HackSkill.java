package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.ActorXXXTracksAction;
import com.guxuede.game.action.move.ActorMoveToAction;
import com.guxuede.game.action.move.ActorMoveToActorAction;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.AnimationProjection;
import com.guxuede.game.tools.MathUtils;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/9/27 .
 */
public class HackSkill extends Skill {

    int step = 0;

    public int getHotKey(){
        return Input.Keys.A;
    }

    public int getTargetType(){
        return TARGET_TYPE_TARGET;
    }

    @Override
    public boolean update(float delta) {
        if(targetEntry == null)return true;

        stateTime += delta;
        if(stateTime > 0 && step == 0){
            step ++;
            float degree = MathUtils.getAngle(owner.getCenterX(),owner.getCenterY(), targetEntry.getCenterX(), targetEntry.getCenterY());
            owner.turnDirection(degree);
        }else if(stateTime >= 2 && step == 1){
            step ++;
            throwHackingProjection();
        }else if(stateTime >= 3 && step == 2){
            return true;
        }
        return false;
    }

    public void throwHackingProjection(){
            float degree = MathUtils.getAngle(owner.getCenterX(),owner.getCenterY(), targetEntry.getCenterX(), targetEntry.getCenterY());
            owner.turnDirection(degree);
            final AnimationProjection projection = ActorFactory.createProjectionActor("BLANK", owner.getWorld());
            projection.collisionSize = 0;
            projection.sourceActor = owner;
            projection.setVisible(false);
            projection.setCenterPosition(owner.getCenterX(), owner.getCenterY());
            ActorMoveToActorAction actorMoveToAction = new ActorMoveToActorAction(targetEntry);
            projection.addAction(ActionsFactory.sequence(
                    actorMoveToAction,
                    ActionsFactory.actorDeathAnimation()));
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
     * 从指定位置抛射一个子弹发射到目标位置
     * @param fx
     * @param fy
     * @param dx
     * @param dy
     */
    public void throwHackingProjection(float fx,float fy,float dx,float dy){
        AnimationEntity animationEntity = owner;
        AnimationProjection projection = ActorFactory.createProjectionActor("BTNGhoulFrenzy", animationEntity.getWorld());
        projection.sourceActor = animationEntity;
        projection.setCenterPosition(fx, fy);
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
        animationEntity.getStage().addActor(projection);
    }

    @Override
    public void exit() {
        reset();
        super.exit();
    }

    @Override
    public void reset() {
        step = 0;
    }
}
