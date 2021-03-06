package com.guxuede.game.action.damage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * 让单位死亡
 */
public class ActorDeathAnimationAction extends RelativeTemporalAction  {

	@Override
	protected void updateRelative(float percentDelta) {
		
	}
	
	@Override
	protected void begin() {
		super.begin();
		AnimationEntity actor = ((AnimationEntity)target);
		actor.animationPlayer.doDeathAnimation();
        if(this.getDuration() == 0){
            Animation animation = actor.animationPlayer.currentAnimation;
            this.setDuration(animation==null?0:animation.getAnimationDuration());
        }
	}
	
	@Override
	protected void end() {
		((AnimationEntity)target).dispose();
	}

    @Override
    public void reset() {
        super.reset();
        setDuration(0);
    }
}
