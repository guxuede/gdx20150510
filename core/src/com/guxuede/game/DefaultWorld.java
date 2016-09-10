package com.guxuede.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guxuede.game.physics.box2d.Box2DPhysicsManager;
import com.guxuede.game.physics.PhysicsManager;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class DefaultWorld extends StageWorld {

    private boolean isNotPause = true;
    private boolean isVisible = true;

    private PhysicsManager physicsManager;
    private Stage stage;
    private Camera camera;

    public DefaultWorld(Stage stage) {
        this.physicsManager = new Box2DPhysicsManager(this);
        this.stage = stage;
        this.camera = stage.getCamera();
    }

    public PhysicsManager getPhysicsManager() {
        return physicsManager;
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
    }

    @Override
    public void start() {
        this.isNotPause = true;
    }

    @Override
    public void hide() {
        this.isVisible = false;
    }

    @Override
    public void show() {
        this.isVisible = true;
    }

    @Override
    public void act(float delta) {
        physicsManager.act(delta);
    }
}
