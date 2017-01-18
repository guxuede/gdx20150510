package com.guxuede.game.actor.state;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.guxuede.game.MouseManager;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.ability.skill.Skill;

/**
 * Created by guxuede on 2016/6/15 .
 */
public class StandState extends ActorState {

    int direction;


    public StandState(int direction){
        this.direction = direction;
    }

    @Override
    public void enter(AnimationEntity entity, InputEvent event) {
        entity.stop();
    }

    @Override
    public ActorState handleInput(final AnimationEntity entity, InputEvent event) {
        if(InputEvent.Type.keyDown == event.getType()){
            if (Input.Keys.RIGHT == event.getKeyCode()){
                return new MoveState(AnimationEntity.RIGHT);
            }else if(Input.Keys.UP == event.getKeyCode()){
                return new MoveState(AnimationEntity.UP);
            }else if(Input.Keys.DOWN == event.getKeyCode()){
                return new MoveState(AnimationEntity.DOWN);
            }else if(Input.Keys.LEFT == event.getKeyCode()){
                return new MoveState(AnimationEntity.LEFT);
            }else if(Input.Keys.SPACE == event.getKeyCode()){
                return new AttackState(direction);
            }
            for (final Skill skill : entity.skills) {
                if (skill.getHotKey() == event.getKeyCode()) {
                    MouseManager.MouseIndicatorListener listener = new MouseManager.MouseIndicatorListener() {
                        @Override
                        public boolean onHoner(AnimationEntity animationEntity, Vector2 center, float r) {
                            return true;
                        }
                        @Override
                        public void onActive(AnimationEntity animationEntity, Vector2 center, float r) {
                            skill.target = animationEntity;
                            skill.owner = entity;
                            skill.targetPos = center==null?null:center.cpy();
                            AttackState actorState = new AttackState(direction);
                            actorState.skill = skill;
                            entity.goingToNewState(actorState,null);
                        }
                    };
                    if (skill.getTargetType() == Skill.TARGET_TYPE_AREA) {
                        entity.getWorld().getMouseManager().enterToAreaChoiceStatus(100, listener);
                    } else {
                        entity.getWorld().getMouseManager().enterToTargetChoiceStatus(listener);
                    }
                    break;
                }
            }
        }else if(event.getType() == InputEvent.Type.touchDown){
            Actor tactor = event.getTarget();
            if(tactor!=null && tactor instanceof AnimationActor && tactor!=entity){
                if(!(this instanceof AttackState) ){
                    AttackState as = new AttackState(direction);
                    as.target = (AnimationEntity) tactor;
                    return as;
                }
            }else{
                return new MoveState(AnimationEntity.DOWN);
            }
        }
        return null;
    }


}
