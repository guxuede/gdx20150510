package com.guxuede.game.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

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
	static public ActorDeathAnimationAction actorDeathAnimation () {
		return actorDeathAnimation(0, null);
	}

	static public ActorDeathAnimationAction actorDeathAnimation (float duration) {
		return actorDeathAnimation(duration, null);
	}

	static public ActorDeathAnimationAction actorDeathAnimation (float duration, Interpolation interpolation) {
		ActorDeathAnimationAction action = action(ActorDeathAnimationAction.class);
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
		//action.setDuration(0);
		//action.setInterpolation(interpolation);
		return action;
	}

    static public Action hackshake(){
        return parallel(sequence(scaleBy(0,0.1f,1),scaleBy(0,-0.1f)),new ActorRLShakeAction(1));
    }
    static public Action magicShake(){
        return parallel(sequence(scaleBy(0,0.1f,0.5f),scaleBy(0,-0.1f)),new ActorUDShakeAction(1));
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
    
    static public GdxSequenceAction gdxSequence (Action action1) {
        GdxSequenceAction action = action(GdxSequenceAction.class);
        action.addAction(action1);
        return action;
    }

    static public GdxSequenceAction gdxSequence (Action action1, Action action2) {
        GdxSequenceAction action = action(GdxSequenceAction.class);
        action.addAction(action1);
        action.addAction(action2);
        return action;
    }

    static public GdxSequenceAction gdxSequence (Action action1, Action action2, Action action3) {
        GdxSequenceAction action = action(GdxSequenceAction.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        return action;
    }

    static public GdxSequenceAction gdxSequence (Action action1, Action action2, Action action3, Action action4) {
        GdxSequenceAction action = action(GdxSequenceAction.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        return action;
    }

    static public GdxSequenceAction gdxSequence (Action action1, Action action2, Action action3, Action action4, Action action5) {
        GdxSequenceAction action = action(GdxSequenceAction.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        action.addAction(action5);
        return action;
    }

    static public GdxSequenceAction gdxSequence (Action... actions) {
        GdxSequenceAction action = action(GdxSequenceAction.class);
        for (int i = 0, n = actions.length; i < n; i++)
            action.addAction(actions[i]);
        return action;
    }

    static public GdxSequenceAction gdxSequence () {
        return action(GdxSequenceAction.class);
    }

    static public GdxParallelAction gdxParallel (Action action1) {
        GdxParallelAction action = action(GdxParallelAction.class);
        action.addAction(action1);
        return action;
    }

    static public GdxParallelAction gdxParallel (Action action1, Action action2) {
        GdxParallelAction action = action(GdxParallelAction.class);
        action.addAction(action1);
        action.addAction(action2);
        return action;
    }

    static public GdxParallelAction gdxParallel (Action action1, Action action2, Action action3) {
        GdxParallelAction action = action(GdxParallelAction.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        return action;
    }

    static public GdxParallelAction gdxParallel (Action action1, Action action2, Action action3, Action action4) {
        GdxParallelAction action = action(GdxParallelAction.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        return action;
    }

    static public GdxParallelAction gdxParallel (Action action1, Action action2, Action action3, Action action4, Action action5) {
        GdxParallelAction action = action(GdxParallelAction.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        action.addAction(action5);
        return action;
    }

    static public GdxParallelAction gdxParallel (Action... actions) {
        GdxParallelAction action = action(GdxParallelAction.class);
        for (int i = 0, n = actions.length; i < n; i++)
            action.addAction(actions[i]);
        return action;
    }

    static public GdxParallelAction gdxParallel () {
        return action(GdxParallelAction.class);
    }

}
