package com.guxuede.game.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guxuede.game.actor.ActorEventListener;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationEntity;

/**
 * 角色动画播放器，控制角色播放
 */
public class ActorAnimationPlayer implements ActorEventListener{

    public AnimationHolder animationHolder;
    public Animation currentAnimation;
    //********************************************************
    //
    //********************************************************
    public int width,height;
    public float stateTime;

    //********************************************************
    //
    //********************************************************
    public int lastPlayerMethod;
    public int lastPlayerDirection;

	public ActorAnimationPlayer(AnimationHolder animationHolder) {
        this.animationHolder = animationHolder;
        this.width = this.animationHolder.width;
        this.height = this.animationHolder.height;
        this.stateTime = 0f;
        doIdelAnimation(AnimationActor.DOWN);
    }
	
	public void act(){
        stateTime += Gdx.graphics.getDeltaTime();
	}

    /**
     * 设计一个好的方法，来恢复状态
     * @param animationHolder
     */
    public void setAnimationHolder(AnimationHolder animationHolder){
        this.animationHolder = animationHolder;
        //animation changed,consider to restore player status
        switch (lastPlayerMethod){
            case 1: doMoveUpAnimation();break;
            case 2: doMoveDownAnimation();break;
            case 3: doMoveLeftAnimation();break;
            case 4: doMoveRightAnimation();break;
            case 5: doIdelAnimation(lastPlayerDirection);break;
            case 6: doDeathAnimation();break;
        }
    }
	
	public TextureRegion getKeyFrame(){

		return currentAnimation.getKeyFrame(stateTime, true);
	}

	@Override
	public void onMoveUp() {
		doMoveUpAnimation();
	}

	@Override
	public void onMoveDown() {
		doMoveDownAnimation();
	}

	@Override
	public void onMoveLeft() {
		doMoveLeftAnimation();
	}

	@Override
	public void onMoveRight() {
		doMoveRightAnimation();
	}

	@Override
	public void onStop(int direction) {
		doIdelAnimation(direction);
	}
	
	public void doMoveUpAnimation() {
		currentAnimation = animationHolder.getWalkUpAnimation();
        lastPlayerMethod = 1;
	}

	public void doMoveDownAnimation() {
		currentAnimation = animationHolder.getWalkDownAnimation();
        lastPlayerMethod = 2;
	}

	public void doMoveLeftAnimation() {
		currentAnimation = animationHolder.getWalkLeftAnimation();
        lastPlayerMethod=3;
	}

	public void doMoveRightAnimation() {
		currentAnimation = animationHolder.getWalkRightAnimation();
        lastPlayerMethod=4;
	}
	
	public void doIdelAnimation(int direction) {
        lastPlayerMethod = 5;
        lastPlayerDirection = direction;
		switch (direction) {
		case AnimationActor.UP:
			currentAnimation = animationHolder.getStopUpAnimation();
			break;
		case AnimationActor.DOWN:
			currentAnimation = animationHolder.getStopDownAnimation();
			break;
		case AnimationActor.RIGHT:
			currentAnimation = animationHolder.getStopRightAnimation();
			break;
		case AnimationActor.LEFT:
			currentAnimation = animationHolder.getStopLeftAnimation();
			break;
		default:
			currentAnimation = animationHolder.getStopDownAnimation();
			break;
		}
	}
	
	public void doDeathAnimation() {
		currentAnimation = animationHolder.getDeathAnimation();
        lastPlayerMethod=6;
	}
	
}
