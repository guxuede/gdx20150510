package com.guxuede.game.action.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.tools.MathUtils;

/**
 * Created by guxuede on 2016/7/17 .
 */
public class LightningEffect extends AnimationEffect{
    public AnimationEntity lightingTargetEntity;

    public LightningEffect(){

    }

    public LightningEffect(String animationName, AnimationEntity lightingTargetEntity) {
        super(animationName);
        this.lightingTargetEntity = lightingTargetEntity;
        setDuration(0.5f);
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
            AnimationEntity target = this.lightingTargetEntity;
            float degrees = MathUtils.getAngle(source.getCenterX(),source.getCenterY(),target.getCenterX(),target.getCenterY());
            sprite.setRotation(degrees+90);
            float distance = (float) MathUtils.distance(source.getCenterX(),source.getCenterY(),target.getCenterX(),target.getCenterY());//2单位之间的距离
            float lightLen = 100;//闪电素材的长度
            //for(int i=0;i<getlenAnima(distance/lightLen,animationActor.stateTime);i++){
            int totalDuan = (int) (distance/lightLen);

            //修正闪电出发的位置应该，早于闪电长度的一半，这样，闪电在于施法者的前面
            float drxa = source.getCenterX() + com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen);
            float drya = source.getCenterY() + com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen);
            sprite.setPosition(drxa , drya);
            sprite.draw(batch, parentAlpha);
            //画闪电体
            for(int i=1;i<totalDuan;i++){
                float drx = 0;
                float dry = 0;
                drx = source.getCenterX() + com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen) * i;
                dry = source.getCenterY() + com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen) * i;
                sprite.setPosition(drx , dry);
                sprite.draw(batch, parentAlpha);
            }
            //修正最后打在目标上的闪电位置
            if(distance > lightLen){
                float drx = target.getCenterX() - com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen/2);// * totalDuan - com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen);
                float dry = target.getCenterY() - com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen/2);// * totalDuan - com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen) ;
                sprite.setPosition(drx , dry);
                sprite.draw(batch, parentAlpha);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        lightingTargetEntity = null;
    }

    public void setLightingTargetEntity(AnimationEntity lightingTargetEntity) {
        this.lightingTargetEntity = lightingTargetEntity;
    }
}
