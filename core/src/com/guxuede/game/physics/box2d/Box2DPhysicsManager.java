package com.guxuede.game.physics.box2d;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.guxuede.game.StageWorld;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationDoor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.AnimationProjection;
import com.guxuede.game.physics.PhysicsPlayer;
import com.guxuede.game.physics.PhysicsManager;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class Box2DPhysicsManager implements PhysicsManager {
    protected World world;
    private Box2DDebugRenderer debugRenderer;
    private StageWorld stageWorld;

    public Box2DPhysicsManager(StageWorld stageWorld) {
        this.stageWorld = stageWorld;
        this.world = new World(new Vector2(0, 0), true);
        this.debugRenderer = new Box2DDebugRenderer();
        this.world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                AnimationEntity actorA = (AnimationEntity) fixtureA.getBody().getUserData();
                AnimationEntity actorB = (AnimationEntity) fixtureB.getBody().getUserData();
                if(actorA!=null && actorA.collisionSize== 0 || actorB!=null && actorB.collisionSize == 0){
                    return false;
                }
                return Box2DPhysicsManager.this.shouldCollide(actorA,actorB);
            }
        });
        this.world.setContactListener(new ContactListener() {
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                //System.err.println("preSolve");
//                AnimationEntity actorA = (AnimationEntity) contact.getFixtureA().getBody().getUserData();
//                AnimationEntity actorB = (AnimationEntity) contact.getFixtureB().getBody().getUserData();
//                actorB.stop();
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                //System.err.println("postSolve");
            }

            @Override
            public void endContact(Contact contact) {
                //System.err.println("endContact");
            }

            //Contact.getFixtureA获得创建时间比较早的对象,Contact.getFixtureB获得创建时间晚的对象
            @Override
            public void beginContact(Contact contact) {
                //System.err.println("beginContact start");
                AnimationEntity actorA = (AnimationEntity) contact.getFixtureA().getBody().getUserData();
                AnimationEntity actorB = (AnimationEntity) contact.getFixtureB().getBody().getUserData();
                //Vector2 vector2 = contact.getWorldManifold().getNormal();//TODO ?得到的位置不正确
                //System.out.println("pppppppp:"+vector2.x+","+vector2.y);
                Box2DPhysicsManager.this.collide(actorA,actorB);
            }
        });
    }

    /**
     * 　float timeStep = 1.0f / 60.0f;//刷新时间粒度
     int velocityIterations = 6;//速度计算层级
     int positionIterations = 2;//位置计算层级
     */
    public void act(float delta){
        world.step(1/50f, 2, 2);
    }

    @Override
    public boolean shouldCollide(AnimationEntity actorA, AnimationEntity actorB) {
        //0.如果其中一个是单位子弹，另一个是null碰撞
        //1.其中一个是子弹，一个是单位，且子弹不是单位射出的。 碰撞
        //2。两个都是子弹，且来自不同的单位。碰撞
        if (actorA != null && actorB == null) {
            //System.err.println("shouldCollide 1");
            return true;
        } else if (actorB != null && actorA == null) {
            //System.err.println("shouldCollide 2");
            return true;
        } else if (actorA instanceof AnimationProjection && actorB instanceof AnimationActor && actorA.sourceActor != actorB) {
            //System.err.println("shouldCollide 3");
            return true;
        } else if (actorB instanceof AnimationProjection && actorA instanceof AnimationActor && actorB.sourceActor != actorA) {
            //System.err.println("shouldCollide 4");
            return true;
        }else if(actorA instanceof AnimationEntity && actorB instanceof AnimationDoor){
            return true;
        }else if(actorA instanceof AnimationDoor && actorB instanceof AnimationEntity){
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
        }else if(actorA instanceof AnimationActor && actorB instanceof AnimationDoor){
            ((AnimationDoor) actorB).doDoor((AnimationActor) actorA);
        }else if(actorA instanceof AnimationDoor && actorB instanceof AnimationActor){
            ((AnimationDoor) actorA).doDoor((AnimationActor) actorB);
        }
    }

    @Override
    public PhysicsPlayer createPositionPlayer() {
        return new Box2DPhysicsPlayer(this);
    }

    @Override
    public void onMapLoad(TiledMap map) {
        createABoack(map);
    }

    @Override
    public void render() {
        if(StageWorld.isDebug){
            debugRenderer.render(world, stageWorld.getCamera().combined);
        }
    }

    //
    private void createABoack(TiledMap map) {
        {
            BodyDef bd;
            bd = new BodyDef();
            bd.type = BodyDef.BodyType.KinematicBody;
            bd.position.set(100, 200);
            CircleShape c = new CircleShape();
            c.setRadius(200 / 3);
            FixtureDef ballShapeDef = new FixtureDef();
            ballShapeDef.density = 1.0f;//密度
            ballShapeDef.friction = 1f;////摩擦粗糙程度
            ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
            ballShapeDef.shape = c;//形状
            //ballShapeDef.isSensor=
            Body positionPlayer = world.createBody(bd);
            positionPlayer.createFixture(ballShapeDef);
            //physicsPlayer.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
            positionPlayer.setLinearDamping(100);//阻尼
            positionPlayer.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
            positionPlayer.setUserData(null);
            c.dispose();
        }
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        float unitScale = 1;
        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();

        final float layerTileWidth = layer.getTileWidth() * unitScale;
        final float layerTileHeight = layer.getTileHeight() * unitScale;

        final int col1 = 0;
        final int col2 = layerWidth;

        final int row1 = 0;
        final int row2 = layerHeight;

        float y = row2 * layerTileHeight;
        float xStart = col1 * layerTileWidth;

        for (int row = row2; row >= row1; row--) {
            float x = xStart;
            for (int col = col1; col < col2; col++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    x += layerTileWidth;
                    continue;
                }
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();

                    TextureRegion region = tile.getTextureRegion();

                    float x1 = x + tile.getOffsetX() * unitScale;
                    float y1 = y + tile.getOffsetY() * unitScale;
                    float x2 = x1 + region.getRegionWidth() * unitScale;
                    float y2 = y1 + region.getRegionHeight() * unitScale;

                    //batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);BodyDef  bd = new  BodyDef ();
                    BodyDef bd = new BodyDef();
                    bd.type = BodyDef.BodyType.KinematicBody;
                    bd.position.set(x1 + 16, y1 + 16);

                    PolygonShape c = new PolygonShape();
                    c.setAsBox(16, 16);
                    FixtureDef ballShapeDef = new FixtureDef();
                    ballShapeDef.density = 1.0f;//密度
                    ballShapeDef.friction = 1f;////摩擦粗糙程度
                    ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
                    ballShapeDef.shape = c;//形状
                    //ballShapeDef.isSensor=
                    Body positionPlayer = world.createBody(bd);
                    positionPlayer.createFixture(ballShapeDef);
                    //physicsPlayer.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
                    positionPlayer.setLinearDamping(100);//阻尼
                    positionPlayer.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
                    positionPlayer.setUserData(null);
                    c.dispose();


                }
                x += layerTileWidth;
            }
            y -= layerTileHeight;
        }
    }
}
