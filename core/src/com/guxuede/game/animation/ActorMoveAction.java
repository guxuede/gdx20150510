
package com.guxuede.game.animation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;

/**
 * 给定角色速度，使其移动
 */
public class ActorMoveAction extends TemporalAction {
	private float startX, startY;
	private float endX,endY;
	float degrees;

	protected void begin () {
		AnimationEntity actor = ((AnimationEntity)target);
		startX = actor.getCenterX();
		startY = actor.getCenterY();
		float duration = (float) (MathUtils.distance(startX,startY,endX,endY)/actor.speed);
		this.setDuration(duration);
		 degrees = MathUtils.getAngle(startX,startY,endX,endY);
		
		actor.turnDirection(degrees);
		Vector2 v = new Vector2(100, 100);
		v.setAngle(degrees);
		actor.setLinearVelocity(v);
		
	}

	protected void update (float percent) {
		AnimationEntity actor = ((AnimationEntity)target);
		Vector2 v = new Vector2(100, 100);
		v.setAngle(degrees);
		actor.setLinearVelocity(v);
		actor.doMoveAnimation();
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
