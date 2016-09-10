package com.guxuede.game.actor;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.GameWorld;
import com.guxuede.game.resource.ActorAnimationPlayer;

/**
 * Created by guxuede on 2016/5/31 .
 */
public class EffectsEntity extends AnimationEntity {
    public EffectsEntity(ActorAnimationPlayer animationPlayer, GameWorld world, InputListener l) {
        super(animationPlayer, world, l);
    }

    public EffectsEntity(ActorAnimationPlayer animationPlayer, GameWorld world) {
        super(animationPlayer, world);
    }

//    public void createBody(GameWorld world){
    //TODO 设置 子弹可以接受碰撞但没有物理反应。
//    }
}
