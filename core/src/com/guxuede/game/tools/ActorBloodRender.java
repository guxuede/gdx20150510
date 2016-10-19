package com.guxuede.game.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.actor.AnimationActor;

/**
 * Created by guxuede on 2016/10/15 .
 */
public class ActorBloodRender {

    public static final float BLOOD_WIDTH = 50;
    public static final float BLOOD_HEIGHT = 6;

    private ShapeRenderer shapes;
    public ActorBloodRender(){
        shapes = new ShapeRenderer();
        shapes.setAutoShapeType(true);
    }

    public void render(Array<Actor> actorArray, Batch batch){
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        shapes.begin();
        for(Actor actor : actorArray){
            if(actor instanceof AnimationActor){
                AnimationActor animationActor = (AnimationActor) actor;
                if(!animationActor.isInScreen)continue;
                Vector2 p = animationActor.getTopPosition();
                p.add(-BLOOD_WIDTH/2,0);
                shapes.set(ShapeRenderer.ShapeType.Filled);
                shapes.setColor(Color.GRAY);
                shapes.rect(p.x,p.y,BLOOD_WIDTH,BLOOD_HEIGHT);
                shapes.set(ShapeRenderer.ShapeType.Line);
                shapes.setColor(Color.BLACK);
                shapes.rect(p.x,p.y,BLOOD_WIDTH,BLOOD_HEIGHT);

                shapes.set(ShapeRenderer.ShapeType.Filled);
                shapes.setColor(Color.GREEN);
                shapes.rect(p.x+1,p.y+1,BLOOD_WIDTH*(animationActor.currentHitPoint/animationActor.hitPoint)-1,4f);
            }
        }
        shapes.end();
    }
}
