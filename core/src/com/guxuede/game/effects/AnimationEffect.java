package com.guxuede.game.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxEffect;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.libgdx.ResourceManager;

/**
 * Created by guxuede on 2016/6/2 .
 */
public class AnimationEffect extends GdxEffect {

    public Animation effectAnimation;

    public AnimationEffect(){

    }

    public AnimationEffect(String animationName){
        Animation effectAnimation = ResourceManager.getAnimationHolder(animationName).getStopDownAnimation();
        setEffectAnimation(effectAnimation);
    }

    @Override
    public void drawBodyEffect(Batch batch, float parentAlpha){
        if(effectAnimation!=null){
            AnimationEntity animationActor = getAnimationEntity();
            GdxSprite sprite = (GdxSprite) effectAnimation.getKeyFrame(animationActor.stateTime, true);
            sprite.setPosition(animationActor.getEntityX(),animationActor.getEntityY());
            sprite.draw(batch, parentAlpha);
        }
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
