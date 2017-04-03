package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.StageWorld;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.resource.ActorAnimationPlayer;

public class AnimationProjection extends AnimationEntity {

    protected boolean trunDirectionWhenMove = true;

    public AnimationProjection(ActorAnimationPlayer animationPlayer, StageWorld world) {
        super(animationPlayer, world);
        init();
    }



    public void init(){
		//this.scaleBy(1);
		speed = 50000000;
        setDrawZIndex(2);
        this.isSensor = true;
	}

	@Override
	public AnimationEntity turnDirection(float degrees) {
		super.turnDirection(degrees);
        if(trunDirectionWhenMove){
            this.setRotation(degrees+90);
        }
        return this;
	}

    /**
     * 撞击
     * @param entity 撞击对象
     * @param position 撞击位置
     */
    public void hit(AnimationEntity entity,Vector2 position){
        if(collisionSize != 0){
            doShowDamageEffect(entity,position);
        }
    }


    public void doShowDamageEffect(AnimationEntity entity,Vector2 position) {
        int hitP = MathUtils.random(1,10);
        BarrageTip tip = new BarrageTip("-"+hitP, position.x, position.y);
        tip.setZIndex(Integer.MAX_VALUE);
        getStage().addActor(tip);

        if (entity != null) {
            if(entity.equals(this)){
                entity.dead();
            }else{
                entity.addAction(
                        ActionsFactory.sequence(
                                ActionsFactory.color(Color.RED, 0.1f),
                                ActionsFactory.color(Color.PINK, 0.1f),
                                ActionsFactory.color(new Color(1, 1, 1, 1), 0.1f)
                        )
                );
                entity.currentHitPoint = entity.currentHitPoint - hitP;
                AnimationEffect effect = new AnimationEffect();
                effect.setEffectAnimation(ResourceManager.getAnimationHolder("Blow2").getStopDownAnimation());
                entity.addAction(effect);
            }
        }
    }
}