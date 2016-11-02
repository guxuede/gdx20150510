package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by guxuede on 2016/10/25 .
 */
public class TiledActor extends LevelDrawActor {

    public Sprite sprite;

    @Override
    public void drawBody(Batch batch, float parentAlpha) {
        sprite.draw(batch,parentAlpha);
    }
}
