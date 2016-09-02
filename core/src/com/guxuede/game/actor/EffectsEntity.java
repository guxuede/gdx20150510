package com.guxuede.game.actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.resource.ActorAnimationPlayer;

/**
 * Created by guxuede on 2016/5/31 .
 */
public class EffectsEntity extends AnimationEntity {
    public EffectsEntity(ActorAnimationPlayer animationPlayer, World world, InputListener l) {
        super(animationPlayer, world, l);
    }

    public EffectsEntity(ActorAnimationPlayer animationPlayer, World world) {
        super(animationPlayer, world);
    }

    public void createBody(World world){
        if(lifeStatus == LIFE_STATUS_CREATE){
            lifeStatus = LIFE_STATUS_BORN;
            int actorWidth= animationPlayer.width;
            this.setVisible(true);
            /**********************************box2d************************************************/
            BodyDef  bd = new  BodyDef ();
            bd.type= BodyDef.BodyType.DynamicBody;
            bd.position.set(getEntityX(),getEntityY());

            CircleShape c=new CircleShape();
            c.setRadius(actorWidth/3);
            FixtureDef ballShapeDef = new FixtureDef();
            ballShapeDef.density = 1.0f;
            ballShapeDef.friction = 1f;
            ballShapeDef.restitution = 0.0f;
            ballShapeDef.shape = c;
            ballShapeDef.isSensor= true;
            body = world.createBody(bd);
            body.createFixture(ballShapeDef);
            body.setLinearDamping(100);
            body.setAngularDamping(100);
            body.setUserData(this);
            c.dispose();
        }
        if(lifeStatus == LIFE_STATUS_BORN){
            lifeStatus = LIFE_STATUS_LIVE;
        }
    }
}
