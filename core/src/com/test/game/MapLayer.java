package com.test.game;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.guxuede.game.actor.AnimationActor;
import com.guxuede.game.libgdx.MovebleOrthographicCamera;

public class MapLayer extends Group{
	

	private OrthographicCamera orthographicCamera;
	private OrthogonalTiledMapRenderer tileMapRenderer;
	protected Box2DDebugRenderer debugRenderer;
	private World world;
	
	private static final int[] LAYER1= new int[]{0}; 
	private static final int[] LAYER2= new int[]{1}; 
	private static final int[] LAYER3= new int[]{2}; 
	
	public AnimationActor actor;
	
	
	public MapLayer(){
		this.setDebug(true);
		orthographicCamera=new MovebleOrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float centerX = screenWidth / 2;
		float centerY = screenHeight / 2;

		orthographicCamera.position.set(0, 0, 0);
//		orthographicCamera.viewportWidth = screenWidth;
//		orthographicCamera.viewportHeight = screenHeight;
		//orthographicCamera.setToOrtho(false);
		//orthographicCamera.translate(0, 0);orthographicCamera.update();
		//orthographicCamera.rotate(60);
		orthographicCamera.update();
		
		this.setSize(screenWidth*4, screenHeight*4);
		
		initMap();
		
    	TextureAtlas atlas=new TextureAtlas(Gdx.files.internal("pack"));
    	actor=new AnimationActor(atlas,"Animal",world);
    	actor.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
				System.out.println("----");
				return super.touchDown(event, x, y, pointer, button);
			}
    		
		});
    	actor.setColor(0, 0, 1, 1);
		addActor(actor);
		addActor(new AnimationActor(atlas,"Dancer",world));
		addActor(new AnimationActor(atlas,"Mage",world));
	}

	@Override
	public void act(float delta) {
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3); 
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
/*		
		Camera camera = orthographicCamera;
		camera.update();

		if (!this.isVisible()) return;

		tileMapRenderer.setView(orthographicCamera);
		tileMapRenderer.render(LAYER1);
		tileMapRenderer.render(LAYER2);
		if (batch != null) {
			batch.end();
			batch.begin();
			batch.setProjectionMatrix(camera.combined);
			
			getChildren().sort(new Comparator<Actor>() {
				@Override
				public int compare(Actor o1, Actor o2) {
					return (int) (o2.getY()-o1.getY());
				}
			});
			super.draw(batch, parentAlpha);
			batch.end();
			batch.begin();
			batch.setProjectionMatrix(getStage().getCamera().combined);
			
			tileMapRenderer.render(LAYER3);
			debugRenderer.render(world, orthographicCamera.combined); 
		}
*/
		
		//orthographicCamera.position.x=actor.getX();
		//orthographicCamera.position.y=actor.getY();
		//orthographicCamera.translate(actor.getX()-orthographicCamera.position.x, actor.getY()-orthographicCamera.position.y);
		orthographicCamera.update();
		
		//tileMapRenderer.setView(orthographicCamera);
		//tileMapRenderer.render(LAYER1);
		//tileMapRenderer.render(LAYER2);
		{
			batch.end();
			batch.begin();
			batch.setProjectionMatrix(orthographicCamera.combined);
			getChildren().sort(new Comparator<Actor>() {
				@Override
				public int compare(Actor o1, Actor o2) {
					return (int) (o2.getY()-o1.getY());
				}
			});
			super.draw(batch, parentAlpha);
		}
		{
			//tileMapRenderer.render(LAYER3);
			//debugRenderer.render(world, orthographicCamera.combined); 
		}
		{
			batch.end();
			batch.begin();
			batch.setProjectionMatrix(getStage().getCamera().combined);
		}
		

	}
	
	public final static Vector3 tmp = new Vector3();
	public final static Vector2 tmp1 = new Vector2();

	/**
	 * hit 没有完善,估计是舞台,地图,照相机,演员的相对坐标没有弄清楚
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		
		//parentToLocalCoordinates(tmp1.set(x-orthographicCamera.position.x-Gdx.graphics.getWidth()/2, y-orthographicCamera.position.y-Gdx.graphics.getHeight()/2));
		//System.err.println(x+","+y+"-----"+tmp1);
		//return super.hit(x+orthographicCamera.position.x-Gdx.graphics.getWidth()/2, y+orthographicCamera.position.y-Gdx.graphics.getHeight()/2, touchable);
/*		orthographicCamera.unproject(tmp.set(x, y, 0));
		float yy=2*orthographicCamera.position.y-tmp.y-1;
		return super.hit(tmp.x , yy, touchable);*/
		
		return super.hit(x-640/2, y-480/2, touchable);
		//320 240
	}

	private void initMap(){
		debugRenderer = new Box2DDebugRenderer(); 
		world = new World(new Vector2(0, 0), true);
		
		TiledMap map = new TmxMapLoader().load("desert.tmx");
        tileMapRenderer = new OrthogonalTiledMapRenderer(map); 
        
//        if(map.layers.size() > 1){
//        	TiledMapTileLayer tt=map.layers.get(1);
//        	
//        	for(int h=0;h<tt.getHeight();h++){
//        		for(int w=0;w<tt.getWidth();w++){
//            		//System.out.println(map.getTileProperty(tt.tiles[h][w], "gid"));;
//        			if(atlas.getRegion((tt.tiles[h][w]))!=null){
//        						
//        				System.out.println();
//        		        BodyDef bd = new BodyDef();bd.type=BodyType.StaticBody; bd.position.set(w*32+16, (tt.getHeight()-h)*32-16); 
//        				PolygonShape c=new PolygonShape();c.setAsBox(32/2, 32/2);
//        				Body body = world.createBody(bd); //通过world创建一个物体 
//        				body.createFixture(c, 1f); //将形状和密度赋给物体 
//        			}
//            	}
//        	}
//        }
	}
}
