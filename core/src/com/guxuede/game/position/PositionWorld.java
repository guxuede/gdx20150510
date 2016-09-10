package com.guxuede.game.position;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/9/10 .
 */
public interface PositionWorld {
    public void act(float delta);

    public boolean shouldCollide(AnimationEntity actorA, AnimationEntity actorB) ;

    public void collide(AnimationEntity actorA, AnimationEntity actorB) ;

    public PositionPlayer createPositionPlayer();

}
