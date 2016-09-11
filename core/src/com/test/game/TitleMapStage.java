package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.guxuede.game.DefaultWorld;
import com.guxuede.game.StageWorld;
import com.guxuede.game.actor.*;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.ActorChangeAppearanceAction;
import com.guxuede.game.action.ActorFormulaTracksAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.action.effects.DoubleImageEffect;
import com.guxuede.game.libgdx.MovebleOrthographicCamera;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.libgdx.MyOrthogonalTiledMapRenderer;
import com.guxuede.game.light.DefaultLightManager;
import com.guxuede.game.light.LightManager;
import com.guxuede.game.resource.AnimationHolder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TitleMapStage extends Stage {


	private MyOrthogonalTiledMapRenderer tileMapRenderer;
    public StageWorld world;
	public AnimationEntity viewActor;
    private LightManager lightManager;

    public String stageName;

	public TitleMapStage(String stageName) {
        super(
                new FitViewport(Gdx.graphics.getWidth(),
                                Gdx.graphics.getHeight(),
                                new MovebleOrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
                )
                , new SpriteBatch());
        init(stageName);
        this.stageName = stageName;
    }

	public void init(String mapName){
        this.setDebugAll(StageWorld.isDebug);
        //
		this.world = new DefaultWorld(this);
		TiledMap map = new TmxMapLoader().load(mapName);//"desert.tmx"
        this.tileMapRenderer = new MyOrthogonalTiledMapRenderer(map,this.getBatch());
        this.lightManager = new DefaultLightManager(world);
        this.lightManager.onMapLoad(map);
        this.world.getPhysicsManager().onMapLoad(map);
        this.addListener(new ClickListener() {
            @Override
            public boolean handle(Event e) {
                //System.out.println("TitleMapStage ClickListener:"+e);
                if (!(e instanceof InputEvent)) return false;
                InputEvent event = (InputEvent) e;
                if (viewActor != null) {
                    viewActor.handleInput(event);
                    return true;
                }
                return super.handle(e);
            }
        });
        InputListener focusListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
				viewActor = (AnimationEntity) event.getListenerActor();
				//System.out.println("Switch viewActor.");
				return super.touchDown(event, x, y, pointer, button);
			}
		} ;
        AnimationActor actor = ActorFactory.createActor("Undead",world,focusListener);
		actor.setPosition(200, 400);
        DoubleImageEffect doubleImageEffect = new DoubleImageEffect();
        doubleImageEffect.setDuration(50);
        actor.addAction(doubleImageEffect);
        actor.addAction(new AnimationEffect("3aaa",100));
        actor.addAction(new AnimationEffect("special10"));
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

        ActorFactory.createRandomActor(world,this,focusListener);
        ActorFactory.createRandomDoor(world,this);
    }

	@Override
	public void act(float delta) {
        if(world.isNotPause()){
            world.act(Gdx.graphics.getDeltaTime());
            for(Actor actor:getActors()){
                if(actor instanceof AnimationEntity){
                    AnimationEntity a = ((AnimationEntity) actor);
                    processActorLife(a);
                }
            }
            super.act(delta);
        }
	}

	
	@Override
	public void draw() {
        if(world.isVisible()){
            OrthographicCamera camera = (OrthographicCamera) getCamera();
            camera.update();
            tileMapRenderer.setView(camera);
            tileMapRenderer.renderLayer1();
            tileMapRenderer.renderLayer2();
            drawThisStage(camera);
            tileMapRenderer.renderLayer3();

            world.getPhysicsManager().render();
            lightManager.render();

            if(viewActor !=null){
                camera.position.x= viewActor.getCenterX();
                camera.position.y= viewActor.getCenterY();
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

    public void detachActor(AnimationEntity actor){
        actor.getPhysicsPlayer().destroy(actor);
        actor.remove();
        actor.setStageWorld(null);
    }
    public void attachActor(AnimationEntity actor){
        actor.setPhysicsPlayer(world.getPhysicsManager().createPositionPlayer());
        this.addActor(actor);
        actor.setStageWorld(world);
        actor.getPhysicsPlayer().init(actor);
    }

    private void processActorLife(AnimationEntity entity){
        if(entity.lifeStatus == AnimationEntity.LIFE_STATUS_CREATE){
            entity.lifeStatus = AnimationEntity.LIFE_STATUS_BORN;
            entity.getPhysicsPlayer().init(entity);
            entity.setVisible(true);
        }else if(entity.lifeStatus == AnimationEntity.LIFE_STATUS_BORN){
            entity.lifeStatus = AnimationEntity.LIFE_STATUS_LIVE;
        }else if(entity.lifeStatus == AnimationEntity.LIFE_STATUS_DESTORY){
            entity.getPhysicsPlayer().destroy(entity);
            entity.remove();//TODO 也许不应该在这里remove掉
        }
    }

}
