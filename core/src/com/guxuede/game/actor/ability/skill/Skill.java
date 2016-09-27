package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.utils.Pool;
import com.guxuede.game.action.ActorThrowProjectionAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.state.ActorState;
import com.guxuede.game.actor.state.StandState;

/**
 * Created by guxuede on 2016/9/27 .
 */
public class Skill implements Pool.Poolable{

    public AnimationEntity owner;
    public float stateTime;


    public boolean update(float delta) {

        return false;
    }

    public void exit() {

    }

    @Override
    public void reset() {

    }
}
