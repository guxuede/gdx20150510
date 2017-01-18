package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.ActorJumpAction1;

/**
 * Created by guxuede on 2016/10/7 .
 */
public class JumpSkill extends Skill {


    public int getHotKey(){
        return Input.Keys.Q;
    }

    public int getTargetType(){
        return TARGET_TYPE_TARGET;
    }


    @Override
    public boolean update(float delta) {
        if(targetPos == null)return true;
        boolean targetIsClear = owner.getWorld().getPhysicsManager().pointIsClear(targetPos);
        if(targetIsClear){
            owner.addAction(new ActorJumpAction1(targetPos));
            //owner.addAction(ActionsFactory.actorJumpAction(targetPos.x,targetPos.y));
        }
        return true;
    }
}
