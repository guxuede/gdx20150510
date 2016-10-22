package com.guxuede.game.actor.ability.buffer;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;

/**
 * Created by guxuede on 2016/10/20 .
 * 1.每固定几秒 扣掉固定几滴血
 * 2.
 */
public class PoisonBuffer extends RelativeTemporalAction{

    AnimationEntity targetEntity;

    public static final float PER_SECOND = 1;
    public static final float PER_MILLISECOND = 1;

    private float TRIGGER_INTERVAL = PER_SECOND;
    private float lastTime = 0;

    public PoisonBuffer(){

    }

    @Override
    protected void begin() {
        super.begin();
        targetEntity = (AnimationEntity) target;
    }

    @Override
    protected void updateRelative(float percentDelta) {
        targetEntity.currentHitPoint -= (20*percentDelta);
    }
//
//    @Override
//    protected void update(float percent) {
//        float time = getTime();
//        if(time - lastTime >= TRIGGER_INTERVAL){
//            lastTime = time;
//            update(percent,time/TRIGGER_INTERVAL);
//        }
//        System.out.println("time = [" + time + "]");
//    }
//
//    public void update(float percent,float timeInUnit){
//        System.out.println("=================percent = [" + percent + "], timeInUnit = [" + timeInUnit + "]");
//        targetEntity.currentHitPoint -= 10;
//    }

    @Override
    protected void end() {
        super.end();
        targetEntity = null;
    }
}
