package com.guxuede.game.animation;

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
			int direction = actor.direction ;
			if(direction==ActorMoveDirectiveAction.DOWN){
				actor.body.setLinearVelocity(0, -actor.speed);
	        }else if(direction==ActorMoveDirectiveAction.UP){
	        	actor.body.setLinearVelocity(0, actor.speed);
	        }else if(direction==ActorMoveDirectiveAction.LEFT){
	        	actor.body.setLinearVelocity(-actor.speed, 0);
	        }else if(direction==ActorMoveDirectiveAction.RIGHT){
	        	actor.body.setLinearVelocity(actor.speed, 0);
	        }
		}
	}

}
