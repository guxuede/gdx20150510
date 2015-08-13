package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.tools.ActionsUtils;

public class AnimationActorProjection extends AnimationActor {

	
	public AnimationActorProjection(String name, World world, InputListener l) {
		super(name, world, l);
		init();
	}
	
	public AnimationActorProjection(TextureAtlas atlas, String name,World world, InputListener l) {
		super(atlas, name, world, l);
		init();
	}
	public AnimationActorProjection(TextureAtlas atlas, String name, World world) {
		super(atlas, name, world);
		init();
	}
	public AnimationActorProjection(TextureRegion ar, World world) {
		super(ar, world);
		init();
	}
	
	public void init(){
		this.scaleBy(1);
		speed = 20;
		this.body.setBullet(true);
	}
	
	@Override
	public void turnDirection(float degrees) {
		super.turnDirection(degrees);
		this.setRotation(degrees+90);
	}
}