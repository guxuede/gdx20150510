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

    private List<Vector2> paths = Arrays.asList(new Vector2(400,400),new Vector2(100,200));

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


    @Override
    public boolean act(float delta) {
        AnimationEntity entity = (AnimationEntity) getTarget();
        if(!isArrive()){
            //if(!entity.isMoving){
            entity.isMoving = true;
            entity.turnDirection(MathUtils.getAngle(entity.getEntityX(),entity.getEntityY(),currentPath.x,currentPath.y));
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

    protected boolean isArrive(){
        AnimationEntity entity = (AnimationEntity) getTarget();
        float dist = currentPath.dst(entity.getEntityX(),entity.getEntityY());
        //System.out.println(entity.getEntityX()+","+entity.getEntityY()+",,,,"+dist);
        return dist < 10;
    }

    protected boolean gotoNextPath(){
        if(paths.isEmpty()) return false;
        if(currentPathIndex >= paths.size()-1) return false;

        currentPathIndex ++;
        currentPath =paths.get(currentPathIndex);
        return true;
    }

    protected int getMoveDirect(){
        AnimationEntity entity = (AnimationEntity) getTarget();

        float dx = currentPath.x - entity.getEntityX();
        float dy = currentPath.y - entity.getEntityY();
        if(Math.abs(dx) > Math.abs(dy)){
            if(dx > 0){
                return AnimationEntity.RIGHT;
            }else{
                return AnimationEntity.LEFT;
            }
        }else{
            if(dy > 0){
                return AnimationEntity.UP;
            }else{
                return AnimationEntity.DOWN;
            }
        }

    }
}
