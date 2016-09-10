package com.guxuede.game.action;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class TracksEllipseFormula implements TracksFormula{

    public static final float shortShaft = 25;
    public static final float longShaft = 50;

    @Override
    public float getX(float percent) {
        return longShaft*MathUtils.cosDeg(360 * percent * 20);
    }

    @Override
    public float getY(float percent) {
        return shortShaft*MathUtils.sinDeg(360 * percent * 20);
    }
}
