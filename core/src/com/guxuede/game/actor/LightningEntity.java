package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.StageWorld;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.resource.ActorAnimationPlayer;
import com.guxuede.game.tools.MathUtils;

/**
 * Created by guxuede on 2016/7/16 .
 */
public class LightningEntity extends AnimationProjection {

    public AnimationEntity targetAnimation;

    public LightningEntity(ActorAnimationPlayer animationPlayer, StageWorld world) {
        super(animationPlayer, world);
    }

    float lenAnima;
    float lenAnimaTime;

    @Override
    public void drawBody(Batch batch, float parentAlpha) {
        GdxSprite sprite = (GdxSprite) animationPlayer.getKeyFrame();
        if (sprite != null) {
            AnimationEntity source = this.sourceActor;
            AnimationEntity target = this.targetAnimation;
            float degrees = MathUtils.getAngle(source.getCenterX(),source.getCenterY(),target.getCenterX(),target.getCenterY());
            float distance = (float) MathUtils.distance(source.getCenterX(),source.getCenterY(),target.getCenterX(),target.getCenterY());
            float lightLen = getHeight();
            for(int i=0;i<getlenAnima(distance/lightLen);i++){
                float drx = source.getCenterX();
                float dry = source.getCenterY();
                drx = drx+com.badlogic.gdx.math.MathUtils.cosDeg(degrees)*lightLen*i;
                dry = dry+com.badlogic.gdx.math.MathUtils.sinDeg(degrees)*lightLen*i;
                sprite.setPosition(drx + drawOffSetX, dry + drawOffSetY);
                sprite.draw(batch, parentAlpha, degrees+90, getScaleX(), getScaleY(), getColor());
            }

        }
    }

    float getlenAnima(float totalLen){
        if(stateTime - lenAnimaTime > 1){
            lenAnima ++ ;
            lenAnimaTime = stateTime;
        }
        if(lenAnima > totalLen){
            lenAnima = totalLen;
        }
        return lenAnima;
    }
}
