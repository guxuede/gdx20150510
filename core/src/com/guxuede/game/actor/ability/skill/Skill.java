package com.guxuede.game.actor.ability.skill;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.guxuede.game.actor.AnimationEntity;

import static com.guxuede.game.actor.ability.skill.SkillTargetTypeEnum.TARGET_TYPE_ALL_TARGET;
import static com.guxuede.game.actor.ability.skill.SkillTargetTypeEnum.TARGET_TYPE_AREA;
import static com.guxuede.game.actor.ability.skill.SkillTargetTypeEnum.TARGET_TYPE_TARGET;

/**
 * Created by guxuede on 2016/9/27 .
 */
public abstract class Skill implements Pool.Poolable{

    public AnimationEntity targetEntry;
    public Vector2 targetPos;

    public int getHotKey(){
        return Input.Keys.A;
    }

    public SkillTargetTypeEnum getTargetType(){
        return SkillTargetTypeEnum.TARGET_TYPE_TARGET;
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

    public boolean verifyTarget(AnimationEntity animationEntity, Vector2 center, float r){
        if(getTargetType() == TARGET_TYPE_TARGET && animationEntity!=null){
            return true;
        }else if(getTargetType() == TARGET_TYPE_AREA && center!=null){
            return true;
        }else if(getTargetType() == TARGET_TYPE_ALL_TARGET && (animationEntity!=null || center!=null)){
            return true;
        }
        return false;
    }


    @Override
    public void reset() {
        targetEntry = null;
        targetPos = null;
        stateTime = 0;
    }
}
