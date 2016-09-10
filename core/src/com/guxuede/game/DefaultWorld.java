package com.guxuede.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guxuede.game.position.box2d.Box2dPositionWorld;
import com.guxuede.game.position.PositionWorld;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class DefaultWorld implements GameWorld{

    private PositionWorld positionWorld;
    private Stage stage;

    public DefaultWorld(Stage stage) {
        this.positionWorld = new Box2dPositionWorld();
        this.stage = stage;
    }

    @Override
    public PositionWorld getPositionWorld() {
        return positionWorld;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void act(float delta) {
        positionWorld.act(delta);
    }
}
