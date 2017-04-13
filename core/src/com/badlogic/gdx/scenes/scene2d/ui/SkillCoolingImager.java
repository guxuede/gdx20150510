package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.guxuede.game.actor.ability.skill.ScriptSkill;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2017/4/9 .
 */
public class SkillCoolingImager extends Button {

    ShapeRenderer shapeRenderer;

    public SkillCoolingImager(Skin skin) {
        super(skin);
        shapeRenderer = new ShapeRenderer();
    }

    public SkillCoolingImager(ButtonStyle style) {
        super(style);
        shapeRenderer = new ShapeRenderer();
    }

    float a2=360;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 0.8f);
        localToStageCoordinates(TempObjects.temp0Vector2.set(getX(Align.center),getY(Align.center)));
        shapeRenderer.arc(TempObjects.temp0Vector2.x,TempObjects.temp0Vector2.y,100,0,a2--,5);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        if(a2<0){
            a2 = 360;
        }
    }

    @Override
    public float getPrefHeight() {
        return getHeight();
    }

    @Override
    public float getPrefWidth() {
        return getWidth();
    }
}
