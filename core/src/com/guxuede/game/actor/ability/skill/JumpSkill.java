package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.guxuede.game.action.ActionsFactory;

/**
 * Created by guxuede on 2016/10/7 .
 */
public class JumpSkill extends Skill {


    public int getHotKey(){
        return Input.Keys.Q;
    }

    public int getTargetType(){
        return TARGET_TYPE_AREA;
    }


    @Override
    public boolean update(float delta) {
        if(targetPos == null)return true;

        owner.addAction(ActionsFactory.actorJumpAction(targetPos.x,targetPos.y));
        return true;
    }
}
