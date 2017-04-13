package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.guxuede.game.actor.ability.skill.ScriptSkill;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2017/4/9 .
 */
public class SkillCoolingHolder extends Button {

    ShapeRenderer shapeRenderer;
    ScriptSkill scriptSkill;

    public SkillCoolingHolder(Skin skin,ScriptSkill scriptSkill) {
        super(skin);
        shapeRenderer = new ShapeRenderer();
        this.scriptSkill = scriptSkill;
    }

    public SkillCoolingHolder(ButtonStyle style,ScriptSkill scriptSkill) {
        super(style);
        shapeRenderer = new ShapeRenderer();
        this.scriptSkill = scriptSkill;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        if(!scriptSkill.isAvailable){
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 0.8f);
            localToStageCoordinates(TempObjects.temp0Vector2.set(getX(Align.center),getY(Align.center)));

            shapeRenderer.arc(TempObjects.temp0Vector2.x,TempObjects.temp0Vector2.y,100,0,degree,5);
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
        }
    }

    float degree;
    @Override
    public void act(float delta) {
        super.act(delta);
        if(!scriptSkill.isAvailable){
            degree = 360*(scriptSkill.getSkillCooldownCounter()/scriptSkill.getSkillCooldownTime());
            scriptSkill.setSkillCooldownCounter(scriptSkill.getSkillCooldownCounter()+delta);
            if(scriptSkill.getSkillCooldownCounter() >= scriptSkill.getSkillCooldownTime()){
                scriptSkill.isAvailable = true;
                degree = 0;
                scriptSkill.setSkillCooldownCounter(0);
            }
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
