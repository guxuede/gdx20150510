
package com.guxuede.game.animation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;


public class ActorMoveAction extends TemporalAction {
	private float startX, startY;
	private float endX,endY;
	float degrees;

	protected void begin () {
		AnimationEntity actor = ((AnimationEntity)target);
		startX = actor.body.getPosition().x;
		startY = actor.body.getPosition().y;
		float duration = (float) (MathUtils.distance(startX,startY,endX,endY)/actor.speed);
		this.setDuration(duration);
		 degrees = MathUtils.getAngle(startX,startY,endX,endY);
		
		actor.turnDirection(degrees);
		actor.body.setAwake(true);
		Vector2 v = new Vector2(100, 100);
		v.setAngle(degrees);
		actor.body.setLinearVelocity(v);
		
	}

	protected void update (float percent) {
		AnimationEntity actor = ((AnimationEntity)target);
		//actor.body.setTransform(startX + (endX - startX) * percent,startY + (endY - startY) * percent, 99);
		//target.setPosition(startX + (endX - startX) * percent, startY + (endY - startY) * percent);
		Vector2 v = new Vector2(100, 100);
		v.setAngle(degrees);
		actor.body.setLinearVelocity(v);
		
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
