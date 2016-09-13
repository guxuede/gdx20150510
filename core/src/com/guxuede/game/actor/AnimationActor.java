package com.guxuede.game.actor;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.StageWorld;
import com.guxuede.game.resource.ActorAnimationPlayer;

public class AnimationActor extends AnimationEntity{

    public AnimationActor(ActorAnimationPlayer animationPlayer, StageWorld world) {
        super(animationPlayer, world);
    }
}