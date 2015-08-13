package com.guxuede.game.actor;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ParticelActor extends Actor{

	ParticleEffect effect;
	
	public ParticelActor() {
		setBounds(0, 0, 100, 100);
		effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
        //effect.setPosition(getX(), getY());
        effect.start();
       effect.setDuration(100000);
        ParticleEmitter p=effect.findEmitter("Untitled");
        p.getWind().setHigh(100);
        this.addAction(Actions.moveBy(100, 100,1));
	}
	
	@Override
	protected void positionChanged() {
		effect.setPosition(getX(), getY());
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		//effect.setPosition(getX(), getY());
		effect.draw(batch, Gdx.app.getGraphics().getDeltaTime());
	}
}
