package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.guxuede.game.action.ActorJumpAction1;

import static com.guxuede.game.actor.ability.skill.SkillTargetTypeEnum.TARGET_TYPE_AREA;

/**
 * Created by guxuede on 2016/10/7 .
 */
public class JumpSkill extends Skill {


    public int getHotKey(){
        return Input.Keys.Q;
    }

    public SkillTargetTypeEnum getTargetType(){
        return TARGET_TYPE_AREA;
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
