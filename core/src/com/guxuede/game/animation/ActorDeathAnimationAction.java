package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationEntity;

public class ActorDeathAnimationAction extends RelativeTemporalAction  {

	public Integer direction;
	
	@Override
	protected void updateRelative(float percentDelta) {
		
	}
	
	@Override
	protected void begin() {
		super.begin();
		AnimationEntity actor = ((AnimationEntity)target);
		final int direction = this.direction!=null?this.direction:actor.direction;
		actor.animationPlayer.doDeathAnimation();
	}
	
	@Override
	protected void end() {
		((AnimationEntity)target).dispose();
	}


}
