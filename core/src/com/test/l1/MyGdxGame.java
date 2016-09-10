//package com.test.game;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
//import com.badlogic.gdx.physics.box2d.World;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Button;
//import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
//import com.badlogic.gdx.scenes.scene2d.ui.List;
//import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
//import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.ui.TextField;
//import com.badlogic.gdx.scenes.scene2d.ui.Window;
//import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
//import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.Gdx;
//import com.guxuede.game.viewActor.ActorFactory;
//import com.guxuede.game.viewActor.AnimationActor;
//import com.guxuede.game.viewActor.DebugButton;
//import com.guxuede.game.libgdx.MovebleOrthographicCamera;
//import com.guxuede.game.libgdx.ResourceManager;
//import com.guxuede.game.libgdx.Skin;
//
//
//public class MyGdxGame implements ApplicationListener {
//	String[] listEntries = {"potato1", "potato2", "potato3", "potato4",
//			"potato5", "potato6", "potato7", "potato8", "potato2", "potato3", "potato4",
//			"potato5", "potato6", "potato7", "potato2", "potato3", "potato4",
//			"potato5", "potato6", "potato7", "potato6", "potato7", "potato6", "potato7", "potato6", "potato7"};
//
//	ArrayList<String> listString=new ArrayList<String>(Arrays.asList(listEntries));
//
//	Texture texture;
//	SpriteBatch batch;
//
//	OrthographicCamera camera;
//	private Stage stage;
//	World world ;
//	Box2DDebugRenderer debugRenderer ;
//
//	@Override
//	public void create () {
//		texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
//		Skin skin=new Skin(Gdx.files.internal("uiskin.json"));
//		batch = new SpriteBatch();
//		camera = new MovebleOrthographicCamera();
//		stage=new Stage(new ScreenViewport(camera));
//
//		stage.addActor(createUi(skin));
//
//		Dialog d= new Dialog("---", skin);
//		 TextButtonStyle bs=new TextButtonStyle(ResourceManager.up,ResourceManager.down,null,ResourceManager.font);
//		 DebugButton button=new DebugButton("ABCD你好确定",bs);
//		 button.setPosition(100f, 100f);
//		 d.addActor(button);
//		  world = new World(new Vector2(0, 0), true);
//		  debugRenderer = new Box2DDebugRenderer();
//		 TextureAtlas atlas=new TextureAtlas(Gdx.files.internal("pack"));
//		 d.addActor(ActorFactory.createAnimationActor(atlas,"Dancer",world));
//		 d.addActor(ActorFactory.createAnimationActor(atlas,"Mage",world));
//		stage.addActor(d);
//
//		Gdx.input.setInputProcessor(stage);
//	}
//
//	@Override
//	public void resize (int width, int height) {
//
//	}
//
//	@Override
//	public void render () {
//    	Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		camera.update();
//		stage.act(Gdx.graphics.getDeltaTime());
//		stage.draw();
//		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
//		debugRenderer.render(world, camera.combined);
//		//Table.drawDebug(stage);
//	}
//
//	@Override
//	public void pause () {
//	}
//
//	@Override
//	public void resume () {
//	}
//
//	@Override
//	public void dispose () {
//	}
//
//
//	private Table createUi(Skin skin){
//		Table ui=new Table(skin);
//		ui.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//		/**澶撮儴鍖哄煙  瀹氫箟涓�簺鑿滃崟**/
//		Table topBar=new Table(skin);topBar.debug();
//		topBar.add(new Label("Menu1", skin)).expand();topBar.add(new Label("Menu2", skin)).expand();
//		topBar.add(new Label("Menu3", skin)).expand();//topBar.add(new DebugButton("guxu", ResourceManager.bs)).expand();
//
//		/**涓棿閮ㄥ垎 **/
//		Table midBar=new Table(skin);midBar.debug();
//			//宸︿晶鏄亰澶╃獥鍙�
//			Table midLeft=new Table(skin);midLeft.debug();
//			final List list = new List(new ListStyle(skin.getFont("default-font"),Color.WHITE,Color.WHITE,new BaseDrawable()));
//			list.setItems(listEntries);
//			final ScrollPane msgPane = new ScrollPane(list);
//			list.clearListeners();
//			midLeft.add(msgPane).top().left();
//			midLeft.row();
//			final TextField msgInput=new TextField("input\r\nsdfsdf", skin);
//				msgInput.addListener(new InputListener(){
//					@Override
//					public boolean keyUp(InputEvent event, int keycode) {
//						if(keycode==66){
//							String msg=msgInput.getText();
//							if(msg.length() > 0){
//								listString.add(msg);
//								list.setItems(listString.toArray());
//								msgPane.setScrollPercentY(100);
//								msgInput.setText("");
//								return true;
//							}
//						}
//						return super.keyDown(event, keycode);
//					}
//				});
//			midLeft.add(msgInput);
//			midBar.add(midLeft).expand().top().left();
//			//鍙充晶浠诲姟淇℃伅鏍忎綅
//			Table midRight=new Table(skin);midRight.debug();
//				midRight.add("mid right");
//			midBar.add(midRight);
//
//
//		/**bottom**/
//		Table bottomBar=new Table(skin);bottomBar.debug();
//		bottomBar.add("bottomBar");
//
//		ui.debug();
//		ui.add(topBar).expandX().fillX().right();
//		ui.row();
//		ui.add(midBar).expand().fill();
//		ui.row();
//		ui.add(bottomBar);
//
//		return ui;
//	}
//
//	/**
//	 * 鎬荤粨:
//	 * Table 鏄竴涓〃甯冨眬
//	 * Table.add()鍙皢缁勪欢娣诲姞鍒板綋鍓嶈涓�
//	 * Table.row()鍙崲琛�(浠ュ悗鍐嶆坊鍔犵粍浠舵槸鍦ㄦ柊琛屼腑娣诲姞,鍗砊able鏃舵湁鐘舵�鍨嬬殑)
//	 * Cell.expand/X/Y 鍙皢姝ell鎾戝ぇ.涓�涓墍鏈塩ell鍏ㄩ儴搴旂敤杩欎釜鏂规硶鐨勮瘽,閭ｄ箞姣忎釜cell浼氬钩鍧囧垎閰嶈繖涓�(鍙兘骞朵笉鍏呮弧),浼氬緢濂界湅
//	 * Cell.right/top/bottom/left 濡傛灉姝ell澶熷ぇ(鍗抽噷闈㈢殑缁勪欢娌℃湁鍏呭垎浣跨敤璇ell),涓�埇缁忓父宸﹀彸浼氭湁闂蹭綑,鍙敤杩欎釜璁剧疆瀵归綈鏂瑰紡
//	 * Cell.fill 璁剧疆璇ell瀹藉害鍏呮弧鏀硅绌洪棿.鍥犱负涓�埇cell鐨勫ぇ灏忔槸璇ell鎵�鐨勭粍浠跺ぇ灏�
//	 *
//	 */
//}
