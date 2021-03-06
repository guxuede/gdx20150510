package com.guxuede.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.IntArray;
import com.guxuede.game.action.move.ActorMoveToPathAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.tools.TempObjects;

import static com.guxuede.game.StageWorld.MAP_CELL_H;
import static com.guxuede.game.StageWorld.MAP_CELL_W;

/**
 * Created by guxuede on 2016/10/6 .
 */
public class MouseManager extends InputListener {

    public static final int MOUSE_STTUS_NORMAL=0,MOUSE_STTUS_AREA_Indicator=1,MOUSE_STTUS_TARGET_Indicator=2;
    public int status = MOUSE_STTUS_NORMAL;

    public StageWorld stageWorld;
    public Sprite mouseSprite;
    public float r;
    public MouseIndicatorListener listener;
    ShapeRenderer shapes;

    public MouseManager(){
        shapes = new ShapeRenderer();
        shapes.setColor(Color.GREEN);
        cancelIfNeed();
    }

    public void setDefaultCursor(){
        Gdx.graphics.setCursor(ResourceManager.customCursor);
        mouseSprite = null;
    }

    public void setAreaCursor(){
        setDefaultCursor();
        mouseSprite = ResourceManager.mouseAreaIndicator;
    }

    public void setTargetCursor(){
        Gdx.graphics.setCursor(ResourceManager.customCursor);
        mouseSprite = ResourceManager.mouseTargetIndicator;
    }


    public void enterToAreaChoiceStatus(float r,MouseIndicatorListener listener){
        cancelIfNeed();
        this.listener = listener;
        this.r = r;
        status = MOUSE_STTUS_AREA_Indicator;
        setAreaCursor();
    }

    public void enterToTargetChoiceStatus(MouseIndicatorListener listener){
        cancelIfNeed();
        this.listener = listener;
        this.r = 0;
        status = MOUSE_STTUS_TARGET_Indicator;
        setTargetCursor();
    }


    public void cancelIfNeed(){
        cancel();
        if(listener !=null){
            listener.onCancel();
            listener = null;
        }
    }

    public void cancel(){
        this.r = 0;
        status = MOUSE_STTUS_NORMAL;
        setDefaultCursor();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(status!= MOUSE_STTUS_NORMAL){
            if(button == 1){
                cancelIfNeed();
                return true;
            }else if (button == 0){
                AnimationEntity animationTarget = event.getTarget() instanceof AnimationEntity? (AnimationEntity) event.getTarget() :null;
                TempObjects.temp0Vector2.set(x,y);
                if(status == MOUSE_STTUS_AREA_Indicator){
                    if(listener !=null && listener.onHoner(animationTarget,TempObjects.temp0Vector2,r)){
                        listener.onActive(animationTarget, TempObjects.temp0Vector2 ,r);
                        cancel();
                        return true;
                    }
                }else if(status == MOUSE_STTUS_TARGET_Indicator){
                    if(listener !=null && listener.onHoner(animationTarget,null,r)){
                        listener.onActive(animationTarget,TempObjects.temp0Vector2,r);
                        cancel();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if(status!= MOUSE_STTUS_NORMAL && Input.Keys.ESCAPE == keycode){
            cancelIfNeed();
            return true;
        }
        return false;
    }

    public void render(Batch batch,float delta){
        Vector2 pos = stageWorld.getStage().screenToStageCoordinates(TempObjects.temp1Vector2.set(Gdx.input.getX(),Gdx.input.getY()));
        if(mouseSprite !=null){
            batch.begin();
            mouseSprite.setPosition(pos.x,pos.y);
            mouseSprite.rotate(1f);
            mouseSprite.draw(batch);
            batch.end();
        }

        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.rect((int)(pos.x/MAP_CELL_W) * MAP_CELL_W, (int)(pos.y/MAP_CELL_H) * MAP_CELL_H, MAP_CELL_W, MAP_CELL_H);
        shapes.end();
//        if(ActorMoveToPathAction.path!=null){
//            IntArray path = ActorMoveToPathAction.path;
//            for (int i = 0, n = path.size; i < n; i += 2) {
//                int x = path.get(i);
//                int y = path.get(i + 1);
//                shapes.rect(x, y, MAP_CELL_W, MAP_CELL_H);
//            }
//        }
    }

    public static class MouseIndicatorListener {
        public boolean onHoner(AnimationEntity animationEntity, Vector2 center , float r){
            return false;
        }
        public void onActive(AnimationEntity animationEntity, Vector2 center , float r){

        }
        public void onCancel(){

        }
    }
}
