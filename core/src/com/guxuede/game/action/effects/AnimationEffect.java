package com.guxuede.game.action.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxEffect;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.AnimationHolder;
import com.guxuede.game.resource.SoundHolder;

/**
 * Created by guxuede on 2016/6/2 .
 */
public class AnimationEffect extends GdxEffect {

    public Animation effectAnimation;
    public SoundHolder sound;

    public AnimationEffect(){

    }

    public AnimationEffect(String animationName){
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(animationName);
        Animation effectAnimation = animationHolder.getStopDownAnimation();
        sound = animationHolder.getAnimationSound(AnimationHolder.STOP_DOWN_ANIMATION);
        setEffectAnimation(effectAnimation);
    }

    public AnimationEffect(String animationName,float duration){
        this(animationName);
        this.setDuration(duration);
    }

    @Override
    public void drawBodyEffect(Batch batch, float parentAlpha){
        if(effectAnimation!=null){
            AnimationEntity animationActor = getAnimationEntity();
            GdxSprite sprite = (GdxSprite) effectAnimation.getKeyFrame(animationActor.stateTime, true);
            sprite.setPosition(animationActor.getCenterX(),animationActor.getCenterY());
            sprite.draw(batch, parentAlpha);
        }
    }

    @Override
    public void begin() {
        if(sound!=null){
            sound.play();
        }
    }

    @Override
    public void end() {
        if(sound!=null){
            sound.stop();
        }
    }

    @Override
    public boolean act(float delta) {
        if(sound!=null){
            sound.act(delta,actor);
        }
        return super.act(delta);
    }


    public void setEffectAnimation(Animation effectAnimation) {
        this.effectAnimation = effectAnimation;
        this.setDuration(effectAnimation.getAnimationDuration());
    }

    @Override
    public void reset() {
        super.reset();
        effectAnimation = null;
    }
}
