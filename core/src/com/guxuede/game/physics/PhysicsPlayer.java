package com.guxuede.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/9/10 .
 */
public interface PhysicsPlayer {



    public float getX();

    public float getY();

    /**
     * 返回位置信息
     * 只读，且不允许保存
     * @return
     */
    public Vector2 getXY();

    /**
     * 直接改变位置，无视物理限制
     * @param x
     */
    @Deprecated
    public void setX(float x);
    /**
     * 直接改变位置，无视物理限制
     * @param
     */
    @Deprecated
    public void setY(float y);
    /**
     * 直接改变位置，无视物理限制
     * @param
     */
    public void setXY(float x,float y);
    /**
     * 直接改变位置，无视物理限制
     * @param
     */
    public void setXY(Vector2 vector2);

    public void act(float delta,AnimationEntity entity);

    public void init(AnimationEntity entity);
    public void destroy(AnimationEntity entity);

    void setAwake(boolean b);

    void setLinearVelocity(Vector2 v);
}
