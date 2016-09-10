package com.guxuede.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guxuede.game.physics.PhysicsManager;

/**
 * Created by guxuede on 2016/9/10 .
 */
public abstract class StageWorld {
    public static boolean isDebug = false;
    public abstract void act(float delta);
    public abstract PhysicsManager getPhysicsManager();
    public abstract Stage getStage();
    public abstract Camera getCamera();
    public abstract boolean isNotPause();
    public abstract boolean isVisible();
    public abstract void pause();
    public abstract void start();
    public abstract void hide();
    public abstract void show();
}
