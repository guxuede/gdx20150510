package com.guxuede.game.actor.state;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.action.ActorThrowProjectionAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.actor.ability.skill.HackSkill;
import com.guxuede.game.actor.ability.skill.MagicSkill;
import com.guxuede.game.actor.ability.skill.Skill;

/**
 * Created by guxuede on 2016/6/16 .
 */
public class AttackState extends StandState {

    AnimationEntity target;
    public Skill skill;

    public AttackState(int direction){
        super(direction);
    }

    @Override
    public void enter(AnimationEntity entity, InputEvent event) {
        entity.stop();
    }

    @Override
    public ActorState update(AnimationEntity entity,float delta) {
        boolean result = skill ==null?true:skill.update(delta);
        if(result){
            return new StandState(direction);
        }
        return null;
    }

    @Override
    public void exit(AnimationEntity entity) {
        if(skill!=null){
            skill.exit();
            skill = null;
        }
    }
}
