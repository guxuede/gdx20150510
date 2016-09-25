package com.test.l1;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.resource.ResourceManager;

/**
 * Created by guxuede on 2016/5/31 .
 */
public class TestGame  implements ApplicationListener {
    private Batch batch ;
    private Animation animation1;
    private Animation animation2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        animation1= ResourceManager.getAnimationHolder("special10").getStopDownAnimation();
        animation2= ResourceManager.getAnimationHolder("Undead").getStopDownAnimation();

    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        GdxSprite sprite1 = (GdxSprite) animation1.getKeyFrames()[0];
        GdxSprite sprite2 = (GdxSprite) animation2.getKeyFrames()[0];
        //sprite1.setOrigin(192, 192);
        //sprite1.setCenter(-192/2, 0);
        //sprite2.setCenter(-80 / 2, 0);
        sprite2.setRotation(90f);
        sprite2.setScale(0.1f,1);
        sprite1.setPosition(100+100, 200);
        sprite2.setPosition(100 + 100, 200);
        //sprite1.setCenter(-192/2, 0);
        //sprite2.setCenter(-80 / 2, 0);
        sprite1.draw(batch);
        sprite2.draw(batch);
        batch.end();
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
