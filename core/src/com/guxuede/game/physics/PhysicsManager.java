package com.guxuede.game.physics;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/9/10 .
 */
public interface PhysicsManager {
    public void act(float delta);

    public boolean shouldCollide(AnimationEntity actorA, AnimationEntity actorB) ;

    public void collide(AnimationEntity actorA, AnimationEntity actorB) ;

    public PhysicsPlayer createPositionPlayer();

    /**寻路 start和target时舞台坐标**/
    public IntArray getAstarPath(Vector2 start, Vector2 target);
    /**指定点是否可通行**/
    public boolean pointIsClear(Vector2 point);

    public void onMapLoad(TiledMap map);

    public void render();

}
