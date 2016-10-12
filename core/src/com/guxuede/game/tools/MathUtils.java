package com.guxuede.game.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.AnimationProjection;

import java.util.List;

public class MathUtils {

    /**
     * 总是返回游戏中正值的角度0~360
     * Compute the angle between two points
     * return p1 and p2 angle (degree)
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
	public static float getAngle(float startX, float startY,float endX,float endY){
		double d=Math.atan((endY-startY) / (endX-startX))/ 3.14 * 180;
		if(endY > startY && d < 0){
			d = 90*2 + d;
		}else if(endY < startY && d > 0){
			d = 90*2 + d;
		}else if(endY < startY && d < 0){
			d = 90*4 + d;
		}
		return (float) d;
	}

    /**
     * 总是返回游戏中正值的角度0~360
     * @param p1
     * @param p2
     * @return
     */
    public static float getAngle(Vector2 p1,Vector2 p2){
        return getAngle(p1.x,p1.y,p2.x,p2.y);
    }
	
    /**
     * 返回从此 Point2D 到指定点的距离的平方
     * Returns the square of the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the square of the distance between the two
     * sets of specified coordinates.
     * @since 1.2
     */
    public static float distanceSq(float x1, float y1,
    		float x2, float y2)
    {
        x1 -= x2;
        y1 -= y2;
        return (x1 * x1 + y1 * y1);
    }

    /**
     * 返回两个点之间距离。
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the distance between the two sets of specified
     * coordinates.
     * @since 1.2
     */
    public static double distance(float x1, float y1,
    		float x2, float y2)
    {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }


    /**
     * 找到最近This的单位
     * @param actorList
     * @param findedList
     * @param This
     */
    public static void findClosestEntry(Array<Actor> actorList, List<AnimationEntity> findedList, AnimationEntity This){
        AnimationEntity finded = null;
        float distance = Float.MAX_VALUE;
        for(Actor actor : actorList){
            if(actor instanceof AnimationEntity
                    && !(actor instanceof AnimationProjection)
                    && actor != This
                    && (findedList == null || !findedList.contains(actor))
                    ){
                AnimationEntity entity = (AnimationEntity) actor;
                float d = Vector2.dst(entity.getCenterX(),entity.getCenterY(),This.getCenterX(),This.getCenterY());
                if(d < distance){
                    distance = d;
                    finded = entity;
                }
            }
        }
        if(finded!=null){
            findedList.add(finded);
            findClosestEntry(actorList,findedList,This);
        }
    }

    public static final float EPSILON = 0.1f;
    public static boolean isBetween11(Vector2 a,Vector2 b,Vector2 c){

        float crossproduct = (c.y - a.y) * (b.x - a.x) - (c.x - a.x) * (b.y - a.y);
        return false;
        //return -epsilon < crossproduct < epsilon && min(a.x, b.x) <= c.x <= max(a.x, b.x) && min(a.y, b.y) <= c.y <= max(a.y, b.y)
    }
    /**
     * 判断点c是否落在【点a和点b】的直线段上
     * @param a
     * @param c
     * @param b
     * @return
     */
    public static boolean isBetween(Vector2 a,Vector2 c,Vector2 b){
        float cc = a.dst(c) + c.dst(b) - a.dst(b);
        return -EPSILON < cc && cc < EPSILON;
    }

    public static void main(String[] args) {
//		System.err.println(distance(0, 0, -1, -1));
    	System.err.println(Math.cos(2*Math.PI * 60 / 360));
	}

}
