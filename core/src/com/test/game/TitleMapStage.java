package com.test.game;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guxuede.game.actor.*;
import com.guxuede.game.animation.ActorChangeAppearanceAction;
import com.guxuede.game.animation.ActorFormulaTracksAction;
import com.guxuede.game.effects.AnimationEffect;
import com.guxuede.game.effects.DoubleImageEffect;
import com.guxuede.game.libgdx.GdxEffect;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.libgdx.maps.titled.MyOrthogonalTiledMapRenderer;
import com.guxuede.game.animation.ActionsFactory;

public class TitleMapStage extends Stage{
	
	
	private MyOrthogonalTiledMapRenderer tileMapRenderer;
	protected Box2DDebugRenderer debugRenderer;
	private World world;

	
	public AnimationEntity actor;
    Sound sound;


	public TitleMapStage() {
		super();
	}

	public TitleMapStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}

	public TitleMapStage(Viewport viewport) {
		super(viewport);
	}

	public void init(){
        this.setDebugAll(true);
        sound = Gdx.audio.newSound(Gdx.files.internal("164-Skill08.ogg"));
		world = new World(new Vector2(0, 0), true);
		world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                AnimationEntity actorA = (AnimationEntity) fixtureA.getBody().getUserData();
                AnimationEntity actorB = (AnimationEntity) fixtureB.getBody().getUserData();
                //0.如果其中一个是单位子弹，另一个是null碰撞
                //1.其中一个是子弹，一个是单位，且子弹不是单位射出的。 碰撞
                //2。两个都是子弹，且来自不同的单位。碰撞
                if (actorA != null && actorB == null) {
                    System.err.println("1");
                    return true;
                } else if (actorB != null && actorA == null) {
                    System.err.println("2");
                    return true;
                } else if (actorA instanceof AnimationProjection && actorB instanceof AnimationActor && actorA.sourceActor != actorB) {
                    System.err.println("3");
                    return true;
                } else if (actorB instanceof AnimationProjection && actorA instanceof AnimationActor && actorB.sourceActor != actorA) {
                    System.err.println("4");
                    return true;
                }
                return false;
            }
        });
		world.setContactListener(new ContactListener() {
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

            @Override
            public void endContact(Contact contact) {
                System.err.println("end");
            }

            //Contact.getFixtureA获得创建时间比较早的对象,Contact.getFixtureB获得创建时间晚的对象
            @Override
            public void beginContact(Contact contact) {
                System.err.println("start");
                AnimationEntity actorA = (AnimationEntity) contact.getFixtureA().getBody().getUserData();
                AnimationEntity actorB = (AnimationEntity) contact.getFixtureB().getBody().getUserData();
                WorldManifold worldManifold = contact.getWorldManifold();
                Vector2 vector2 = worldManifold.getPoints()[0];

                if (actorA != null && actorB == null) {
                    if (actorA instanceof AnimationProjection) {
                        doShowDamageEffect(vector2, null);
                        actorA.dispose();
                    }
                } else if (actorB != null && actorA == null) {
                    if (actorB instanceof AnimationProjection) {
                        doShowDamageEffect(vector2, null);
                        actorB.dispose();
                    }
                } else if (actorA instanceof AnimationProjection && actorB instanceof AnimationActor && actorA.sourceActor != actorB) {
                    doShowDamageEffect(vector2, actorB);
                    actorA.dispose();

                } else if (actorB instanceof AnimationProjection && actorA instanceof AnimationActor && actorB.sourceActor != actorA) {
                    doShowDamageEffect(vector2, actorA);
                    actorB.dispose();
                    sound.play();
                }

            }

            public void doShowDamageEffect(Vector2 vector2, Actor actor) {
                BarrageTip tip = new BarrageTip("-1", vector2.x, vector2.y);
                tip.setZIndex(Integer.MAX_VALUE);
                addActor(tip);

                if (actor != null) {
                    actor.addAction(
                            ActionsFactory.sequence(
                                    ActionsFactory.color(Color.RED, 0.1f),
                                    ActionsFactory.color(Color.PINK, 0.1f),
                                    ActionsFactory.color(new Color(1, 1, 1, 1), 0.1f)
                            )
                    );
                    AnimationEffect effect = new AnimationEffect();
                    effect.setEffectAnimation(ResourceManager.getAnimationHolder("special10").getStopDownAnimation());
                    actor.addAction(effect);
                }
            }
        });
		
		debugRenderer = new Box2DDebugRenderer(); 
		
		TiledMap map = new TmxMapLoader().load("desert1.tmx");
		
        tileMapRenderer = new MyOrthogonalTiledMapRenderer(map,this.getBatch()); 
        
		
        InputListener focusListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
				actor = (AnimationEntity) event.getListenerActor();
				System.out.println("Switch actor.");
				return super.touchDown(event, x, y, pointer, button);
			}
		} ;
        AnimationActor actor = ActorFactory.createActor("Undead",world,focusListener);
		actor.setPosition(200, 100);
        DoubleImageEffect doubleImageEffect = new DoubleImageEffect();
        doubleImageEffect.setDuration(5);
        actor.addAction(doubleImageEffect);
        addActor(actor);

        AnimationActor actor1 = ActorFactory.createActor("Undead", world, focusListener);
        actor1.setPosition(100, 150);
        ActorChangeAppearanceAction actorChangeAppearanceAction = new ActorChangeAppearanceAction();
        actorChangeAppearanceAction.setDuration(5f);
        actor1.addAction(actorChangeAppearanceAction);
        ActorFormulaTracksAction actorFormulaTracksAction = new ActorFormulaTracksAction();
        actorFormulaTracksAction.setDuration(10f);
        actor1.addAction(ActionsFactory.sequence(ActionsFactory.parallel(actorFormulaTracksAction, ActionsFactory.scaleBy(0.5f, 0.5f, 10f)), ActionsFactory.scaleBy(-0.5f, -0.5f)));
        actor1.setColor(Color.BLUE);
        AnimationEffect effect = new AnimationEffect();
        effect.setDuration(10);
        effect.setEffectAnimation(ResourceManager.getAnimationHolder("wind2").getStopDownAnimation());
        actor1.addAction(effect);
        addActor(actor1);

		addActor(new ParticelActor());
		addActor(new BarrageTip("hello",100,100));
        //addActor(ActorFactory.createActor("light4", world, focusListener));
        addActor(ActorFactory.createActor("thunder1", world, focusListener));
        //addActor(ActorFactory.createEffectsActor("special10", world, focusListener));
        addActor(ActorFactory.createEffectsActor("yaoshou1",world,focusListener));

		//actor.body.setTransform(100, 100, 0);
		//createABoack(map);
	}
	
/**
 * 　float timeStep = 1.0f / 60.0f;//刷新时间粒度
int velocityIterations = 6;//速度计算层级
int positionIterations = 2;//位置计算层级
 */
	@Override
	public void act(float delta) {
		world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2); 
		for(Actor actor:getActors()){
			if(actor instanceof AnimationEntity){
				AnimationEntity a = ((AnimationEntity) actor);
				a.createBody(world);
				a.destroyBody(world);
			}
		}
		super.act(delta);
	}

	
	@Override
	public void draw() {
		tileMapRenderer.setView((OrthographicCamera) getCamera());
		tileMapRenderer.renderLayer1();
		tileMapRenderer.renderLayer2();
        drawActors();
		tileMapRenderer.renderLayer3();
		debugRenderer.render(world, getCamera().combined);
		if(actor!=null){
			getCamera().position.x=actor.getEntityX();
			getCamera().position.y=actor.getEntityY();
		}
	}
	
	protected void drawActors(){
		getActors().sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                //(o1.getZIndex() < o2.getZIndex())?1:
                return Float.compare(o2.getY(), o1.getY());
                //return (int) (o2.getY()-o1.getY());
            }
        });
        setActorDrawLevel(LevelDrawActor.DRAW_LEVEL_FOOT);
        super.draw();
        setActorDrawLevel(LevelDrawActor.DRAW_LEVEL_BODY);
        super.draw();
        setActorDrawLevel(LevelDrawActor.DRAW_LEVEL_HEAD);
        super.draw();
	}

    private void setActorDrawLevel(int drawLevel){
        for(Actor actor : getActors()){
            if(actor instanceof LevelDrawActor){
                ((LevelDrawActor)actor).drawLevel = drawLevel;
            }
        }
    }
	
	protected void createBlockWall(TiledMap map) {
		com.badlogic.gdx.maps.MapLayer layer = map.getLayers().get(1);
		if (layer.isVisible()) {
			if (layer instanceof TiledMapTileLayer) {
				TiledMapTileLayer tiledMapTileLayer = ((TiledMapTileLayer)layer);
				for(int x = 0;x < tiledMapTileLayer.getHeight();x++){
					
				}
			} else if (layer instanceof TiledMapImageLayer) {
				
			} else {
				
			}
		}
		
	}
	
	private void createABoack(TiledMap map){
		 {
		BodyDef  bd;
			bd = new BodyDef();
			bd.type = BodyType.KinematicBody;
			bd.position.set(100, 200);
			CircleShape c = new CircleShape();
			c.setRadius(200 / 3);
			FixtureDef ballShapeDef = new FixtureDef();
			ballShapeDef.density = 1.0f;//密度
			ballShapeDef.friction = 1f;////摩擦粗糙程度
			ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
			ballShapeDef.shape = c;//形状
			//ballShapeDef.isSensor=
			Body body = world.createBody(bd);
			body.createFixture(ballShapeDef);
			//body.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
			body.setLinearDamping(100);//阻尼
			body.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
			body.setUserData(null);
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
					BodyDef  bd = new BodyDef();
					bd.type=BodyType.KinematicBody; 
					bd.position.set(x1+16,y1+16); 
					
					PolygonShape c=new PolygonShape(); 
					c.setAsBox(16, 16);
					FixtureDef ballShapeDef = new FixtureDef();
					ballShapeDef.density = 1.0f;//密度
					ballShapeDef.friction = 1f;////摩擦粗糙程度
					ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
					ballShapeDef.shape = c;//形状
					//ballShapeDef.isSensor=
					Body body = world.createBody(bd);
					body.createFixture(ballShapeDef);
					//body.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
					body.setLinearDamping(100);//阻尼
					body.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
					body.setUserData(null);
					c.dispose();
					
					
				}
				x += layerTileWidth;
			}
			y -= layerTileHeight;
		}
	}
}
