package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.animation.ActionsFactory;
import com.guxuede.game.effects.AnimationEffect;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.ActorAnimationPlayer;

import java.util.HashMap;
import java.util.Map;

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
		speed = 200;
	}
	
	@Override
	public void createBody(World world) {
		super.createBody(world);
		this.body.setBullet(true);
	}
	
	@Override
	public void turnDirection(float degrees) {
		super.turnDirection(degrees);
		//this.setRotation(degrees+90);
	}

    Map<AnimationEntity,Float> hitMaps = new HashMap<AnimationEntity, Float>();
    /**
     * 一秒一次hit
     * @param entity
     * @return
     */
    public boolean isHit(AnimationEntity entity){
        if(entity==null)return true;

        if(!hitMaps.containsKey(entity)){
            hitMaps.put(entity,stateTime);
            return true;
        }else{
            float du = hitMaps.get(entity);
            if(stateTime - du > 1){
                System.out.println("aaa"+stateTime);
                hitMaps.put(entity,stateTime);
                return true;
            }else {
                return false;
            }
        }
    }

    public void hit(AnimationEntity entity,Vector2 vector2){
        //if(isHit(entity)){
            doShowDamageEffect(vector2, entity);
            ResourceManager.sound.play();
        //}
    }


    public void doShowDamageEffect(Vector2 vector2, Actor actor) {
        BarrageTip tip = new BarrageTip("-1", vector2.x, vector2.y);
        tip.setZIndex(Integer.MAX_VALUE);
        getStage().addActor(tip);

        if (actor != null) {
            actor.addAction(
                    ActionsFactory.sequence(
                            ActionsFactory.color(Color.RED, 0.1f),
                            ActionsFactory.color(Color.PINK, 0.1f),
                            ActionsFactory.color(new Color(1, 1, 1, 1), 0.1f)
                    )
            );
            AnimationEffect effect = new AnimationEffect();
            effect.setEffectAnimation(ResourceManager.getAnimationHolder("special10").getStopDownAnimation());
            actor.addAction(effect);
        }
    }
}