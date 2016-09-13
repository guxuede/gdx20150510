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

    private String stageName ;
    private Vector2 point;


    public ActorSwitchStageAction(String stageName, Vector2 point) {
        this.stageName = stageName;
        this.point = point;
    }

    @Override
    public boolean act(float delta) {
        if(target instanceof AnimationEntity){
            AnimationEntity entity = (AnimationEntity) target;
            entity.getWorld().getGame().transferActorToStage(entity,stageName,point);
        }
        return true;
    }
}
