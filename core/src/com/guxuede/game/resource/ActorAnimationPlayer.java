package com.guxuede.game.resource;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guxuede.game.StageWorld;
import com.guxuede.game.actor.ActorEventListener;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.tools.SoundUtils;

/**
 * 角色动画播放器，控制角色播放
 */
public class ActorAnimationPlayer implements ActorEventListener{

    public AnimationHolder animationHolder;
    public Animation currentAnimation;
    public StageWorld stageWorld;
    //********************************************************
    //
    //********************************************************
    public int width,height;
    public float stateTime;

    //********************************************************
    //
    //********************************************************
    public String lastAnimationName;
    protected SoundHolder sound;

	public ActorAnimationPlayer(StageWorld stageWorld,AnimationHolder animationHolder) {
        this.animationHolder = animationHolder;
        this.stageWorld = stageWorld;
        this.width = this.animationHolder.width;
        this.height = this.animationHolder.height;
        this.stateTime = 0f;
        doIdelAnimation(AnimationActor.DOWN);
    }
	
	public void act(float delta,Actor entity){
        stateTime += delta;
        if(sound!=null){
            sound.act(delta,entity);
        }
    }

    /**
     * 设计一个好的方法，来恢复状态
     * @param animationHolder
     */
    public void setAnimationHolder(AnimationHolder animationHolder){
        this.animationHolder = animationHolder;
        //action changed,consider to restore player status
        doAnimation(lastAnimationName);
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
        doAnimation(AnimationHolder.WALK_UP_ANIMATION);
	}

	public void doMoveDownAnimation() {
        doAnimation(AnimationHolder.WALK_DOWN_ANIMATION);
	}

	public void doMoveLeftAnimation() {
        doAnimation(AnimationHolder.WALK_LEFT_ANIMATION);
	}

	public void doMoveRightAnimation() {
        doAnimation(AnimationHolder.WALK_RIGHT_ANIMATION);
	}
	
	public void doIdelAnimation(int direction) {
		switch (direction) {
		case AnimationActor.UP:
            doAnimation(AnimationHolder.STOP_UP_ANIMATION);
			break;
		case AnimationActor.DOWN:
            doAnimation(AnimationHolder.STOP_DOWN_ANIMATION);
			break;
		case AnimationActor.RIGHT:
            doAnimation(AnimationHolder.STOP_RIGHT_ANIMATION);
			break;
		case AnimationActor.LEFT:
            doAnimation(AnimationHolder.STOP_LEFT_ANIMATION);
			break;
		default:
            doAnimation(AnimationHolder.STOP_DOWN_ANIMATION);
			break;
		}
	}
	
	public void doDeathAnimation() {
        doAnimation(AnimationHolder.DEATH_ANIMATION);
	}

    public void doAttackAnimation(){
        doAnimation(AnimationHolder.ATTACK_ANIMATION);
    }


	public void doAnimation(String animationName){
        Animation animation = animationHolder.getAnimation(animationName);
        //if action not change,no need to re do Animation
        if(currentAnimation != animation){
            onAnimationChange(currentAnimation,animation,animationName);
            currentAnimation = animation;
            stateTime = 0;
            lastAnimationName = animationName;
        }
    }

    protected void onAnimationChange(Animation oldAnimation,Animation newAnimation,String animationName){
        if(sound!=null){
            sound.stop();
            stageWorld.getSoundManager().unRegisterSound(sound);
        }

        SoundHolder sound = animationHolder.getAnimationSound(animationName);
        if(sound!=null){
            this.sound =sound;
            sound.play();
            stageWorld.getSoundManager().registerSound(sound);
        }
    }

    public void onDispose(){
        if(sound!=null){
            sound.stop();
            stageWorld.getSoundManager().unRegisterSound(sound);
        }
    }
}
