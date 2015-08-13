package com.guxuede.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guxuede.game.actor.ActorEventListener;
import com.guxuede.game.actor.AnimationActor;

public class ActorAnimationGen implements ActorEventListener{
	
	public int width,height;
	public AnimationActor actor;
	
	public float stateTime;
	 
	public Animation  walkDownAnimation;
	public Animation  walkLeftAnimation;
	public Animation  walkRightAnimation;
	public Animation  walkUpAnimation;
	 
	public Animation  stopDownAnimation;
	public Animation  stopLeftAnimation;
	 public Animation  stopRightAnimation;
	 public Animation  stopUpAnimation;
	 
	 public Animation  currentAnimation;
	 
	 public Animation deathAnimation;
	 
	private float stepDuration=0.1f;

	
	public ActorAnimationGen(AnimationActor actor,TextureRegion ar) {
		this.actor = actor;
		
		//子弹类型的素材
		if(ar.getRegionWidth() == 96 && ar.getRegionHeight() == 16){
			int actorWidth=width=16;
			int actorHeight=height=16;
			TextureRegion[][] tmp=ar.split(actorWidth,actorHeight);
			for(int i=0;i<tmp.length;i++){
				for(int j=0;j<tmp[0].length;j++){
					Sprite s=new Sprite(tmp[i][j]);
					tmp[i][j] =s;
				}
			}
			walkDownAnimation = new Animation(stepDuration, tmp[0][0], tmp[0][1] ,tmp[0][2]);
			walkLeftAnimation = walkDownAnimation;
			walkRightAnimation= walkDownAnimation;
			walkUpAnimation   = walkDownAnimation; 
			
			stopDownAnimation = walkDownAnimation; 
			stopLeftAnimation = walkDownAnimation; 
			stopRightAnimation= walkDownAnimation; 
			stopUpAnimation   = walkDownAnimation; 
			deathAnimation = new Animation(stepDuration, tmp[0][3], tmp[0][4]);
			
		}else{//人物类型的素材
			int actorWidth=width=ar.getRegionWidth()/4;
			int actorHeight=height=ar.getRegionHeight()/4;
			
			TextureRegion[][] tmp=ar.split(actorWidth,actorHeight);
			for(int i=0;i<tmp.length;i++){
				for(int j=0;j<tmp[0].length;j++){
					tmp[i][j] = new Sprite(tmp[i][j]);
				}
			}
			walkDownAnimation = new Animation(stepDuration, tmp[0][0], tmp[0][1] ,tmp[0][2], tmp[0][3]);
			walkLeftAnimation = new Animation(stepDuration, tmp[1][0], tmp[1][1] ,tmp[1][2], tmp[1][3]); 
			walkRightAnimation= new Animation(stepDuration, tmp[2][0], tmp[2][1] ,tmp[2][2], tmp[2][3]); 
			walkUpAnimation   = new Animation(stepDuration, tmp[3][0], tmp[3][1] ,tmp[3][2], tmp[3][3]); 
			
			stopDownAnimation = new Animation(0.2f, tmp[0][2]); 
			stopLeftAnimation = new Animation(0.2f, tmp[1][2]); 
			stopRightAnimation= new Animation(0.2f, tmp[2][2]); 
			stopUpAnimation   = new Animation(0.2f, tmp[3][2]); 
			
		}
        currentAnimation  = stopDownAnimation;
        
        stateTime = 0f;     
	}
	
	
	public void act(){
	}
	
	public Animation getCurrentAnimation(){
		return currentAnimation;
		
	}
	
	public TextureRegion getKeyFrame(){
		stateTime += Gdx.graphics.getDeltaTime();
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
	public void onStop() {
		doIdelAnimation();
	}
	
	public void doMoveUpAnimation() {
		currentAnimation = walkUpAnimation;
	}

	public void doMoveDownAnimation() {
		currentAnimation = walkDownAnimation;
		
	}

	public void doMoveLeftAnimation() {
		currentAnimation = walkLeftAnimation;
	}

	public void doMoveRightAnimation() {
		currentAnimation = walkRightAnimation;
	}
	
	public void doIdelAnimation() {
		switch (actor.direction) {
		case AnimationActor.UP:
			currentAnimation = stopUpAnimation;
			break;
		case AnimationActor.DOWN:
			currentAnimation = stopDownAnimation;
			break;
		case AnimationActor.RIGHT:
			currentAnimation = stopRightAnimation;
			break;
		case AnimationActor.LEFT:
			currentAnimation = stopLeftAnimation;
			break;
		default:
			currentAnimation = stopDownAnimation;
			break;
		}
	}
	
	public void doDeathAnimation() {
		currentAnimation = deathAnimation;
	}
	
}
