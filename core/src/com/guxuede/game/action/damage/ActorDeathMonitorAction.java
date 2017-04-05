package com.guxuede.game.action.damage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;

/**
 * 一直运行在单位身上的action，监听单位HP是否低于0，如果低于0，则判定单位死亡
 * Created by guxuede on 2017/4/5 .
 */
public class ActorDeathMonitorAction extends GdxAction {

    AnimationEntity targetEntity;

    @Override
    protected boolean update(float delta) {
        if(targetEntity.currentHitPoint < 0){
            targetEntity.currentHitPoint = 0;
            targetEntity.addAction(ActionsFactory.actorDeathAnimation());
            return true;
        }
        return false;
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
        targetEntity = (AnimationEntity) actor;
    }

    @Override
    public void reset() {
        super.reset();
        target = null;
    }
}
