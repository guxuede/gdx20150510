package com.guxuede.game.position.box2d;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.AnimationProjection;
import com.guxuede.game.position.PositionPlayer;
import com.guxuede.game.position.PositionWorld;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class Box2dPositionWorld implements PositionWorld {
    public World world;

    public Box2dPositionWorld() {
        this.world = new World(new Vector2(0, 0), true);
        world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                AnimationEntity actorA = (AnimationEntity) fixtureA.getBody().getUserData();
                AnimationEntity actorB = (AnimationEntity) fixtureB.getBody().getUserData();
                return Box2dPositionWorld.this.shouldCollide(actorA,actorB);
            }
        });
        world.setContactListener(new ContactListener() {
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                System.err.println("preSolve");
//                AnimationEntity actorA = (AnimationEntity) contact.getFixtureA().getBody().getUserData();
//                AnimationEntity actorB = (AnimationEntity) contact.getFixtureB().getBody().getUserData();
//                actorB.stop();
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                System.err.println("postSolve");
            }

            @Override
            public void endContact(Contact contact) {
                System.err.println("endContact");
            }

            //Contact.getFixtureA获得创建时间比较早的对象,Contact.getFixtureB获得创建时间晚的对象
            @Override
            public void beginContact(Contact contact) {
                System.err.println("beginContact start");
                AnimationEntity actorA = (AnimationEntity) contact.getFixtureA().getBody().getUserData();
                AnimationEntity actorB = (AnimationEntity) contact.getFixtureB().getBody().getUserData();
                //Vector2 vector2 = contact.getWorldManifold().getNormal();//TODO ?得到的位置不正确
                //System.out.println("pppppppp:"+vector2.x+","+vector2.y);
                Box2dPositionWorld.this.collide(actorA,actorB);
            }
        });
    }

    /**
     * 　float timeStep = 1.0f / 60.0f;//刷新时间粒度
     int velocityIterations = 6;//速度计算层级
     int positionIterations = 2;//位置计算层级
     */
    public void act(float delta){
        world.step(delta, 3, 3);
    }

    @Override
    public boolean shouldCollide(AnimationEntity actorA, AnimationEntity actorB) {
        //0.如果其中一个是单位子弹，另一个是null碰撞
        //1.其中一个是子弹，一个是单位，且子弹不是单位射出的。 碰撞
        //2。两个都是子弹，且来自不同的单位。碰撞
        if (actorA != null && actorB == null) {
            System.err.println("shouldCollide 1");
            return true;
        } else if (actorB != null && actorA == null) {
            System.err.println("shouldCollide 2");
            return true;
        } else if (actorA instanceof AnimationProjection && actorB instanceof AnimationActor && actorA.sourceActor != actorB) {
            System.err.println("shouldCollide 3");
            return true;
        } else if (actorB instanceof AnimationProjection && actorA instanceof AnimationActor && actorB.sourceActor != actorA) {
            System.err.println("shouldCollide 4");
            return true;
        }
        return false;
    }

    @Override
    public void collide(AnimationEntity actorA, AnimationEntity actorB) {
        Vector2 vector2 = TempObjects.temp0Vector2;
        if (actorA != null && actorB == null) {
            if (actorA instanceof AnimationProjection) {
                vector2.set(actorA.getCenterX(),actorA.getCenterY());
                ((AnimationProjection) actorA).hit(actorB, vector2);
            }
        } else if (actorB != null && actorA == null) {
            if (actorB instanceof AnimationProjection) {
                vector2.set(actorB.getCenterX(),actorB.getCenterY());
                ((AnimationProjection) actorB).hit(actorB, vector2);
            }
        } else if (actorA instanceof AnimationProjection && actorB instanceof AnimationActor && actorA.sourceActor != actorB) {
            vector2.set(actorB.getCenterX(),actorB.getCenterY());
            ((AnimationProjection) actorA).hit(actorB, vector2);
        } else if (actorB instanceof AnimationProjection && actorA instanceof AnimationActor && actorB.sourceActor != actorA) {
            vector2.set(actorA.getCenterX(),actorA.getCenterY());
            ((AnimationProjection) actorB).hit(actorA,vector2);
        }
    }

    @Override
    public PositionPlayer createPositionPlayer() {
        return new Box2dPositionPlayer(this);
    }
}
