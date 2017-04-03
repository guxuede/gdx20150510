package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.GdxSequenceAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.action.move.ActorMoveToActorAction;
import com.guxuede.game.actor.AnimationEntity;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.guxuede.game.action.ActionsFactory.actorDeathAnimation;
import static com.guxuede.game.action.ActionsFactory.gdxSequence;
import static com.guxuede.game.actor.ActorFactory.createProjectionActor;

/**
 * Created by guxuede on 2016/9/27 .
 */
public class MagicSkill extends Skill {

    GdxSequenceAction action;

    public int getHotKey() {
        return Input.Keys.B;
    }

    public int getTargetType() {
        return TARGET_TYPE_TARGET;
    }

    @Override
    public void enter() {
        final AnimationEntity targetEntry = this.targetEntry;
        action = gdxSequence(gdxSequence(ActionsFactory.magicShake()),gdxSequence(new AnimationEffect("lightningSpell")), new Action() {
            @Override
            public boolean act(float delta) {
                createProjectionActor("project1", (AnimationEntity) getActor())
                        .faceToTarget(targetEntry)
                        .addAllAction(
                                sequence(
                                        scaleBy(5, 5, 0.2f),
                                        parallel(
                                                scaleBy(-2, -2, 0.1f),
                                                sequence(
                                                        new ActorMoveToActorAction(targetEntry),
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
                return true;
            }
        });
        owner.faceToTarget(targetEntry).addAction(action);
    }

    @Override
    public boolean update(float delta) {
        return action.isAllGDXActionEnd();
//        stateTime += delta;
//        if(stateTime > 0 && step == 0){
//            step ++;
//            if(targetEntry!=null){
//                owner.faceToTarget(targetEntry);
//            }
//            animationEffect = new AnimationEffect("lightningSpell");
//            //owner.addAction(animationEffect);
//            owner.addAction(ActionsFactory.magicShake());
//            animationDuration =animationEffect.getDuration();
//        }else if(stateTime >= animationDuration && step == 1){
//            step ++;
//            ActorThrowProjectionAction ta = new ActorThrowProjectionAction();
//            ta.targetEntity = targetEntry;
//            ta.targetPos = targetPos==null?null:targetPos.cpy();
//            owner.addAction(ta);
//
////            if(targetPos!=null){
////                EffectsEntity effectsEntity = ActorFactory.createEffectsActor("wind2",owner.getWorld());
////                effectsEntity.setCenterPosition(targetPos.x,targetPos.y);
////                effectsEntity.addAction(ActionsFactory.delay(2f,ActionsFactory.actorDeathAnimation()));
////                owner.getWorld().getStage().addActor(effectsEntity);
////            }
//            return true;
//        }
//        return false;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void exit() {
        super.exit();
        //如果SkillAction（技能前摇），还有的没结束，直接就结束。如果已经结束，就不要remove了，让其他的继续执行。前摇是可以打断的
        if (action != null && !action.isAllGDXActionEnd()) {
            action.stopToThisAction();
        }
    }
}
