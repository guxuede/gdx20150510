package com.guxuede.game.libgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/9/11 .
 */
public abstract class GdxGame extends Game {

    public abstract void transferActorToStage(AnimationEntity actor, String stageName, Vector2 pos);

    public void render () {
        if (screen != null){
            screen.render(Gdx.graphics.getDeltaTime());

        }
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
