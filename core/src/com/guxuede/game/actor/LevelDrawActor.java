package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guxuede.game.StageWorld;
import com.guxuede.game.action.GdxParallelAction;
import com.guxuede.game.libgdx.GdxEffect;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/5/31 .
 */
public abstract class LevelDrawActor extends Actor {

    public static final int DRAW_LEVEL_FOOT = 0;
    public static final int DRAW_LEVEL_BODY = 1;
    public static final int DRAW_LEVEL_HEAD = 2;
    public int drawZIndex = 0;
    public int drawLevel;
    public float stateTime;
    public boolean isInScreen = true;//是否在视野中

    public float getCenterX() {
        return super.getX(Align.center);
    }

    public float getCenterY() {
        return super.getY(Align.center);
    }

    /**
     * 返回角色中心点
     * @return
     */
    public Vector2 getCenterPosition(){
        return TempObjects.temp3Vector2.set(getX(Align.center),getY(Align.center));
    }

    /**
     *
     * @return
     */
    public Vector2 getTopPosition(){
        return TempObjects.temp3Vector2.set(getX(Align.top),getY(Align.top));
    }

    /**
     * 返回角色脚部点
     * @return
     */
    public Vector2 getBottomPosition(){
        return TempObjects.temp3Vector2.set(getX(Align.bottom),getY(Align.center)-getHeight()/2);
    }

    /**
     * 判断角色是否在屏幕中可见
     * @return
     */
    public boolean isInScreen(){
        Viewport viewport = getStage().getViewport();
        Vector3 center = viewport.getCamera().position;
        float screenW = Math.max(viewport.getScreenWidth(),viewport.getScreenHeight());
        float dis = Vector2.dst(center.x,center.y, getCenterX(), getCenterY());
        return dis < screenW/2;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        isInScreen = isInScreen();
        if(isInScreen && isVisible()){
            if(drawLevel == 0){
                drawFootEffect(getActions(),batch,parentAlpha);
                drawFoot(batch,parentAlpha);
            }else if(drawLevel == 1){
                drawBody(batch, parentAlpha);
                drawBodyEffect(getActions(),batch, parentAlpha);
            }else{
                drawHead(batch,parentAlpha);
                drawHeadEffect(getActions(),batch,parentAlpha);
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    /**
     * 画角色本身
     * @param batch
     * @param parentAlpha
     */
    public void drawFoot(Batch batch, float parentAlpha){

    }

    public void drawBody(Batch batch, float parentAlpha){

    }

    public void drawHead(Batch batch, float parentAlpha){

    }

    /**
     * 画角色特效
     * @param batch
     * @param parentAlpha
     */
    protected void drawFootEffect(Array<Action> actions,Batch batch, float parentAlpha){
        if (actions.size > 0) {
            for (int i = 0; i < actions.size; i++) {
                Action action = actions.get(i);
                if(action instanceof GdxEffect){
                    ((GdxEffect) action).drawFootEffect(batch, parentAlpha);
                }else if(action instanceof GdxParallelAction){
                        drawFootEffect(((GdxParallelAction) action).getCurrentAction(),batch,parentAlpha);
                }
            }
        }
    }
    protected void drawBodyEffect(Array<Action> actions,Batch batch, float parentAlpha){
        if (actions.size > 0) {
            for (int i = 0; i < actions.size; i++) {
                Action action = actions.get(i);
                if(action instanceof GdxEffect){
                    ((GdxEffect) action).drawBodyEffect(batch, parentAlpha);
                }else if(action instanceof GdxParallelAction){
                    drawBodyEffect(((GdxParallelAction) action).getCurrentAction(),batch,parentAlpha);
                }
            }
        }
    }
    protected void drawHeadEffect(Array<Action> actions,Batch batch, float parentAlpha){
        if (actions.size > 0) {
            for (int i = 0; i < actions.size; i++) {
                Action action = actions.get(i);
                if(action instanceof GdxEffect){
                    ((GdxEffect) action).drawHeadEffect(batch,parentAlpha);
                }else if(action instanceof GdxParallelAction){
                    drawHeadEffect(((GdxParallelAction) action).getCurrentAction(),batch,parentAlpha);
                }
            }
        }
    }



    //覆盖下面两个方法，确保TemporalAction类型的action能被及时掉用end方法
    @Override
    public void clearActions() {
        for (int i = getActions().size - 1; i >= 0; i--){
            Action action = getActions().get(i);
            if (action instanceof TemporalAction){
                TemporalAction ta = (TemporalAction) action;
                ta.finish();
                ta.act(Float.MAX_VALUE);
            }
            action.setActor(null);
        }

        getActions().clear();
    }

    @Override
    public void removeAction(Action action) {
        if (getActions().removeValue(action, true)) {
            if (action instanceof TemporalAction){
                TemporalAction ta = (TemporalAction) action;
                ta.finish();
                ta.act(Float.MAX_VALUE);
            }
            action.setActor(null);
        }
    }

    public void setDrawZIndex(int drawZIndex) {
        this.drawZIndex = drawZIndex;
    }
}
