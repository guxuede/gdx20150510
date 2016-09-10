package com.guxuede.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guxuede.game.position.PositionWorld;

/**
 * Created by guxuede on 2016/9/10 .
 */
public interface GameWorld {

    public PositionWorld getPositionWorld();
    public Stage getStage();

    public void act(float delta);
}
