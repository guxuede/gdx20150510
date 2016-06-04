
package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;


public class ActorJumpAction extends TemporalAction {
	private float startX, startY;
	private float endX,endY;

	private static final float duration = 5f;

	protected void begin () {
		AnimationEntity actor = ((AnimationEntity)target);
		startX = actor.body.getPosition().x;
		startY = actor.body.getPosition().y;
		this.setDuration(duration);
		actor.addAction(ActionsFactory.sequence(ActionsFactory.scaleBy(0.2f, 0.2f, duration / 2), ActionsFactory.scaleBy(-0.2f, -0.2f, duration / 2)));
		
		double d = MathUtils.getAngle(startX,startY,endX,endY);
		
		actor.turnDirection((float) d);
	}

	protected void update (float percent) {
		AnimationEntity actor = ((AnimationEntity)target);
		actor.body.setTransform(startX + (endX - startX) * percent,startY + (endY - startY) * percent, 99);
		final int direction = actor.direction;
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
		super.end();
		AnimationEntity actor = ((AnimationEntity)target);
		actor.animationPlayer.doIdelAnimation(actor.direction);
	}

	public void reset () {
		super.reset();
	}

	public void setPosition (float x, float y) {
		endX = x;
		endY = y;
	}


	public float getX () {
		return endX;
	}

	public void setX (float x) {
		endX = x;
	}

	public float getY () {
		return endY;
	}

	public void setY (float y) {
		endY = y;
	}

}
