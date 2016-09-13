package com.guxuede.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guxuede.game.libgdx.GdxGame;
import com.guxuede.game.physics.box2d.Box2DPhysicsManager;
import com.guxuede.game.physics.PhysicsManager;
import com.guxuede.game.sound.SoundManager;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class DefaultWorld extends StageWorld {

    private boolean isNotPause = true;
    private boolean isVisible = true;

    private GdxGame gdxGame;
    private PhysicsManager physicsManager;
    private Stage stage;
    private Camera camera;
    private SoundManager soundManager;

    public DefaultWorld(Stage stage,GdxGame gdxGame) {
        this.gdxGame = gdxGame;
        this.physicsManager = new Box2DPhysicsManager(this);
        this.soundManager =new SoundManager();
        this.stage = stage;
        this.camera = stage.getCamera();
    }

    public PhysicsManager getPhysicsManager() {
        return physicsManager;
    }

    @Override
    public SoundManager getSoundManager() {
        return soundManager;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public GdxGame getGame() {
        return gdxGame;
    }

    @Override
    public boolean isNotPause() {
        return isNotPause;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void pause() {
        this.isNotPause = false;
        soundManager.pause();
    }

    @Override
    public void start() {
        this.isNotPause = true;
        soundManager.resume();
    }

    @Override
    public void hide() {
        this.isVisible = false;
        soundManager.pause();
    }

    @Override
    public void show() {
        this.isVisible = true;
        soundManager.resume();
    }

    @Override
    public void act(float delta) {
        physicsManager.act(delta);
    }
}
