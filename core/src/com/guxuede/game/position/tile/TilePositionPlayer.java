package com.guxuede.game.position.tile;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.position.PositionPlayer;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class TilePositionPlayer implements PositionPlayer {

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public Vector2 getXY() {
        return null;
    }

    @Override
    public void setX(float x) {

    }

    @Override
    public void setY(float y) {

    }

    @Override
    public void setXY(float x, float y) {

    }

    @Override
    public void setXY(Vector2 vector2) {

    }

    @Override
    public void act(float delta, AnimationEntity entity) {

    }

    @Override
    public void init(AnimationEntity entity) {

    }

    @Override
    public void destroy(AnimationEntity entity) {

    }

    @Override
    public void setAwake(boolean b) {

    }

    @Override
    public void setLinearVelocity(Vector2 v) {

    }
}
