package com.guxuede.game.action.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.guxuede.game.action.TracksEllipseFormula;
import com.guxuede.game.action.TracksFormula;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxEffect;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.resource.AnimationHolder;
import com.guxuede.game.resource.SoundHolder;

/**
 * Created by guxuede on 2016/6/2 .
 */
public class AnimationEffect extends GdxEffect {

    public float drawOffSetX,drawOffSetY;
    public TracksFormula tracksFormula;

    public String animationName;
    public Animation effectAnimation;
    public SoundHolder sound;

    public AnimationEffect(){

    }

    public AnimationEffect(String animationName){
        this.animationName = animationName;
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(animationName);
        Animation effectAnimation = animationHolder.getStopDownAnimation();
        setEffectAnimation(effectAnimation);
        //tracksFormula = new TracksEllipseFormula();
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
            sprite.setPosition(animationActor.getCenterX() + drawOffSetX,animationActor.getCenterY() + drawOffSetY);
            sprite.setRotation(animationActor.getRotation());
            sprite.draw(batch, parentAlpha);
        }
    }

    @Override
    protected void update(float percent) {
        super.update(percent);
        if(tracksFormula!=null){
            drawOffSetX = tracksFormula.getX(percent);
            drawOffSetY = tracksFormula.getY(percent);
        }
    }

    @Override
    public void begin() {
        if(animationName!=null){
            AnimationHolder animationHolder = ResourceManager.getAnimationHolder(animationName);
            sound = animationHolder.getAnimationSound(AnimationHolder.STOP_DOWN_ANIMATION);
            if(sound!=null){
                ((AnimationEntity)target).getWorld().getSoundManager().registerSound(sound);
                sound.play();
            }
        }
    }

    @Override
    public void end() {
        if(sound!=null){
            sound.stop();
            ((AnimationEntity)target).getWorld().getSoundManager().unRegisterSound(sound);
        }
    }

    @Override
    public boolean act(float delta) {
        if(sound!=null){
            sound.act(delta,actor);
        }
        return super.act(delta);
    }

    public void setEffectAnimation(String animationName){
        this.animationName = animationName;
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(animationName);
        Animation effectAnimation = animationHolder.getStopDownAnimation();
        setEffectAnimation(effectAnimation);
    }

    public void setEffectAnimation(Animation effectAnimation) {
        this.effectAnimation = effectAnimation;
        this.setDuration(effectAnimation.getAnimationDuration());
    }

    @Override
    public void reset() {
        super.reset();
        effectAnimation = null;
        animationName = null;
        if(sound!=null){
            sound.stop();
            if(target!=null){
                ((AnimationEntity)target).getWorld().getSoundManager().unRegisterSound(sound);
            }
            sound = null;
        }
    }
}
