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
    public String stageName = MathUtils.randomBoolean()?"desert1.tmx":"desert.tmx";
    public Vector2 point = new Vector2(MathUtils.random(-100,1000),MathUtils.random(-100,1000));

    public AnimationDoor(ActorAnimationPlayer animationPlayer, StageWorld world, InputListener l) {
        super(animationPlayer, world, l);
    }

    public AnimationDoor(ActorAnimationPlayer animationPlayer, StageWorld world) {
        super(animationPlayer, world);
    }

    public void doDoor(AnimationEntity animationActor){
        ActorSwitchStageAction actorSwitchStageAction = new ActorSwitchStageAction();
        animationActor.addAction(ActionsFactory.sequence(actorSwitchStageAction,new AnimationEffect("special10")));
        animationActor.addAction(new AnimationEffect("special10"));
        //MutilStageGame.actorToStage(animationActor,stageName,point);
    }
}
