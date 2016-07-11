package com.guxuede.game.animation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by guxuede on 2016/6/20 .
 */
public class ActorPathMoveAction extends Action {

    public static final int IS_ARRIVE_RADIO = 10;
    private List<Vector2> paths = Arrays.asList(new Vector2(400,400),new Vector2(100,200));

    public AnimationEntity target;

    private Vector2 currentPath;
    private int currentPathIndex=-1;

    public ActorPathMoveAction(){
        currentPathIndex = 0;
        currentPath = paths.get(currentPathIndex);
    }

    public ActorPathMoveAction(float x,float y){
        currentPathIndex = 0;
        paths = Arrays.asList(new Vector2(x, y));
        currentPath = paths.get(currentPathIndex);
    }

    public ActorPathMoveAction(AnimationEntity entity){
        target = entity;
    }

    @Override
    public boolean act(float delta) {
        AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            //if(!entity.isMoving){
            entity.isMoving = true;
            entity.turnDirection(MathUtils.getAngle(entity.getEntityX(),entity.getEntityY(),getTargetX(),getTargetY()));
            int direction = entity.direction;
            if(direction == AnimationEntity.LEFT){
                entity.animationPlayer.doMoveLeftAnimation();
            }else if(direction == AnimationEntity.RIGHT){
                entity.animationPlayer.doMoveRightAnimation();
            }else if(direction == AnimationEntity.DOWN){
                entity.animationPlayer.doMoveDownAnimation();
            }else if(direction == AnimationEntity.UP){
                entity.animationPlayer.doMoveUpAnimation();
            }

            //entity.move(getMoveDirect());
            //}
        }else{
            if(!gotoNextPath()){
                entity.stop();
                return true;
            }
        }
        return false;
    }

    public float getTargetX(){
        if(target!=null){
            return target.getEntityX();
        }
        return currentPath.x;
    }

    public float getTargetY(){
        if(target!=null){
            return target.getEntityY();
        }
        return currentPath.y;
    }

    protected boolean isArrive(){
        AnimationEntity entity = (AnimationEntity) getTarget();
        float dist = Vector2.dst(getTargetX(),getTargetY(),entity.getEntityX(),entity.getEntityY());
        return dist < IS_ARRIVE_RADIO;
    }

    protected boolean gotoNextPath(){
        if(paths.isEmpty()) return false;
        if(currentPathIndex >= paths.size()-1) return false;

        currentPathIndex ++;
        currentPath =paths.get(currentPathIndex);
        return true;
    }

}
