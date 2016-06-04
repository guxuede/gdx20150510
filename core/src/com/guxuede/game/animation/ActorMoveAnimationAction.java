package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationEntity;

public class ActorMoveAnimationAction extends RelativeTemporalAction  {

	public Integer direction;
	
	@Override
	protected void updateRelative(float percentDelta) {
		
	}
	
	@Override
	protected void begin() {
		super.begin();
		AnimationEntity actor = ((AnimationEntity)target);
		final int direction = this.direction!=null?this.direction:actor.direction;
		if(direction==AnimationEntity.DOWN){
			actor.animationPlayer.doMoveDownAnimation();
        }else if(direction==AnimationEntity.UP){
        	actor.animationPlayer.doMoveUpAnimation();
        }else if(direction==AnimationEntity.LEFT){
        	actor.animationPlayer.doMoveLeftAnimation();
        }else if(direction==AnimationEntity.RIGHT){
        	actor.animationPlayer.doMoveRightAnimation();
        }else{
        	actor.animationPlayer.doIdelAnimation(actor.direction);
        }
	}
	
	@Override
	protected void end() {
		AnimationEntity actor = ((AnimationEntity)target);
		actor.animationPlayer.doIdelAnimation(actor.direction);
	}


}
