package com.guxuede.game.animation;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationEntity;

public class ActorAlwayMoveAction extends RelativeTemporalAction{

	public ActorAlwayMoveAction() {
		this.setDuration(Float.MAX_VALUE);
	}
	
	
	@Override
	protected void updateRelative(float percentDelta) {
		AnimationEntity actor = ((AnimationEntity)target);
        if(actor.isMoving){
            actor.setLinearVelocity(getActorLinearVelocity());
		}
	}

    Vector2 tmpVector2 = new Vector2();
    Vector2 getActorLinearVelocity(){
        AnimationEntity actor = ((AnimationEntity)target);
        float speed = actor.speed;
        float degrees = actor.degrees;
        tmpVector2.set(MathUtils.cosDeg(degrees) * speed, MathUtils.sinDeg(degrees) * speed);
        return tmpVector2;
    }
}
