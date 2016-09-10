package com.guxuede.game.physics;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/9/10 .
 */
public interface PhysicsManager {
    public void act(float delta);

    public boolean shouldCollide(AnimationEntity actorA, AnimationEntity actorB) ;

    public void collide(AnimationEntity actorA, AnimationEntity actorB) ;

    public PhysicsPlayer createPositionPlayer();

    public void onMapLoad(TiledMap map);

    public void render();

}
