package com.guxuede.game.actor.state;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/6/15 .
 */
public class ActorState  {

    public ActorState handleInput(AnimationEntity entity ,InputEvent event){
        return null;
    }

    public ActorState update(AnimationEntity entity,float delta){
        return null;
    }

    public void enter(AnimationEntity entity , InputEvent event){

    }

    public void exit(AnimationEntity entity ){

    }
}
