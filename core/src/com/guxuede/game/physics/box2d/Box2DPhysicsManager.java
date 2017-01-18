package com.guxuede.game.physics.box2d;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.guxuede.game.StageWorld;
import com.guxuede.game.actor.*;
import com.guxuede.game.map.MapManager;
import com.guxuede.game.physics.PhysicsPlayer;
import com.guxuede.game.physics.PhysicsManager;
import com.guxuede.game.tools.Astar;
import com.guxuede.game.tools.TempObjects;

import static com.guxuede.game.StageWorld.MAP_CELL_H;
import static com.guxuede.game.StageWorld.MAP_CELL_W;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class Box2DPhysicsManager implements PhysicsManager {
    public static final String collisiob_LAYERS = "对象层 1";
    protected World world;
    private Box2DDebugRenderer debugRenderer;
    private StageWorld stageWorld;

    private TiledMap map;
    private Astar astar;

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
        this.map = map;
        //createABoack(map);
        MapLayer collisionLayer = map.getLayers().get(collisiob_LAYERS);
        if(collisionLayer!=null){
            createLayer(collisionLayer.getObjects(), (short) 0,1,"");
        }

        final TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);//我们使用第二层做碰撞层
        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();
        astar = new Astar(layerWidth,layerHeight){
            @Override
            protected boolean isValid(int x, int y) {
                return pointIsClear(TempObjects.temp0Vector2.set(x*MAP_CELL_W,y*MAP_CELL_H));
            }
        };

    }

    @Override
    public void render() {
        if(StageWorld.isDebug){
            debugRenderer.render(world, stageWorld.getCamera().combined);
        }
    }

    Array<Shape2D> collisionObject = new Array<Shape2D>();;
    public static final float PPM=1;
    public void createLayer(MapObjects objects, short bits, float tileSize, String layerName){
        Body body;
        BodyDef bdef=new BodyDef();
        FixtureDef fdef=new FixtureDef();
        bdef.type= BodyDef.BodyType.StaticBody;
        fdef.friction=0.4f;
        fdef.filter.categoryBits=bits;
        fdef.filter.maskBits=-1;
        fdef.isSensor=false;

        for(MapObject object:objects){
            if(object instanceof RectangleMapObject){
                Rectangle rect=((RectangleMapObject) object).getRectangle();
                bdef.position.set(rect.x/PPM+rect.width/2/PPM,rect.y/PPM+rect.height/2);
                PolygonShape shape=((PolygonShape)new PolygonShape());
                shape.setAsBox(rect.width/2/PPM, rect.height/2);
                fdef.shape=shape;
                body=world.createBody(bdef);
                body.createFixture(fdef).setUserData(layerName);
                shape.dispose();
                collisionObject.add(new Rectangle(rect));
            }else if(object instanceof CircleMapObject){
                //get circle shape and set position:
                Circle circ=((CircleMapObject) object).getCircle();
                bdef.position.set(circ.x,circ.y);

                //set circle shape:
                CircleShape shape=new CircleShape();
                shape.setRadius(circ.radius);
                fdef.shape=shape;

                //create body with this polygon
                body=world.createBody(bdef);
                body.createFixture(fdef).setUserData(layerName);
                shape.dispose();
                throw new RuntimeException("current not support CircleMapObject");
            }else if(object instanceof PolygonMapObject){
                //get polygon shape and set position:
                Polygon poly=((PolygonMapObject) object).getPolygon();
                bdef.position.set(poly.getX()/PPM,poly.getY()/PPM);

                //set polygon shape:
                PolygonShape shape=new PolygonShape();
                float[] polyVertices=poly.getVertices();
                for(int i=0;i<polyVertices.length;i++) polyVertices[i]=polyVertices[i]/PPM; // scale it down
                shape.set(poly.getVertices()); fdef.shape=shape;

                //create body with this polygon
                body=world.createBody(bdef);
                body.createFixture(fdef).setUserData(layerName);
                shape.dispose();
                collisionObject.add(poly);
            }else if(object instanceof PolylineMapObject){
                //get polyline shape and set position:
                Polyline poly=((PolylineMapObject) object).getPolyline();
                bdef.position.set(poly.getX()/PPM,poly.getY()/PPM);

                //set polyline shape:
                ChainShape shape=new ChainShape();
                float[] polyVertices=poly.getVertices();
                for(int i=0;i<polyVertices.length;i++) polyVertices[i]=polyVertices[i]/PPM; // scale it down
                shape.createChain(poly.getVertices()); fdef.shape=shape;

                //create body with this polyline
                body=world.createBody(bdef);
                body.createFixture(fdef).setUserData(layerName);
                shape.dispose();
                collisionObject.add(poly);//Polyline not support contain
            }else if(object instanceof EllipseMapObject){
                //get ellipse shape and set position:
                Ellipse elli=((EllipseMapObject) object).getEllipse();
                bdef.position.set(elli.x/PPM+elli.width/2,elli.y/PPM+elli.height/2);
                //set ellipse shape:
                CircleShape shape=new CircleShape();
                shape.setRadius(elli.width/2/PPM);
                fdef.shape=shape;
                //create body with this ellipse
                body=world.createBody(bdef);
                body.createFixture(fdef).setUserData(layerName);
                shape.dispose();
                collisionObject.add(elli);
            }else if(object instanceof TiledMapTileMapObject){
                TiledMapTileMapObject tiledMapTileMapObject = (TiledMapTileMapObject) object;
                tiledMapTileMapObject.getTextureRegion();
                TiledActor tiledActor = new TiledActor();
                Sprite sprite = new Sprite(tiledMapTileMapObject.getTextureRegion());
                sprite.setPosition(tiledMapTileMapObject.getX(),tiledMapTileMapObject.getY());
                tiledActor.sprite = sprite;
                tiledActor.setPosition(tiledMapTileMapObject.getX(),tiledMapTileMapObject.getY());
                stageWorld.getStage().addActor(tiledActor);
            }
        }
    }

    @Override
    public IntArray getAstarPath(Vector2 start,Vector2 target) {
        IntArray path = astar.getPathClone((int)(start.x/MAP_CELL_W),(int)(start.y/MAP_CELL_H),(int)(target.x/MAP_CELL_W),(int)(target.y/MAP_CELL_H));
        return path;
    }

    @Override
    public boolean pointIsClear(Vector2 point) {
        TempObjects.temp1Vector2.set((int)(point.x/MAP_CELL_W) * MAP_CELL_W+MAP_CELL_W/2, (int)(point.y/MAP_CELL_H) * MAP_CELL_H+MAP_CELL_H/2);
        for(Shape2D block : collisionObject){
            if(block.contains(TempObjects.temp1Vector2)){
                return false;
            }
        }
        return true;
        //TiledMapTileLayer.Cell cell = layer.getCell((int)(point.x/MAP_CELL_W),(int)(point.y/MAP_CELL_H));
        //return cell==null || cell.getTile()==null;
    }
}
