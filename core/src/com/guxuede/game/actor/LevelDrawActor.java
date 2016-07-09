package com.guxuede.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.libgdx.GdxEffect;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.libgdx.ResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guxuede on 2016/5/31 .
 */
public class LevelDrawActor extends Actor {

    public static final int DRAW_LEVEL_FOOT = 0;
    public static final int DRAW_LEVEL_BODY = 1;
    public static final int DRAW_LEVEL_HEAD = 2;
    public int drawLevel;
    public float stateTime;

    public float getEntityX() {
        return super.getX(Align.center);
    }

    public float getEntityY() {
        return super.getY(Align.center);
    }

    public void setEntityPosition(float x, float y) {
        this.setPosition(x, y, Align.center);
    }

    /**
     * 判断角色是否在屏幕中可见
     * @return
     */
    public boolean isInScreen(){
        Vector3 center = getStage().getViewport().getCamera().position;
        float screenW = getStage().getViewport().getScreenWidth()/2;
        float screenH = getStage().getViewport().getScreenHeight()/2;
        if(getEntityX() > center.x-screenW && getEntityX() < center.x+screenW-100 &&
                getEntityY() > center.y-screenH && getEntityY() < center.y+screenH-100 ){
            return true;
        }
        return false;
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

}
