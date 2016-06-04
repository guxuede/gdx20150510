package com.guxuede.game.resource;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guxuede.game.libgdx.GdxSprite;

/**
 * Created by guxuede on 2016/5/26 .
 */
public class BX8ActorAnimationHolderParse implements AnimationHolderParse{
    @Override
    public AnimationHolder parse(TextureRegion ar) {
        AnimationHolder animationHolder = new AnimationHolder();

        int actorWidth=animationHolder.width = ar.getRegionWidth()/4;
        int actorHeight=animationHolder.height = ar.getRegionHeight()/4;

        TextureRegion[][] tmp=ar.split(actorWidth,actorHeight);
        for(int i=0;i<tmp.length;i++){
            for(int j=0;j<tmp[0].length;j++){
                tmp[i][j] = new GdxSprite(tmp[i][j]);
            }
        }
        animationHolder.addAnimation(AnimationHolder.WALK_DOWN_ANIMATION,  new Animation(stepDuration, tmp[0][0], tmp[0][1] ,tmp[0][2], tmp[0][3]));
        animationHolder.addAnimation(AnimationHolder.WALK_LEFT_ANIMATION , new Animation(stepDuration, tmp[1][0], tmp[1][1] ,tmp[1][2], tmp[1][3]));
        animationHolder.addAnimation(AnimationHolder.WALK_RIGHT_ANIMATION, new Animation(stepDuration, tmp[2][0], tmp[2][1] ,tmp[2][2], tmp[2][3]));
        animationHolder.addAnimation(AnimationHolder.WALK_UP_ANIMATION   , new Animation(stepDuration, tmp[3][0], tmp[3][1] ,tmp[3][2], tmp[3][3]));

        animationHolder.addAnimation(AnimationHolder.STOP_DOWN_ANIMATION,  new Animation(0.7f, tmp[0][0], tmp[0][1] ,tmp[0][2], tmp[0][3]));
        animationHolder.addAnimation(AnimationHolder.STOP_LEFT_ANIMATION , new Animation(0.2f, tmp[1][2]));
        animationHolder.addAnimation(AnimationHolder.STOP_RIGHT_ANIMATION, new Animation(0.2f, tmp[2][2]));
        animationHolder.addAnimation(AnimationHolder.STOP_UP_ANIMATION   , new Animation(0.2f, tmp[3][2]));
        return animationHolder;
    }
}
