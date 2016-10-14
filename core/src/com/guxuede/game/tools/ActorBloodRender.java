package com.guxuede.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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
    private Camera camera;
    public ActorBloodRender(Camera camera){
        shapes = new ShapeRenderer();
        shapes.setAutoShapeType(true);
        this.camera = camera;
    }

    public void render(Array<Actor> actorArray, Batch batch){
        shapes.setProjectionMatrix(camera.combined);
        shapes.begin();
        for(Actor actor : actorArray){
            if(actor instanceof AnimationActor){
                AnimationActor animationActor = (AnimationActor) actor;
                Vector2 p = animationActor.getTopPosition();
                shapes.set(ShapeRenderer.ShapeType.Filled);
                shapes.setColor(Color.GRAY);
                shapes.rect(p.x,p.y,BLOOD_WIDTH,BLOOD_HEIGHT);
                shapes.set(ShapeRenderer.ShapeType.Line);
                shapes.setColor(Color.BLACK);
                shapes.rect(p.x,p.y,BLOOD_WIDTH,BLOOD_HEIGHT);

                shapes.set(ShapeRenderer.ShapeType.Filled);
                shapes.setColor(Color.GREEN);
                shapes.rect(p.x+1,p.y+1,BLOOD_WIDTH*(animationActor.currentHitPoint/animationActor.hitPoint),4f);
            }
        }
        shapes.end();
    }
}
