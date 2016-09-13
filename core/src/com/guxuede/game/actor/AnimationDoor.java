package com.guxuede.game.actor;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.StageWorld;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.action.move.ActorSwitchStageAction;
import com.guxuede.game.resource.ActorAnimationPlayer;
import com.test.game.MutilStageGame;

/**
 * Created by guxuede on 2016/9/11 .
 */
public class AnimationDoor extends AnimationEntity {
    public String stageName ;
    public Vector2 point;

    public AnimationDoor(ActorAnimationPlayer animationPlayer, StageWorld world) {
        super(animationPlayer, world);
    }

    public void doDoor(AnimationEntity animationActor){
        ActorSwitchStageAction actorSwitchStageAction = new ActorSwitchStageAction(stageName,point.cpy());
        animationActor.addAction(ActionsFactory.sequence(actorSwitchStageAction));
        animationActor.addAction(new AnimationEffect("special10"));
    }
}
