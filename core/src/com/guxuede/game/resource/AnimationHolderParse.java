package com.guxuede.game.resource;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by guxuede on 2016/5/26 .
 */
public interface AnimationHolderParse {

    static final float stepDuration=0.1f;

    public AnimationHolder parse(TextureRegion ar);
}
