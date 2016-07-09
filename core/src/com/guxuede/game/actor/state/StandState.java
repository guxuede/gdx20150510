package com.guxuede.game.actor.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.guxuede.game.actor.AnimationEntity;

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
    public ActorState handleInput(AnimationEntity entity, InputEvent event) {
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
        }else if(event.getType() == InputEvent.Type.touchDown){
            return new MoveState(AnimationEntity.DOWN);
        }
        return null;
    }


}
