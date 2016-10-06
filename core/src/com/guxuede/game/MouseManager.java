package com.guxuede.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/10/6 .
 */
public class MouseManager extends InputListener {

    public static final int MOUSE_STTUS_NORMAL=0,MOUSE_STTUS_AREA_Indicator=1,MOUSE_STTUS_TARGET_Indicator=2;
    public int status = MOUSE_STTUS_NORMAL;

    public StageWorld stageWorld;
    public Batch batch;
    public Sprite textureRegion;
    public float r;
    public MouseIndicatorLinsner linsner;

    public MouseManager(){
        batch = new SpriteBatch();
        enterToAreaChoiceStatus(100f,null);
        //enterToTargetChoiceStatus(null);
    }

    public void setDefaultCursor(){
        Gdx.graphics.setCursor(ResourceManager.customCursor);
        textureRegion = null;
    }

    public void setAreaCursor(){
        setDefaultCursor();
        textureRegion = ResourceManager.mouseAreaIndicator;
    }

    public void setTargetCursor(){
        Gdx.graphics.setCursor(ResourceManager.customCursor);
        textureRegion = ResourceManager.mouseTargetIndicator;
    }


    public void enterToAreaChoiceStatus(float r,MouseIndicatorLinsner linsner){
        cancelIfNeed();
        this.linsner = linsner;
        this.r = r;
        status = MOUSE_STTUS_AREA_Indicator;
        setAreaCursor();
    }

    public void enterToTargetChoiceStatus(MouseIndicatorLinsner linsner){
        cancelIfNeed();
        this.linsner = linsner;
        this.r = 0;
        status = MOUSE_STTUS_TARGET_Indicator;
        setTargetCursor();
    }


    public void cancelIfNeed(){
        cancel();
        if(linsner!=null){
            linsner.onCancel();
            linsner = null;
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
                if(status == MOUSE_STTUS_AREA_Indicator){
                    if(linsner !=null && linsner.onHoner(null,TempObjects.temp0Vector2.set(x,y),r)){
                        linsner.onActive(null, TempObjects.temp0Vector2.set(x,y),r);
                        cancel();
                        return true;
                    }
                }else if(status == MOUSE_STTUS_TARGET_Indicator){
                    if(linsner!=null && event.getTarget()!=null && event.getTarget() instanceof AnimationEntity && linsner.onHoner((AnimationEntity)event.getTarget(),null,r)){
                        linsner.onActive((AnimationEntity)event.getTarget(),null,r);
                        cancel();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void render(){
        if(textureRegion!=null){
            batch.begin();
            stageWorld.getStage().screenToStageCoordinates(TempObjects.temp1Vector2.set(Gdx.input.getX(),Gdx.input.getY()));
            batch.draw(textureRegion,TempObjects.temp1Vector2.x-textureRegion.getOriginX(),TempObjects.temp1Vector2.y-textureRegion.getOriginY());
            batch.end();
        }
    }

    public static class MouseIndicatorLinsner{
        public boolean onHoner(AnimationEntity animationEntity, Vector2 center , float r){
            return false;
        }
        public void onActive(AnimationEntity animationEntity, Vector2 center , float r){

        }
        public void onCancel(){

        }
    }
}
