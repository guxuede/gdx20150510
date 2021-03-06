package com.guxuede.game.resource;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guxuede on 2016/5/26 .
 */
public class AnimationHolder {
    public static final String WALK_DOWN_ANIMATION = "walkDownAnimation";
    public static final String WALK_LEFT_ANIMATION = "walkLeftAnimation";
    public static final String WALK_RIGHT_ANIMATION = "walkRightAnimation";
    public static final String WALK_UP_ANIMATION = "walkUpAnimation";
    public static final String STOP_DOWN_ANIMATION = "stopDownAnimation";
    public static final String STOP_LEFT_ANIMATION = "stopLeftAnimation";
    public static final String STOP_RIGHT_ANIMATION = "stopRightAnimation";
    public static final String STOP_UP_ANIMATION = "stopUpAnimation";
    public static final String DEATH_ANIMATION = "deathAnimation";
    public static final String ATTACK_ANIMATION = "attackAnimation";

    /*********************************************/
    private Map<String,Animation> animationMap = new HashMap<String, Animation>();
    private Map<String,SoundHolder> animationSoundMap = new HashMap();
    public int width,height;
    public String name;
    /*********************************************/

    public AnimationHolder(){

    }

    public AnimationHolder(Animation allInOneAnimation){
        animationMap.put(WALK_DOWN_ANIMATION,allInOneAnimation);
        animationMap.put(WALK_LEFT_ANIMATION,allInOneAnimation);
        animationMap.put(WALK_RIGHT_ANIMATION,allInOneAnimation);
        animationMap.put(WALK_UP_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_DOWN_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_LEFT_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_RIGHT_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_UP_ANIMATION,allInOneAnimation);
        animationMap.put(DEATH_ANIMATION,allInOneAnimation);
    }


    public AnimationHolder(Animation allInOneAnimation,Animation deathAnimation){
        animationMap.put(WALK_DOWN_ANIMATION,allInOneAnimation);
        animationMap.put(WALK_LEFT_ANIMATION,allInOneAnimation);
        animationMap.put(WALK_RIGHT_ANIMATION,allInOneAnimation);
        animationMap.put(WALK_UP_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_DOWN_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_LEFT_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_RIGHT_ANIMATION,allInOneAnimation);
        animationMap.put(STOP_UP_ANIMATION,allInOneAnimation);
        animationMap.put(DEATH_ANIMATION,deathAnimation);
    }

    public AnimationHolder(Animation walkDownAnimation, Animation walkLeftAnimation, Animation walkRightAnimation, Animation walkUpAnimation, Animation stopDownAnimation, Animation stopLeftAnimation, Animation stopRightAnimation, Animation stopUpAnimation, Animation deathAnimation) {
        animationMap.put(WALK_DOWN_ANIMATION,walkDownAnimation);
        animationMap.put(WALK_LEFT_ANIMATION,walkLeftAnimation);
        animationMap.put(WALK_RIGHT_ANIMATION,walkRightAnimation);
        animationMap.put(WALK_UP_ANIMATION,walkUpAnimation);
        animationMap.put(STOP_DOWN_ANIMATION,stopDownAnimation);
        animationMap.put(STOP_LEFT_ANIMATION,stopLeftAnimation);
        animationMap.put(STOP_RIGHT_ANIMATION,stopRightAnimation);
        animationMap.put(STOP_UP_ANIMATION,stopUpAnimation);
        animationMap.put(DEATH_ANIMATION,deathAnimation);
    }

    public void addAnimation(String name,Animation animation){
        this.animationMap.put(name,animation);
    }

    public void addSound(String name,SoundHolder sound){
        this.animationSoundMap.put(name,sound);
    }

    public Animation getWalkDownAnimation() {
        return getAnimation(WALK_DOWN_ANIMATION);
    }

    public Animation getWalkLeftAnimation() {
        return getAnimation(WALK_LEFT_ANIMATION);
    }

    public Animation getWalkRightAnimation() {
        return getAnimation(WALK_RIGHT_ANIMATION);
    }

    public Animation getWalkUpAnimation() {
        return getAnimation(WALK_UP_ANIMATION);
    }

    public Animation getStopDownAnimation() {
        return getAnimation(STOP_DOWN_ANIMATION);
    }


    public Animation getStopLeftAnimation() {
        return getAnimation(STOP_LEFT_ANIMATION);
    }

    public Animation getStopRightAnimation() {
        return getAnimation(STOP_RIGHT_ANIMATION);
    }


    public Animation getStopUpAnimation() {
        return getAnimation(STOP_UP_ANIMATION);
    }

    public Animation getDeathAnimation() {
        return getAnimation(DEATH_ANIMATION);
    }


    public Animation getAnimation(String name){
        return animationMap.get(name);
    }

    public SoundHolder getAnimationSound(String name) {
        SoundHolder sh = animationSoundMap.get(name);
        if(sh!=null){
            SoundHolder nw = new SoundHolder(sh.getSound(),sh.isLoop());
            return nw;
        }
        return null;
    }

    public AnimationHolder getCopy(){
        AnimationHolder animationHolder = new AnimationHolder();
        animationHolder.name = this.name;
        animationHolder.width = this.width;
        animationHolder.height = this.height;
        animationHolder.animationMap = new HashMap<String, Animation>();
        for(String animationName : this.animationMap.keySet()){
            Animation animation = this.animationMap.get(animationName);
            animationHolder.animationMap.put(animationName,new Animation(animation.getFrameDuration(),animation.getKeyFrames()));
        }
        return animationHolder;
    }
}
