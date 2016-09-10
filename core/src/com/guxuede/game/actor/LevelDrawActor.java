package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guxuede.game.libgdx.GdxEffect;

/**
 * Created by guxuede on 2016/5/31 .
 */
public class LevelDrawActor extends Actor {

    public static final int DRAW_LEVEL_FOOT = 0;
    public static final int DRAW_LEVEL_BODY = 1;
    public static final int DRAW_LEVEL_HEAD = 2;
    public int drawLevel;
    public float stateTime;

    public float getCenterX() {
        return super.getX(Align.center);
    }

    public float getCenterY() {
        return super.getY(Align.center);
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
        if(isInScreen()){
            if(drawLevel == 0){
                drawFoot(batch,parentAlpha);
                drawFootEffect(batch,parentAlpha);
            }else if(drawLevel == 1){
                drawBody(batch, parentAlpha);
                drawBodyEffect(batch, parentAlpha);
            }else{
                drawHead(batch,parentAlpha);
                drawHeadEffect(batch,parentAlpha);
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
    protected void drawFootEffect(Batch batch, float parentAlpha){
        Array<Action> actions = this.getActions();
        if (actions.size > 0) {
            for (int i = 0; i < actions.size; i++) {
                Action action = actions.get(i);
                if(action instanceof GdxEffect){
                    ((GdxEffect) action).drawFootEffect(batch, parentAlpha);
                }
            }
        }
    }
    protected void drawBodyEffect(Batch batch, float parentAlpha){
        Array<Action> actions = this.getActions();
        if (actions.size > 0) {
            for (int i = 0; i < actions.size; i++) {
                Action action = actions.get(i);
                if(action instanceof GdxEffect){
                    ((GdxEffect) action).drawBodyEffect(batch, parentAlpha);
                }
            }
        }
    }
    protected void drawHeadEffect(Batch batch, float parentAlpha){
        Array<Action> actions = this.getActions();
        if (actions.size > 0) {
            for (int i = 0; i < actions.size; i++) {
                Action action = actions.get(i);
                if(action instanceof GdxEffect){
                    ((GdxEffect) action).drawHeadEffect(batch,parentAlpha);
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


}
