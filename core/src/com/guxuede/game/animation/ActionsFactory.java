package com.guxuede.game.animation;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.guxuede.game.animation.ActorDeathAnimationAction;
import com.guxuede.game.animation.ActorJumpAction;
import com.guxuede.game.animation.ActorMoveAction;
import com.guxuede.game.animation.ActorMoveAnimationAction;
import com.guxuede.game.animation.ActorMoveDirectiveAction;

public class ActionsFactory extends Actions{
//
	static public ActorMoveDirectiveAction actorMoveDirective (int direction) {
		return actorMoveDirective(direction, 0, null);
	}

	static public ActorMoveDirectiveAction actorMoveDirective (int direction, float duration) {
		return actorMoveDirective(direction, duration, null);
	}

	static public ActorMoveDirectiveAction actorMoveDirective (int direction, float duration, Interpolation interpolation) {
		ActorMoveDirectiveAction action = action(ActorMoveDirectiveAction.class);
		action.direction = direction;
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	//
	static public ActorMoveAnimationAction actorMoveAnimation (Integer direction) {
		return actorMoveAnimation(direction, 0, null);
	}

	static public ActorMoveAnimationAction actorMoveAnimation (Integer direction, float duration) {
		return actorMoveAnimation(direction, duration, null);
	}

	static public ActorMoveAnimationAction actorMoveAnimation (Integer direction, float duration, Interpolation interpolation) {
		ActorMoveAnimationAction action = action(ActorMoveAnimationAction.class);
		action.direction = direction;
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	//
	static public ActorDeathAnimationAction actorDeathAnimation (Integer direction) {
		return actorDeathAnimation(direction, 0, null);
	}

	static public ActorDeathAnimationAction actorDeathAnimation (Integer direction, float duration) {
		return actorDeathAnimation(direction, duration, null);
	}

	static public ActorDeathAnimationAction actorDeathAnimation (Integer direction, float duration, Interpolation interpolation) {
		ActorDeathAnimationAction action = action(ActorDeathAnimationAction.class);
		action.direction = direction;
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}
	
	//
	static public ActorMoveAction actorMoveAction (float x,float y){
		return actorMoveAction(x,y, null);
	}
	static public ActorMoveAction actorMoveAction (float x, float y,Interpolation interpolation) {
		ActorMoveAction action = action(ActorMoveAction.class);
		action.setPosition(x, y);
		action.setDuration(0);
		action.setInterpolation(interpolation);
		return action;
	}

	//
	static public ActorJumpAction actorJumpAction (float x,float y){
		return actorJumpAction(x,y, null);
	}
	static public ActorJumpAction actorJumpAction (float x, float y,Interpolation interpolation) {
		ActorJumpAction action = action(ActorJumpAction.class);
		action.setPosition(x, y);
		action.setDuration(0);
		action.setInterpolation(interpolation);
		return action;
	}

//	static public Action jumpAction(float length){
//		
//		return parallel(
//				actorGoForwardAction(length, 0.5f),
//				actorMoveAnimation(null, 0.5f),
//				sequence(scaleBy(0.2f, 0.2f, 0.25f),
//						 scaleBy(-0.2f, -0.2f, 0.25f))
//						
//		);
//	}
	static public Action jumpAction(float x,float y){
		return parallel(
				actorJumpAction(x,y)
				//actorMoveAction(x, y)
				//actorMoveAnimation(null, 0.5f),
				//sequence(scaleBy(0.2f, 0.2f, 0.25f),
						// scaleBy(-0.2f, -0.2f, 0.25f))
						
		);
	}
}
