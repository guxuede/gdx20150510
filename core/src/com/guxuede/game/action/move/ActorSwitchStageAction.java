package com.guxuede.game.action.move;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.AnimationEntity;
import com.test.game.MutilStageGame;
import com.test.game.TitleMapStage;

/**
 * Created by guxuede on 2016/9/11 .
 */
public class ActorSwitchStageAction extends Action {

    private int step = 0 ;


    @Override
    public boolean act(float delta) {
        if(target instanceof AnimationEntity){
            AnimationEntity entity = (AnimationEntity) target;
            MutilStageGame.actorToStage(entity,MathUtils.randomBoolean()?"desert1.tmx":"desert.tmx",new Vector2(MathUtils.random(-100,1000),MathUtils.random(-100,1000)));
        }
        return true;
    }
}
