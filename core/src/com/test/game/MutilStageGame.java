package com.test.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.InputProcessorLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxuede on 2016/9/11 .
 */
public class MutilStageGame implements ApplicationListener {

    public static Array<TitleMapStage> mapStages;
    public static TitleMapStage currentStage;
    public static InputMultiplexer inputMultiplexer;
    @Override
    public void create() {
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer); //InputMultiplexer
        mapStages = new Array<TitleMapStage>();
        mapStages.add(new TitleMapStage("desert1.tmx"));
        mapStages.add(new TitleMapStage("desert.tmx"));
        activeStage(0);
    }

    public static Stage activeStage(int i){
        for(TitleMapStage stage : mapStages){
            stage.world.hide();
        }
        currentStage = mapStages.get(i);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(currentStage);
        currentStage.world.show();
        return currentStage;
    }

    public static void actorToStage(AnimationEntity actor, String stageName, Vector2 pos){
        TitleMapStage oldTitleMapStage = (TitleMapStage) actor.getStage();
        if(oldTitleMapStage!=null){
            oldTitleMapStage.detachActor(actor);

        }
        int stageIndex = -1;
        TitleMapStage newTitleMapStage = null;
        for (int i = 0; i < mapStages.size; i++) {
            if(mapStages.get(i).stageName.equals(stageName)){
                stageIndex = i;
                newTitleMapStage = mapStages.get(i);
                break;
            }
        }
        newTitleMapStage.attachActor(actor);
        actor.setEntityPosition(pos.x,pos.y);
        if(actor.equals(oldTitleMapStage.viewActor)){
            oldTitleMapStage.viewActor = null;
            newTitleMapStage.viewActor = actor;
            activeStage(stageIndex);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    float CurFPS;
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currentStage.act();
        currentStage.draw();

        if(this.CurFPS != Gdx.graphics.getFramesPerSecond()) {
            Gdx.graphics.setTitle("FPS:"+String.valueOf(Gdx.graphics.getFramesPerSecond()));
            this.CurFPS = Gdx.graphics.getFramesPerSecond();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F10)){
            activeStage(0);
        }else         if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            activeStage(1);
        }
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
