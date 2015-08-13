package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationActor;

public class ActorMoveAnimationAction extends RelativeTemporalAction  {

	public Integer direction;
	
	@Override
	protected void updateRelative(float percentDelta) {
		
	}
	
	@Override
	protected void begin() {
		super.begin();
		AnimationActor actor = ((AnimationActor)target);
		final int direction = this.direction!=null?this.direction:actor.direction;
		if(direction==AnimationActor.DOWN){
			actor.animationGen.doMoveDownAnimation();
        }else if(direction==AnimationActor.UP){
        	actor.animationGen.doMoveUpAnimation();
        }else if(direction==AnimationActor.LEFT){
        	actor.animationGen.doMoveLeftAnimation();
        }else if(direction==AnimationActor.RIGHT){
        	actor.animationGen.doMoveRightAnimation();
        }else{
        	actor.animationGen.doIdelAnimation();
        }
	}
	
	@Override
	protected void end() {
		AnimationActor actor = ((AnimationActor)target);
		actor.animationGen.doIdelAnimation();
	}


}
