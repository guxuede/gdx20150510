package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.StageWorld;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxGame;
import com.guxuede.game.resource.ResourceManager;

/**
 * Created by guxuede on 2016/9/11 .
 */
public class MutilStageGame extends GdxGame {

    public Array<TitleMapStage> mapStages;
    public InputMultiplexer inputMultiplexer;

    @Override
    public void create() {
        Gdx.graphics.setCursor(ResourceManager.customCursor);
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer); //InputMultiplexer
        mapStages = new Array<TitleMapStage>();
        mapStages.add(new TitleMapStage("desert1.tmx",this));
        mapStages.add(new TitleMapStage("desert.tmx",this));
        setScreen(mapStages.get(0));
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor((Stage)screen);
    }

    @Override
    public void transferActorToStage(AnimationEntity actor, String stageName, Vector2 pos) {
        TitleMapStage oldTitleMapStage = (TitleMapStage) actor.getStage();
        if(oldTitleMapStage!=null){
            oldTitleMapStage.detachActor(actor);
        }
        TitleMapStage newTitleMapStage = null;
        for (int i = 0; i < mapStages.size; i++) {
            if(mapStages.get(i).stageName.equals(stageName)){
                newTitleMapStage = mapStages.get(i);
                break;
            }
        }
        newTitleMapStage.attachActor(actor);
        actor.setEntityPosition(pos.x,pos.y);
        if(actor.equals(oldTitleMapStage.viewActor)){
            oldTitleMapStage.viewActor = null;
            newTitleMapStage.viewActor = actor;
            setScreen(newTitleMapStage);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }

    private float CurFPS;
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        //just for debug below
        if(this.CurFPS != Gdx.graphics.getFramesPerSecond()) {
            Gdx.graphics.setTitle("FPS:"+String.valueOf(Gdx.graphics.getFramesPerSecond()));
            this.CurFPS = Gdx.graphics.getFramesPerSecond();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F10)){
            setScreen(mapStages.get(0));
        }else         if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            setScreen(mapStages.get(1));
        }
    }

}
