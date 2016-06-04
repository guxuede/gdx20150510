package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.resource.ActorAnimationPlayer;

public class AnimationActor extends AnimationEntity{


    public AnimationActor(ActorAnimationPlayer animationPlayer, World world, InputListener l) {
        super(animationPlayer, world, l);
    }

    public AnimationActor(ActorAnimationPlayer animationPlayer, World world) {
        super(animationPlayer, world);
    }
}