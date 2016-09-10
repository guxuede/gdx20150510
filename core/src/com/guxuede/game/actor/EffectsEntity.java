package com.guxuede.game.actor;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.StageWorld;
import com.guxuede.game.resource.ActorAnimationPlayer;

/**
 * Created by guxuede on 2016/5/31 .
 */
public class EffectsEntity extends AnimationEntity {

    public EffectsEntity(ActorAnimationPlayer animationPlayer, StageWorld world, InputListener l) {
        super(animationPlayer, world, l);
        init();
    }

    public EffectsEntity(ActorAnimationPlayer animationPlayer, StageWorld world) {
        super(animationPlayer, world);
        init();
    }

    public void init(){
        isSensor = true;
    }
}
