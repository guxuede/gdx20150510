package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.resource.ActorAnimationPlayer;

public class AnimationProjection extends AnimationEntity {


    public AnimationProjection(ActorAnimationPlayer animationPlayer, World world, InputListener l) {
        super(animationPlayer, world, l);
        init();
    }

    public AnimationProjection(ActorAnimationPlayer animationPlayer, World world) {
        super(animationPlayer, world);
        init();
    }



    public void init(){
		this.scaleBy(1);
		speed = 20;
	}
	
	@Override
	public void createBody(World world) {
		super.createBody(world);
		this.body.setBullet(true);
	}
	
	@Override
	public void turnDirection(float degrees) {
		super.turnDirection(degrees);
		this.setRotation(degrees+90);
	}
}