package com.guxuede.game.tools;

public class MathUtils {
	
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
     * Returns the distance between two points.
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
    
    
    public static void main(String[] args) {
//		System.err.println(distance(0, 0, -1, -1));
    	System.err.println(Math.cos(2*Math.PI * 60 / 360));
	}

}
