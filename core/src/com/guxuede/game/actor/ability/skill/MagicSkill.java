package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.guxuede.game.action.GdxSequenceAction;
import com.guxuede.game.actor.AnimationEntity;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.guxuede.game.action.ActionsFactory.*;
import static com.guxuede.game.actor.ability.skill.SkillTargetTypeEnum.TARGET_TYPE_TARGET;

/**
 * Created by guxuede on 2016/9/27 .
 */
public class MagicSkill extends Skill {

    GdxSequenceAction action;

    public int getHotKey() {
        return Input.Keys.B;
    }

    public SkillTargetTypeEnum getTargetType() {
        return TARGET_TYPE_TARGET;
    }

    @Override
    public void enter() {
        final AnimationEntity targetEntry = this.targetEntry;
        action = gdxSequence(
                gdxSequence(magicShake()),
                gdxSequence(animationEffect("lightningSpell")),
                newProjectionAction("project1",targetEntry,
                        sequence(
                                scaleBy(5, 5, 0.2f),
                                parallel(
                                        scaleBy(-2, -2, 0.1f),
                                        sequence(
                                                actorMoveToActorAction(targetEntry),
                                                parallel(
                                                        scaleBy(5, 5, 0.2f),
                                                        fadeOut(2),
                                                        actorDeathAnimation()
                                                )
                                        )
                                )
                        )
                )
        );
        owner.faceToTarget(targetEntry).addAction(action);
    }

    @Override
    public boolean update(float delta) {
        return action.isAllGDXActionEnd();
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
