package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;

import java.util.Arrays;

/**
 * Created by guxuede on 2016/7/16 .
 */
public class ActorMoveToMutilActorRandomAction extends ActorMoveToMutilAction{

    int times;
    int currentTime;
    AnimationEntity currentEntity;

    public ActorMoveToMutilActorRandomAction(){

    }

    public ActorMoveToMutilActorRandomAction(int times) {
        this.times = times;
    }

    private AnimationEntity findNextTarget(){
        AnimationEntity me = ((AnimationEntity) getTarget());
        AnimationEntity spellCaster = me.sourceActor;
        AnimationEntity find
                = currentEntity != null
                ? currentEntity.findClosestEntry(Arrays.asList(me,spellCaster))
                : me.findClosestEntry(Arrays.asList(spellCaster));
        return find;
    }

    @Override
    public boolean haveNextTarget() {
        if(currentTime < times){
            return findNextTarget()!=null;
        }
        return false;
    }

    @Override
    public void moveToNextTarget() {
        AnimationEntity find = findNextTarget();
        currentEntity = find;
        currentTime ++;
    }

    @Override
    public void onArrived() {
        if(actorMoveListener!=null){
            actorMoveListener.onArrived(null,currentEntity);
        }
    }


    @Override
    public Vector2 getCurrentTarget() {
        return currentEntity==null?null:new Vector2(currentEntity.getCenterX(),currentEntity.getCenterY());
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public void reset() {
        super.reset();
        times = 0;
        currentTime = 0;
        currentEntity = null;
    }
}
