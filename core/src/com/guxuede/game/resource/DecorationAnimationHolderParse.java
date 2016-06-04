package com.guxuede.game.resource;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by guxuede on 2016/5/26 .
 */
public class DecorationAnimationHolderParse implements AnimationHolderParse{
    @Override
    public AnimationHolder parse(TextureRegion ar) {
        int actorWidth = 32;
        int actorHeight = 48;
        TextureRegion[][] tmp = ar.split(actorWidth, actorHeight);
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[0].length; j++) {
                Sprite s = new Sprite(tmp[i][j]);
                tmp[i][j] = s;
            }
        }
        int line =1;
        AnimationHolder animationHolder = new AnimationHolder(new Animation(stepDuration, tmp[line][0], tmp[line][1], tmp[line][2]));
        animationHolder.width = actorWidth;
        animationHolder.height = actorHeight;
        return animationHolder;
    }
}
