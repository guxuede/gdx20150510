package com.guxuede.game.actor;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.GameWorld;
import com.guxuede.game.resource.ActorAnimationPlayer;

public class AnimationActor extends AnimationEntity{


    public AnimationActor(ActorAnimationPlayer animationPlayer, GameWorld world, InputListener l) {
        super(animationPlayer, world, l);
    }

    public AnimationActor(ActorAnimationPlayer animationPlayer, GameWorld world) {
        super(animationPlayer, world);
    }
}