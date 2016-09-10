
package com.guxuede.game.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.MathUtils;

/**
 * 无视物理，直接改变位置
 */
public class ActorJumpAction extends TemporalAction {
	private float startX, startY;
	private float endX,endY;

	private static final float duration = 5f;

	protected void begin () {
		AnimationEntity actor = ((AnimationEntity)target);
		startX = actor.getCenterX();
		startY = actor.getCenterY();
		this.setDuration(duration);
		actor.addAction(ActionsFactory.sequence(ActionsFactory.scaleBy(0.2f, 0.2f, duration / 2), ActionsFactory.scaleBy(-0.2f, -0.2f, duration / 2)));
		
		double d = MathUtils.getAngle(startX,startY,endX,endY);
		
		actor.turnDirection((float) d);
	}

	protected void update (float percent) {
		AnimationEntity actor = ((AnimationEntity)target);
		actor.setEntityPosition(startX + (endX - startX) * percent,startY + (endY - startY) * percent);
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
