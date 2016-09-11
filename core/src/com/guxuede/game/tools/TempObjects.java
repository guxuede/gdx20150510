package com.guxuede.game.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class TempObjects {

    /**
     * 临时变量，主要是为了少创建Vector2,这些变量临时使用,使用它必须保证不要用他们做储存。
     */
    public static final Vector2 temp0Vector2 = new Vector2();
    public static final Vector2 temp1Vector2 = new Vector2();
    public static final Vector2 temp2Vector2 = new Vector2();
    public static final Vector2 temp3Vector2 = new Vector2();

    public static final Vector3 temp0Vector3 = new Vector3();
    public static final Vector3 temp1Vector3 = new Vector3();
}
