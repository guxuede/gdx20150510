package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.resource.ActorAnimationPlayer;

/**
 * Created by guxuede on 2016/5/25 .
 */
public class DecorationActor extends AnimationEntity {

    public DecorationActor(ActorAnimationPlayer animationPlayer, World world, InputListener l) {
        super(animationPlayer, world, l);
    }

    public DecorationActor(ActorAnimationPlayer animationPlayer, World world) {
        super(animationPlayer, world);
    }
}
