package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.StageWorld;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.ActorAnimationPlayer;

public class AnimationProjection extends AnimationEntity {

    protected boolean trunDirectionWhenMove = true;


    public AnimationProjection(ActorAnimationPlayer animationPlayer, StageWorld world, InputListener l) {
        super(animationPlayer, world, l);
        init();
    }

    public AnimationProjection(ActorAnimationPlayer animationPlayer, StageWorld world) {
        super(animationPlayer, world);
        init();
    }



    public void init(){
		this.scaleBy(1);
		speed = 50000000;
        setZIndex(2);
        this.isSensor = true;
	}

	@Override
	public void turnDirection(float degrees) {
		super.turnDirection(degrees);
        if(trunDirectionWhenMove){
            this.setRotation(degrees+90);
        }
	}

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    /**
     * 撞击
     * @param entity 撞击对象
     * @param position 撞击位置
     */
    public void hit(AnimationEntity entity,Vector2 position){
        doShowDamageEffect(entity,position);
    }


    public void doShowDamageEffect(AnimationEntity entity,Vector2 position) {
        BarrageTip tip = new BarrageTip("-1", position.x, position.y);
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
                AnimationEffect effect = new AnimationEffect();
                effect.setEffectAnimation(ResourceManager.getAnimationHolder("attack1").getStopDownAnimation());
                entity.addAction(effect);
            }
        }
    }
}