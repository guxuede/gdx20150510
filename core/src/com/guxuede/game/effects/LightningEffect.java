package com.guxuede.game.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.tools.MathUtils;

/**
 * Created by guxuede on 2016/7/17 .
 */
public class LightningEffect extends AnimationEffect{
    public AnimationEntity targetEntity;

    float lenAnima;
    float lenAnimaTime;

    public LightningEffect(String animationName, AnimationEntity targetEntity) {
        super(animationName);
        this.targetEntity = targetEntity;
        setDuration(0.5f);
        ResourceManager.thunderSound.play();
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
            AnimationEntity target = this.targetEntity;
            float degrees = MathUtils.getAngle(source.getEntityX(),source.getEntityY(),target.getEntityX(),target.getEntityY());
            sprite.setRotation(degrees+90);
            float distance = (float) MathUtils.distance(source.getEntityX(),source.getEntityY(),target.getEntityX(),target.getEntityY());//2单位之间的距离
            float lightLen = 100;//闪电素材的长度
            //for(int i=0;i<getlenAnima(distance/lightLen,animationActor.stateTime);i++){
            int totalDuan = (int) (distance/lightLen);

            //修正闪电出发的位置应该，早于闪电长度的一半，这样，闪电在于施法者的前面
            float drxa = source.getEntityX() + com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen);
            float drya = source.getEntityY() + com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen);
            sprite.setPosition(drxa , drya);
            sprite.draw(batch, parentAlpha);
            //画闪电体
            for(int i=1;i<totalDuan;i++){
                float drx = 0;
                float dry = 0;
                drx = source.getEntityX() + com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen) * i;
                dry = source.getEntityY() + com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen) * i;
                sprite.setPosition(drx , dry);
                sprite.draw(batch, parentAlpha);
            }
            //修正最后打在目标上的闪电位置
            if(distance > lightLen){
                float drx = target.getEntityX() - com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen/2);// * totalDuan - com.badlogic.gdx.math.MathUtils.cosDeg(degrees) * (lightLen);
                float dry = target.getEntityY() - com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen/2);// * totalDuan - com.badlogic.gdx.math.MathUtils.sinDeg(degrees) * (lightLen) ;
                sprite.setPosition(drx , dry);
                sprite.draw(batch, parentAlpha);
            }
        }
    }


    float getlenAnima(float totalLen,float stateTime){
        if(stateTime - lenAnimaTime > 0.05){
            lenAnima ++ ;
            lenAnimaTime = stateTime;
        }
        if(lenAnima > totalLen){
            lenAnima = totalLen;
        }
        return lenAnima;
    }

    public static void main(String[] args) {

        //System.out.println(Vector2.dst2(320,324 , 172,143));
    }

}
