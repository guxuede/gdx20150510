package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.guxuede.game.tools.TempObjects;

import java.util.Iterator;
import java.util.List;

/**
 * Created by guxuede on 2016/7/16 .
 */
public class ActorMoveToPathAction extends ActorMoveToMutilAction {


    private IntArray path;
    private int currentPoint = 0;

    public ActorMoveToPathAction(IntArray path) {
        this.path = path;
        for(int i = 0;i<path.size;i++){
            path.set(i,path.get(i)*32);
        }
        currentPoint = (path.size > 0 )? path.size-2 : -1;
    }

    @Override
    public boolean haveNextTarget() {
        return currentPoint-2 >= 0 ;
    }

    @Override
    public void moveToNextTarget() {
        currentPoint-=2;
    }

    @Override
    public Vector2 getCurrentTarget() {
        return currentPoint == -1?null:TempObjects.temp2Vector2.set(path.get(currentPoint)+16,path.get(currentPoint+1)+16);
    }
}
