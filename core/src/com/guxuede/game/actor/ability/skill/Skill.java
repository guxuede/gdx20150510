package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/9/27 .
 */
public abstract class Skill implements Pool.Poolable{

    public static final int TARGET_TYPE_TARGET =0,TARGET_TYPE_AREA = 1;

    public AnimationEntity targetEntry;
    public Vector2 targetPos;

    public int getHotKey(){
        return Input.Keys.A;
    }

    public int getTargetType(){
        return TARGET_TYPE_TARGET;
    }
    /**技能拥有者*/
    public AnimationEntity owner;
    public float stateTime;


    public boolean update(float delta) {

        return false;
    }

    public void enter(){

    }

    public void exit() {
        reset();
    }

    @Override
    public void reset() {
        targetEntry = null;
        targetPos = null;
        stateTime = 0;
    }
}
