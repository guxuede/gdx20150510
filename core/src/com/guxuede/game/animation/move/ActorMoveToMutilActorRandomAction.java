package com.guxuede.game.animation.move;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guxuede on 2016/7/16 .
 */
public class ActorMoveToMutilActorRandomAction extends ActorMoveToMutilAction{

    int times;
    int currentTime;
    AnimationEntity currentEntity;

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
    public Vector2 getCurrentTarget() {
        return currentEntity==null?null:new Vector2(currentEntity.getEntityX(),currentEntity.getEntityY());
    }
}
