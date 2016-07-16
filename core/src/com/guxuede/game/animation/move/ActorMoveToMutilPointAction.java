package com.guxuede.game.animation.move;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by guxuede on 2016/7/16 .
 */
public class ActorMoveToMutilPointAction extends ActorMoveToMutilAction {

    private Iterator<Vector2> iterator;
    private Vector2 currentPoint;

    public ActorMoveToMutilPointAction(List<Vector2> points) {
        this.iterator = points.listIterator();
    }

    @Override
    public boolean haveNextTarget() {
        return iterator.hasNext();
    }

    @Override
    public void moveToNextTarget() {
        currentPoint = iterator.next();
    }

    @Override
    public Vector2 getCurrentTarget() {
        return currentPoint;
    }
}
