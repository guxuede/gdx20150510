package com.guxuede.game.action.move;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/9/11 .
 */
public class ActorSwitchStageAction extends Action {

    @Override
    public boolean act(float delta) {
        if(target instanceof AnimationEntity){

        }
        return true;
    }
}
