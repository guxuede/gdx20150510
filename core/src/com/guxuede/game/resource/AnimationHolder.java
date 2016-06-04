package com.guxuede.game.resource;

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


    public Map<String,Animation> animationMap = new HashMap<String, Animation>();
    public int width,height;
    public String name;

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


    public Animation getWalkDownAnimation() {
        return animationMap.get(WALK_DOWN_ANIMATION);
    }

    public Animation getWalkLeftAnimation() {
        return animationMap.get(WALK_LEFT_ANIMATION);
    }

    public Animation getWalkRightAnimation() {
        return animationMap.get(WALK_RIGHT_ANIMATION);
    }

    public Animation getWalkUpAnimation() {
        return animationMap.get(WALK_UP_ANIMATION);
    }

    public Animation getStopDownAnimation() {
        return animationMap.get(STOP_DOWN_ANIMATION);
    }


    public Animation getStopLeftAnimation() {
        return animationMap.get(STOP_LEFT_ANIMATION);
    }

    public Animation getStopRightAnimation() {
        return animationMap.get(STOP_RIGHT_ANIMATION);
    }


    public Animation getStopUpAnimation() {
        return animationMap.get(STOP_UP_ANIMATION);
    }

    public Animation getDeathAnimation() {
        return animationMap.get(DEATH_ANIMATION);
    }


}
