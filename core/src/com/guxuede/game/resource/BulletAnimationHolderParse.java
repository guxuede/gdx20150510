package com.guxuede.game.resource;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guxuede.game.libgdx.GdxSprite;

/**
 * Created by guxuede on 2016/5/26 .
 */
public class BulletAnimationHolderParse implements AnimationHolderParse {

    @Override
    public AnimationHolder parse(TextureRegion ar) {
        if(ar.getRegionWidth() != 96 && ar.getRegionHeight() != 16) {
            throw new RuntimeException("");
        }

        int actorWidth  = 16;
        int actorHeight = 16;
        TextureRegion[][] tmp = ar.split(actorWidth, actorHeight);
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[0].length; j++) {
                GdxSprite s = new GdxSprite(tmp[i][j]);
                tmp[i][j] = s;
            }
        }
        AnimationHolder animationHolder = new AnimationHolder(new Animation(stepDuration, tmp[0][0], tmp[0][1], tmp[0][2])
        ,new Animation(stepDuration, tmp[0][3], tmp[0][4]));
        animationHolder.width = actorWidth;
        animationHolder.height = actorHeight;
        return animationHolder;
    }
}
