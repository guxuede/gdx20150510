package com.guxuede.game.actor.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/6/15 .
 */
public class MoveState extends StandState {

    public MoveState(int direction){
        super(direction);
    }

    @Override
    public void enter(AnimationEntity entity, InputEvent event) {
        if(event.getType()== InputEvent.Type.touchDown){
            entity.moveToPoint(event.getStageX(), event.getStageY());
            this.direction = entity.direction;//修正direction，应该重构造方法里传入？
        }else{
            entity.move(direction);
        }
    }

    @Override
    public ActorState handleInput(AnimationEntity entity, InputEvent event) {
        ActorState actorState = super.handleInput(entity, event);
        if(actorState!=null){
            return actorState;
        }
        Integer standCode = null;
        if(InputEvent.Type.keyUp == event.getType() && ( standCode = convertKeyToDirection(event.getKeyCode()))!=null){
            Integer moveDirection = getMovePressPressKeyDirection();
            //如果没有方向键被按住,那么角色停止
            if(moveDirection==null){
                return new StandState(standCode);
            }else if(moveDirection != direction){
                //如果有方向键被按住,且不一样的方向,那么重新走
                return new MoveState(moveDirection);
            }
        }

        return null;
    }

    /**
     * 去继续检查是否任然有方向键被按住
     * @return
     */
    public Integer getMovePressPressKeyDirection(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            return AnimationEntity.UP;
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            return AnimationEntity.DOWN;
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            return AnimationEntity.RIGHT;
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            return AnimationEntity.LEFT;
        }
        return null;
    }

    public Integer convertKeyToDirection(int keyCode){
        if (Input.Keys.RIGHT == keyCode){
            return AnimationEntity.RIGHT;
        }else if(Input.Keys.UP == keyCode){
            return AnimationEntity.UP;
        }else if(Input.Keys.DOWN == keyCode){
            return AnimationEntity.DOWN;
        }else if(Input.Keys.LEFT == keyCode){
            return AnimationEntity.LEFT;
        }
        return null;
    }
}
