package com.guxuede.game.animation.move;

import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;

import java.util.Iterator;
import java.util.List;

/**
 * Created by guxuede on 2016/7/16 .
 */
public class ActorMoveToMutilActorAction extends ActorMoveToMutilAction{

    private Iterator<AnimationEntity> iterator;
    private AnimationEntity currentEntity;


    public ActorMoveToMutilActorAction(List<AnimationEntity> currentEntity) {
        this.iterator = currentEntity.listIterator();
    }

    @Override
    public boolean haveNextTarget() {
        return iterator.hasNext();
    }

    @Override
    public void moveToNextTarget() {
        currentEntity = iterator.next();
    }

    @Override
    public Vector2 getCurrentTarget() {
        return currentEntity==null?null:new Vector2(currentEntity.getCenterX(),currentEntity.getCenterY());
    }
}
