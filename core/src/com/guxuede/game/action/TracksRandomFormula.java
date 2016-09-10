package com.guxuede.game.action;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class TracksRandomFormula implements TracksFormula {

    public static final int RANGE =10;

    @Override
    public float getX(float percent) {
        return MathUtils.random(0, RANGE);
    }

    @Override
    public float getY(float percent) {
        return MathUtils.random(0,RANGE);
    }
}
