package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GdxSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.guxuede.game.DefaultWorld;
import com.guxuede.game.StageWorld;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.ActorChangeAppearanceAction;
import com.guxuede.game.action.ActorFormulaTracksAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.action.effects.DoubleImageEffect;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.LevelDrawActor;
import com.guxuede.game.actor.ability.buffer.PoisonBuffer;
import com.guxuede.game.libgdx.*;
import com.guxuede.game.light.DefaultLightManager;
import com.guxuede.game.light.LightManager;
import com.guxuede.game.map.MapManager;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.tools.ActorBloodRender;
import com.guxuede.game.tools.TempObjects;

import java.util.Comparator;

public class TitleMapStage extends Stage implements GdxScreen {


	private MyOrthogonalTiledMapRenderer tileMapRenderer;
    public StageWorld world;
	public AnimationEntity viewActor;
    private LightManager lightManager;
    private ActorBloodRender actorBloodRender;
    public String stageName;

	public TitleMapStage(String stageName, GdxGame gdxGame) {
        super(
                new FitViewport(Gdx.graphics.getWidth(),
                                Gdx.graphics.getHeight(),
                                new MovebleOrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
                )
                , new SpriteBatch());
        this.setDebugAll(true);
        this.stageName = stageName;
        this.setDebugAll(StageWorld.isDebug);
		this.world = new DefaultWorld(this,gdxGame);
		TiledMap map = new GdxTmxMapLoader().load(stageName);//"desert.tmx"
        this.tileMapRenderer = new MyOrthogonalTiledMapRenderer(map,this.getBatch());
        this.lightManager = new DefaultLightManager(world);
        this.lightManager.onMapLoad(map);
        this.world.getPhysicsManager().onMapLoad(map);
        new MapManager(world).onMapLoad(map);
        this.actorBloodRender = new ActorBloodRender();
        InputListenerMultiplexer inputListenerMultiplexer = new InputListenerMultiplexer();
        inputListenerMultiplexer.addListener(world.getMouseManager());
        inputListenerMultiplexer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor actor = event.getTarget();
                if(actor !=null && actor instanceof  AnimationActor && actor != viewActor && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                    viewActor = (AnimationEntity) actor;
                    return true;
                }
                return false;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Actor actor = event.getTarget();
                if(actor !=null && actor instanceof  AnimationActor && actor != viewActor){
                   ((AnimationActor) actor).isHover = true;

                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Actor actor = event.getTarget();
                if(actor !=null && actor instanceof  AnimationActor){
                    ((AnimationActor) actor).isHover = false;
                }
            }
        });
        inputListenerMultiplexer.addListener(new InputListener() {
            @Override
            public boolean handle(Event e) {
                if(viewActor != null && e instanceof InputEvent){
                    return viewActor.handleInput((InputEvent) e);
                }
                return super.handle(e);
            }
        });
        this.addListener(inputListenerMultiplexer);
        AnimationActor actor = ActorFactory.createActor("Undead",world);
		actor.setCenterPosition(200, 400);
        DoubleImageEffect doubleImageEffect = new DoubleImageEffect();
        doubleImageEffect.setDuration(50);
        actor.addAction(doubleImageEffect);
        actor.addAction(new AnimationEffect("3aaa",100));
        PoisonBuffer poisonBuffer =  new PoisonBuffer();
        poisonBuffer.setDuration(13);
        actor.addAction(poisonBuffer);
        actor.addAction(new AnimationEffect("poison1",13));
        addActor(actor);

        AnimationActor actor1 = ActorFactory.createActor("Aquatic", world);
        actor1.setCenterPosition(100, 150);
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


        //ActorFactory.createRandomActor(world,this,focusListener);
        //ActorFactory.createRandomDoor(world,this);
    }

    private Texture bg = ResourceManager.getTexture("World");

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
                if(o1 instanceof  LevelDrawActor && !(o2 instanceof  LevelDrawActor)){
                    return -1;
                }if(!(o1 instanceof  LevelDrawActor) && (o2 instanceof  LevelDrawActor)){
                    return 1;
                }else if(!(o1 instanceof  LevelDrawActor) && !(o2 instanceof  LevelDrawActor)){
                    return Float.compare(o1.getZIndex(), o2.getZIndex());
                }else{
                    int z = ((LevelDrawActor) o1).drawZIndex - ((LevelDrawActor) o2).drawZIndex;
                    if(z == 0){
                        return Float.compare(o2.getY(), o1.getY());
                    }else{
                        return z;
                    }
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
            //entity.setVisible(true); //TODO 初始化时是否要隐藏掉单位？
        }else if(entity.lifeStatus == AnimationEntity.LIFE_STATUS_BORN){
            entity.lifeStatus = AnimationEntity.LIFE_STATUS_LIVE;
        }else if(entity.lifeStatus == AnimationEntity.LIFE_STATUS_DESTROY){
            entity.getPhysicsPlayer().destroy(entity);
            entity.remove();//TODO 也许不应该在这里remove掉
        }
    }

    @Override
    public void show() {
        this.world.show();
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

    @Deprecated
    @Override
    public void draw() {
        throw new RuntimeException("Please call render");
    }

    public static final float MapWidth = 1000;
    public static final float MapHeight = 1000;

    @Override
    public void render(float delta) {
        this.act(delta);
        if(world.isVisible()){
            OrthographicCamera camera = (OrthographicCamera) getCamera();
            camera.update();
            getBatch().begin();
            getBatch().draw(bg, (float) (-MapWidth/2+camera.position.x*0.2), (float) (-MapHeight/2+camera.position.y*0.2),MapWidth,MapHeight);
            getBatch().end();
            tileMapRenderer.setView(camera);
            tileMapRenderer.renderLayer1();
            tileMapRenderer.renderLayer2();
            drawThisStage(camera);
            tileMapRenderer.renderLayer3();
            //tileMapRenderer.render(new int[]{3});
            world.getPhysicsManager().render();
            lightManager.render();
            actorBloodRender.render(getActors(),getBatch());
            world.getMouseManager().render(getBatch(),delta);
            if(viewActor !=null){
                camera.position.x= viewActor.getCenterX();
                camera.position.y= viewActor.getCenterY();
            }

        }
        if(StageWorld.isDebug){
            super.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        lightManager.resize(width,height);
        OrthographicCamera camera = (OrthographicCamera) getCamera();
        TempObjects.temp0Vector3.set(camera.position);
        //getViewport().setScreenSize(width,height);
        getViewport().setWorldSize(width,height);
        getViewport().update(width,height);
        camera.setToOrtho(false, width, height);
        camera.position.set(TempObjects.temp0Vector3);
        camera.update();
        getBatch().setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
        this.world.pause();
    }

    @Override
    public void resume() {
        this.world.show();
        this.world.start();
    }

    @Override
    public void hide() {
        this.world.hide();
    }
}
