package com.guxuede.game.actor;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.GameWorld;
import com.guxuede.game.resource.ActorAnimationPlayer;

/**
 * Created by guxuede on 2016/5/25 .
 */
public class DecorationActor extends AnimationEntity {

    public DecorationActor(ActorAnimationPlayer animationPlayer, GameWorld world, InputListener l) {
        super(animationPlayer, world, l);
    }

    public DecorationActor(ActorAnimationPlayer animationPlayer, GameWorld world) {
        super(animationPlayer, world);
    }
}
