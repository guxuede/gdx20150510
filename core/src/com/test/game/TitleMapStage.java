package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guxuede.game.DefaultWorld;
import com.guxuede.game.GameWorld;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.LevelDrawActor;
import com.guxuede.game.animation.ActionsFactory;
import com.guxuede.game.animation.ActorChangeAppearanceAction;
import com.guxuede.game.animation.ActorFormulaTracksAction;
import com.guxuede.game.effects.AnimationEffect;
import com.guxuede.game.effects.DoubleImageEffect;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.libgdx.maps.titled.MyOrthogonalTiledMapRenderer;

import java.util.Comparator;

public class TitleMapStage extends Stage{

    private boolean isDebug = false;
	private MyOrthogonalTiledMapRenderer tileMapRenderer;
	private GameWorld world;
	public AnimationEntity actor;
    private LightManager lightManager;

	public TitleMapStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}

	public void init(){
        this.setDebugAll(isDebug);
		world = new DefaultWorld(this);

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
		actor.setPosition(200, 400);
        DoubleImageEffect doubleImageEffect = new DoubleImageEffect();
        doubleImageEffect.setDuration(50);
        actor.addAction(doubleImageEffect);
        actor.addAction(new AnimationEffect("3aaa",100));
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

		//addActor(new ParticelActor());
		//addActor(new BarrageTip("hello",100,100));
        addActor(ActorFactory.createActor("Bird", world, focusListener));
        addActor(ActorFactory.createActor("lvbu", world, focusListener));

        //addActor(ActorFactory.createActor("thunder1", world, focusListener));
        //addActor(ActorFactory.createEffectsActor("special10", world, focusListener));
       // addActor(ActorFactory.createActor("kulou1", world,focusListener));
//        AnimationProjection projection = ActorFactory.createProjectionActor("kulou1", world, focusListener);
//        addActor(projection);
		//actor.positionPlayer.setTransform(100, 100, 0);
//        LightningEntity lightningEntity = ActorFactory.createLightningEntity("lightningLine",world,focusListener);
//        lightningEntity.sourceActor = actor;
//        lightningEntity.targetAnimation = actor1;
//        addActor(lightningEntity);
		//createABoack(map);
        lightManager = new LightManager(this);
    }

boolean pause = false;
	@Override
	public void act(float delta) {
        if(pause){
            return;
        }
        world.act(Gdx.graphics.getDeltaTime());
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
        OrthographicCamera camera = (OrthographicCamera) getCamera();
        camera.update();
        tileMapRenderer.setView(camera);
        tileMapRenderer.renderLayer1();
        tileMapRenderer.renderLayer2();
        drawThisStage(camera);
        tileMapRenderer.renderLayer3();

        if(isDebug){
            //debugRenderer.render(world, getCamera().combined);
        }
        lightManager.render(camera);

		if(actor!=null){
            camera.position.x=actor.getCenterX();
            camera.position.y=actor.getCenterY();
		}else{
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                camera.position.y+=5;
                camera.update();
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                camera.position.y-=5;
                camera.update();
            }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                camera.position.x-=5;
                camera.update();
            }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                camera.position.x+=5;
                camera.update();
            }
        }
	}

    /**
     * 覆盖其父类的方法，因为我们需要画各种层级DRAW_LEVEL_FOOT，DRAW_LEVEL_BODY，DRAW_LEVEL_HEAD
     * @param camera
     */
    public void drawThisStage(Camera camera){
        if (getRoot().isVisible()){
            Batch batch = this.getBatch();
            if (batch != null) {
                sortActor();
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                setActorDrawLevel(LevelDrawActor.DRAW_LEVEL_FOOT);
                getRoot().draw(batch, 1);
                setActorDrawLevel(LevelDrawActor.DRAW_LEVEL_BODY);
                getRoot().draw(batch, 1);
                setActorDrawLevel(LevelDrawActor.DRAW_LEVEL_HEAD);
                getRoot().draw(batch, 1);
                batch.end();
            }
            //if (debug) drawDebug(); 不能debug看了 :(
        }

    }

    private void sortActor(){
        getActors().sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                if(o1.getZIndex() == o2.getZIndex()){
                    return Float.compare(o2.getY(), o1.getY());
                }else{
                    return Float.compare(o1.getZIndex(), o2.getZIndex());
                }
            }
        });
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
//
//	private void createABoack(TiledMap map){
//		 {
//		BodyDef  bd;
//			bd = new BodyDef();
//			bd.type = BodyType.KinematicBody;
//			bd.position.set(100, 200);
//			CircleShape c = new CircleShape();
//			c.setRadius(200 / 3);
//			FixtureDef ballShapeDef = new FixtureDef();
//			ballShapeDef.density = 1.0f;//密度
//			ballShapeDef.friction = 1f;////摩擦粗糙程度
//			ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
//			ballShapeDef.shape = c;//形状
//			//ballShapeDef.isSensor=
//			Body positionPlayer = world.createBody(bd);
//			positionPlayer.createFixture(ballShapeDef);
//			//positionPlayer.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
//			positionPlayer.setLinearDamping(100);//阻尼
//			positionPlayer.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
//			positionPlayer.setUserData(null);
//			c.dispose();
//		}
//		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
//		float unitScale = 1;
//		final int layerWidth = layer.getWidth();
//		final int layerHeight = layer.getHeight();
//
//		final float layerTileWidth = layer.getTileWidth() * unitScale;
//		final float layerTileHeight = layer.getTileHeight() * unitScale;
//
//		final int col1 = 0;
//		final int col2 = layerWidth;
//
//		final int row1 = 0;
//		final int row2 = layerHeight;
//
//		float y = row2 * layerTileHeight;
//		float xStart = col1 * layerTileWidth;
//
//		for (int row = row2; row >= row1; row--) {
//			float x = xStart;
//			for (int col = col1; col < col2; col++) {
//				final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
//				if (cell == null) {
//					x += layerTileWidth;
//					continue;
//				}
//				final TiledMapTile tile = cell.getTile();
//
//				if (tile != null) {
//					final boolean flipX = cell.getFlipHorizontally();
//					final boolean flipY = cell.getFlipVertically();
//					final int rotations = cell.getRotation();
//
//					TextureRegion region = tile.getTextureRegion();
//
//					float x1 = x + tile.getOffsetX() * unitScale;
//					float y1 = y + tile.getOffsetY() * unitScale;
//					float x2 = x1 + region.getRegionWidth() * unitScale;
//					float y2 = y1 + region.getRegionHeight() * unitScale;
//
//					//batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);BodyDef  bd = new  BodyDef ();
//					BodyDef  bd = new BodyDef();
//					bd.type=BodyType.KinematicBody;
//					bd.position.set(x1+16,y1+16);
//
//					PolygonShape c=new PolygonShape();
//					c.setAsBox(16, 16);
//					FixtureDef ballShapeDef = new FixtureDef();
//					ballShapeDef.density = 1.0f;//密度
//					ballShapeDef.friction = 1f;////摩擦粗糙程度
//					ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
//					ballShapeDef.shape = c;//形状
//					//ballShapeDef.isSensor=
//					Body positionPlayer = world.createBody(bd);
//					positionPlayer.createFixture(ballShapeDef);
//					//positionPlayer.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
//					positionPlayer.setLinearDamping(100);//阻尼
//					positionPlayer.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
//					positionPlayer.setUserData(null);
//					c.dispose();
//
//
//				}
//				x += layerTileWidth;
//			}
//			y -= layerTileHeight;
//		}
//	}
}
