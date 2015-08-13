package com.test.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.actor.AnimationActorProjection;
import com.guxuede.game.actor.BarrageTip;
import com.guxuede.game.actor.ParticelActor;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.tools.ActionsUtils;

public class TitleMapStage extends Stage{
	
	
	private List<AnimationActor> willDeleteActors = new ArrayList<AnimationActor>();
	private List<AnimationActor> throwActors = new ArrayList<AnimationActor>();


	private OrthogonalTiledMapRenderer tileMapRenderer;
	protected Box2DDebugRenderer debugRenderer;
	private World world;
	
	private static final int[] LAYER1= new int[]{0}; 
	private static final int[] LAYER2= new int[]{1}; 
	private static final int[] LAYER3= new int[]{2}; 
	
	public AnimationActor actor;
    

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
		world = new World(new Vector2(0, 0), true);
		world.setContactFilter(new ContactFilter() {
			@Override
			public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
				AnimationActor actorA=(AnimationActor) fixtureA.getBody().getUserData();
				AnimationActor actorB=(AnimationActor) fixtureB.getBody().getUserData();
				if(actorA.sourceActor == actorB || actorB.sourceActor == actorA){
					return false;
				}
				return true;
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
				AnimationActor actorA=(AnimationActor) contact.getFixtureA().getBody().getUserData();
				AnimationActor actorB=(AnimationActor) contact.getFixtureB().getBody().getUserData();
				if(actorA.sourceActor == actorB || actorB.sourceActor == actorA){
					
				}else{
					WorldManifold worldManifold=contact.getWorldManifold();
					Vector2 vector2 = worldManifold.getPoints()[0];
					addActor(new BarrageTip("hello",vector2.x,vector2.y));
					actorA.dispose();
				}
			}
		});
		
		debugRenderer = new Box2DDebugRenderer(); 
		
		TiledMap map = new TmxMapLoader().load("desert1.tmx");
        tileMapRenderer = new OrthogonalTiledMapRenderer(map,this.getBatch()); 
        
		
        InputListener focusListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
				actor = (AnimationActor) event.getListenerActor();
				System.out.println(Thread.currentThread().getName());
				return super.touchDown(event, x, y, pointer, button);
			}
		} ;
		actor = new AnimationActor(ResourceManager.atlas,"Undead",world,focusListener);
		actor.setPosition(100, 100);
		addActor(actor);
		//addActor(new AnimationActor("bahamut.png",world,focusListener));
		//actor =new AnimationActorProjection("Image 164.png",world,focusListener);
		//addActor(actor);
		addActor(new ParticelActor());
		addActor(new BarrageTip("hello",100,100));
		//actor.body.setTransform(100, 100, 0);
	}
	
/**
 * 　float timeStep = 1.0f / 60.0f;//刷新时间粒度
int velocityIterations = 6;//速度计算层级
int positionIterations = 2;//位置计算层级
 */
	@Override
	public void act(float delta) {
		world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2); 
		if(!world.isLocked()){
			for(AnimationActor actor:willDeleteActors){
				world.destroyBody(actor.body);
				actor.remove();
			}
			willDeleteActors.clear();
			for(AnimationActor actor:throwActors){
				float degrees = actor.degrees;
				float l = 200;
				double radians = (float) (2*Math.PI * degrees / 360);
				float dx=(float) (actor.getX()+l*Math.cos(radians));
				float dy=(float) (actor.getY()+l*Math.sin(radians));
				AnimationActorProjection projection = new AnimationActorProjection(ResourceManager.bult,world);
				projection.sourceActor = actor;
				projection.body.setTransform(actor.body.getPosition(),0);
				projection.setRotation((float) radians);
				projection.addAction(
						ActionsUtils.sequence(ActionsUtils.scaleBy(5, 5,0.1f),
								ActionsUtils.scaleBy(-5, -5,0.1f),
								ActionsUtils.jumpAction(dx, dy),
								ActionsUtils.actorDeathAnimation(0,0.2f)));
				this.addActor(projection);
			}
			throwActors.clear();
		}
		
		super.act(delta);
	}

	
	@Override
	public void draw() {
		tileMapRenderer.setView((OrthographicCamera) getCamera());
		tileMapRenderer.render(LAYER1);
		tileMapRenderer.render(LAYER2);
		drawActors();
		super.draw();
		tileMapRenderer.render(LAYER3);
		debugRenderer.render(world, getCamera().combined); 
		if(actor!=null){
			getCamera().position.x=actor.getX();
			getCamera().position.y=actor.getY();
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
	}

	public void deleteActor(AnimationActor actor){
		willDeleteActors.add(actor);
	}
	
	public void throwProjection(AnimationActor actor){
		throwActors.add(actor);
	}
//	public void throwProjection(){
//		throwProjection(this.degrees);
//	}
//	public void throwProjection(float degrees){
//		float l = 400;
//		double radians = (float) (2*Math.PI * degrees / 360);
//		float dx=(float) (this.getX()+l*Math.cos(radians));
//		float dy=(float) (this.getY()+l*Math.sin(radians));
//		throwProjection(dx,dy);
//	}
//	public void throwProjection(float x,float y){
//		AnimationActorProjection projection = new AnimationActorProjection("Image 164.png",body.getWorld(),null);
//		projection.sourceActor = this;
//		projection.body.setTransform(this.body.getPosition(),0);
//		projection.addAction(ActionsUtils.jumpAction(x, y));
//		this.getStage().addActor(projection);
//	}
}
