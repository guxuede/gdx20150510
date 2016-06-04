package com.guxuede.game.libgdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * 增强action使作用对象有动画效果，可以draw
 * Created by guxuede on 2016/6/2 .
 */
public class GdxEffect extends TemporalAction {

    @Override
    protected void update(float percent) {

    }

    public void drawFootEffect(Batch batch, float parentAlpha){

    }
    public void drawBodyEffect(Batch batch, float parentAlpha){

    }
    public void drawHeadEffect(Batch batch, float parentAlpha){

    }

    protected  AnimationEntity getAnimationEntity(){
        return (AnimationEntity) target;
    }

}
