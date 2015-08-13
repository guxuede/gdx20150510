package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationActor;

public class ActorDeathAnimationAction extends RelativeTemporalAction  {

	public Integer direction;
	
	@Override
	protected void updateRelative(float percentDelta) {
		
	}
	
	@Override
	protected void begin() {
		super.begin();
		AnimationActor actor = ((AnimationActor)target);
		final int direction = this.direction!=null?this.direction:actor.direction;
		actor.animationGen.doDeathAnimation();
	}
	
	@Override
	protected void end() {
		((AnimationActor)target).dispose();
	}


}
