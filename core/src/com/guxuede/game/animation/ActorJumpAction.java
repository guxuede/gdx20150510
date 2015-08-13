
package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.tools.ActionsUtils;
import com.guxuede.game.tools.MathUtils;


public class ActorJumpAction extends TemporalAction {
	private float startX, startY;
	private float endX,endY;

	private static final float duration = 0.5f;

	protected void begin () {
		AnimationActor actor = ((AnimationActor)target);
		startX = actor.body.getPosition().x;
		startY = actor.body.getPosition().y;
		this.setDuration(duration);
		actor.addAction(ActionsUtils.sequence(ActionsUtils.scaleBy(0.2f, 0.2f, duration/2),ActionsUtils.scaleBy(-0.2f, -0.2f, duration/2)));
		
		double d = MathUtils.getAngle(startX,startY,endX,endY);
		
		actor.turnDirection((float) d);
	}

	protected void update (float percent) {
		AnimationActor actor = ((AnimationActor)target);
		actor.body.setTransform(startX + (endX - startX) * percent,startY + (endY - startY) * percent, 99);
		//target.setPosition(startX + (endX - startX) * percent, startY + (endY - startY) * percent, alignment);
		final int direction = actor.direction;
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
		super.end();
		AnimationActor actor = ((AnimationActor)target);
		actor.animationGen.doIdelAnimation();
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
