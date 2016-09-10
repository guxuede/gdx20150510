package com.guxuede.game.action;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationEntity;

public class ActorDeathAnimationAction extends RelativeTemporalAction  {

	@Override
	protected void updateRelative(float percentDelta) {
		
	}
	
	@Override
	protected void begin() {
		super.begin();
		AnimationEntity actor = ((AnimationEntity)target);
		actor.animationPlayer.doDeathAnimation();
        this.setDuration(actor.animationPlayer.currentAnimation.getAnimationDuration());
	}
	
	@Override
	protected void end() {
		((AnimationEntity)target).dispose();
	}


}
