package com.guxuede.game.action.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.tools.MathUtils;

import java.util.List;

/**
 * Created by guxuede on 2016/7/17 .
 */
public class LightningEffectMutilActor extends AnimationEffect{
    public List<AnimationEntity> targetEntries;

    public LightningEffectMutilActor(){

    }

    public LightningEffectMutilActor(String animationName, List<AnimationEntity> targetEntries) {
        super(animationName);
        this.targetEntries = targetEntries;
        setDuration(10f);
    }

    @Override
    protected void update(float percent) {
        super.update(percent);
    }

    @Override
    public void drawBodyEffect(Batch batch, float parentAlpha){
        if(effectAnimation!=null){
            AnimationEntity animationActor = getAnimationEntity();
            GdxSprite sprite = (GdxSprite) effectAnimation.getKeyFrame(animationActor.stateTime, true);
            AnimationEntity source = this.getAnimationEntity();
            for(int i = 0; i< targetEntries.size(); i++){
                AnimationEntity s_v = targetEntries.get(i);
                AnimationEntity t_v = i == 0? source: targetEntries.get(i-1);
                drawLight(batch, parentAlpha, sprite, new Vector2(s_v.getCenterX(), s_v.getCenterY()), new Vector2(t_v.getCenterX(),t_v.getCenterY()));
            }
        }
    }

    private void drawLight(Batch batch, float parentAlpha, GdxSprite sprite, Vector2 source, Vector2 target) {
        float degrees = MathUtils.getAngle(source.x,source.y,target.x,target.y);

        float distance = (float) MathUtils.distance(source.x,source.y,target.x,target.y);//2单位之间的距离
        float lightLen = 128;//闪电素材的长度

        float drxa = source.x + com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (distance/2);
        float drya = source.y + com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (distance/2);
        sprite.setPosition(drxa , drya);
        sprite.draw(batch, parentAlpha,degrees+90,1,distance/lightLen, Color.RED);

    }

    public void setTargetEntries(List<AnimationEntity> targetEntries) {
        this.targetEntries = targetEntries;
    }
}
