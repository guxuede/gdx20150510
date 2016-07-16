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

    @Override
    public boolean haveNextTarget() {
        System.out.println("haveNextTarget");
        if(currentTime < times){
            AnimationEntity spellCaster = (AnimationEntity) getTarget();
            AnimationEntity find = currentEntity != null ?
                    currentEntity.findClosestEntry(Arrays.asList(spellCaster))
                    : spellCaster.findClosestEntry(null);
            return find!=null;
        }
        return false;
    }

    @Override
    public void moveToNextTarget() {
        System.out.println("moveToNextTarget");
        AnimationEntity spellCaster = (AnimationEntity) getTarget();
        AnimationEntity find = currentEntity != null ?
                currentEntity.findClosestEntry(Arrays.asList(spellCaster))
                : spellCaster.findClosestEntry(null);
        currentEntity = find;
        currentTime ++;
    }

    @Override
    public Vector2 getCurrentTarget() {
        return currentEntity==null?null:new Vector2(currentEntity.getEntityX(),currentEntity.getEntityY());
    }
}
