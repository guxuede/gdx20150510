package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.guxuede.game.action.ActorThrowProjectionAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.state.ActorState;
import com.guxuede.game.actor.state.StandState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxuede on 2016/9/27 .
 */
public abstract class Skill implements Pool.Poolable{

    public static final int TARGET_TYPE_TARGET =0,TARGET_TYPE_AREA = 1;

    public AnimationEntity target;
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

    public void exit() {
        reset();
    }

    @Override
    public void reset() {
        target = null;
        targetPos = null;
        stateTime = 0;
    }
}
