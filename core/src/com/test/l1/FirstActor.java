package com.test.l1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FirstActor extends Actor{
	Texture but;
	public FirstActor() {
		but=new Texture(Gdx.files.internal("imgbut.PNG"));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(but, getX(), getY());
	}
}
