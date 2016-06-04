package com.test.game;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.DebugButton;
import com.guxuede.game.libgdx.InputProcessorLine;
import com.guxuede.game.libgdx.MovebleOrthographicCamera;
import com.guxuede.game.libgdx.ResourceManager;

import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
public class TitleMapGame implements ApplicationListener{

	Context cx;Scriptable scope;
    private TitleMapStage mapStage; 
    private OrthographicCamera camera;
    
    private Stage uiStage;
    
    private SpriteBatch batch;
    ParticleEffect effect;

	@Override
	public void create() {
        cx = Context.enter();
        scope = cx.initStandardObjects();
		camera=new MovebleOrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mapStage = new TitleMapStage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera),
				new SpriteBatch());
		mapStage.init();

		 //添加一个button用来测试对比该
		 TextButtonStyle bs=new TextButtonStyle(ResourceManager.up,ResourceManager.down,null,ResourceManager.font);
		 DebugButton button=new DebugButton("ABCD你好确定",bs);
		 button.setPosition(100, 100);
		// button.addListener();
		 mapStage.addActor(button);
		 
		 uiStage = new Stage();


        TouchpadStyle touchpadStyle=new TouchpadStyle(ResourceManager.down,ResourceManager.BTNImmolationOn1 );
		 final Touchpad touchpad=new Touchpad(12, touchpadStyle);
		 touchpad.setPosition(24, 24);
		 touchpad.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor1) {
				//System.out.println(touchpad.getKnobPercentX()+","+touchpad.getKnobPercentY());
				if(touchpad.getKnobPercentX()==0 && touchpad.getKnobPercentY()==0){
					mapStage.actor.stop();
				}else if(Math.abs(touchpad.getKnobPercentX()) > Math.abs(touchpad.getKnobPercentY())){
					if(touchpad.getKnobPercentX()>0){
						System.out.println("left");
						mapStage.actor.moveLeft();
					}else{
						System.out.println("right");
						mapStage.actor.moveRight();
					}
				}else{
					if(touchpad.getKnobPercentY()>0){
						System.out.println("down");
						mapStage.actor.moveDown();
					}else{
						mapStage.actor.moveUp();
						System.out.println("up");
					}
				}
			}
		});
		 uiStage.addActor(touchpad);
		 
		 ImageButton imageButton = new ImageButton(ResourceManager.BTNImmolationOn, ResourceManager.BTNImmolationOn1);
		 imageButton.setSize(20f, 20f);;
		 imageButton.setPosition(100f, 10f);
		 uiStage.addActor(imageButton);
		 Gdx.input.setInputProcessor(new InputProcessorLine(uiStage,mapStage)); //InputMultiplexer
		 mapStage.addListener(new ClickListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//System.err.println(x+","+y);
				//mapStage.actor.addAction(ActionsFactory.jumpAction(x, y));
				//amapStage.actor.throwProjection(x, y);
				super.touchUp(event, x, y, pointer, button);
			}
			 @Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(Keys.SPACE == keycode){
					mapStage.actor.throwProjection();
				}else if(Keys.UP == keycode){
					mapStage.actor.moveUp();
				}else if(Keys.DOWN == keycode){
					mapStage.actor.moveDown();
				}else if(Keys.LEFT == keycode){
					mapStage.actor.moveLeft();
				}else if(Keys.RIGHT == keycode){
					mapStage.actor.moveRight();
				}
				return super.keyDown(event, keycode);
			}
			 @Override
			public boolean keyUp(InputEvent event, int keycode) {
				 AnimationEntity entity=mapStage.actor;
				if(entity.isMoving){
					if( Keys.UP == keycode && entity.direction == AnimationEntity.UP
					 || Keys.DOWN == keycode && entity.direction == AnimationEntity.DOWN
					 || Keys.LEFT == keycode && entity.direction == AnimationEntity.LEFT
					 || Keys.RIGHT == keycode && entity.direction == AnimationEntity.RIGHT){
						mapStage.actor.stop();
					}
				}
				return super.keyDown(event, keycode);
			}
		 });
		 //openDialog("script.html",null);
		 
		batch = new SpriteBatch();
		effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
        effect.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        effect.start();
        
		ScriptableObject.putProperty(scope, "out", System.out); 
		ScriptableObject.putProperty(scope, "mapStage", mapStage); 
		ScriptableObject.putProperty(scope, "camera", camera); 
		ScriptableObject.putProperty(scope, "effect", effect); 
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//((OrthographicCamera) uiStage.getCamera()).setToOrtho(false, width/2, height/2);
	}
	
	@Override
	public void render() {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapStage.act(); 
        mapStage.draw();
        
        uiStage.act();
        uiStage.draw();
        
		
        batch.begin();
		effect.draw(batch, Gdx.app.getGraphics().getDeltaTime());
        //System.out.println(Gdx.graphics.getFramesPerSecond());
		batch.end();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	
	public void openDialog(String name,Dialog dialog){
		if(dialog!=null){
			dialog.remove();
		}
		 Dialog d= new Dialog("Dialog", ResourceManager.skin);
		 d.setModal(false);
		 if(dialog!=null){
			 d.setBounds(dialog.getX(),dialog.getY(),dialog.getWidth(),dialog.getHeight());
		 }else{
			 d.setBounds(0, 0,400f,200f);
			 d.addAction(Actions.moveTo(0, Gdx.graphics.getHeight(),0.5f));
		 }
		 parseFile(name,d);
		 uiStage.addActor(d);
	 
	}
	
	public void parseFile(String file,Dialog d){
		try {
			Document doc=Jsoup.parse(Gdx.files.internal(file).read(), "utf-8","http://gdx.com");
			parseHeader(doc.head(),cx,scope);
			parseBody(d,doc.body());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void parseBody(Dialog d,Element eBody){
		VerticalGroup body=new VerticalGroup().align(Align.left).pad(10f);
		body.setFillParent(true);
		parseBody(d,body,eBody);
		d.getContentTable().addActor(body);
		 
	}
	
	public void parseBody(final Dialog d,Group body,Element eBody){
		List<Node> nodes=eBody.childNodes();
		for(Node n:nodes){
			if(n instanceof TextNode){
				String text = ((TextNode) n).text();
				if(!StringUtil.isBlank(text)){
					body.addActor(new Label(text,ResourceManager.skin));
				}
			}else if(n instanceof Element){
				Element e=(Element) n;
				if("p".equals(e.tag().getName())){
					HorizontalGroup p = new HorizontalGroup();
					body.addActor(p);
					parseBody(d,p,e);
				}else if("a".equals(e.tag().getName())){
					TextButton tb = new TextButton(e.text(),ResourceManager.skin);
					final String href = e.attr("href");
					if(!"".equals(href.trim())){
						tb.addListener(new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x,float y) {
								openDialog(href,d);
							}
						});
					}
					body.addActor(tb);
				}else if("button".equals(e.tag().getName())){
					Button b = new DebugButton(e.text(),ResourceManager.bs);
					final String onClick = e.attr("onclick");
					if(!"".equals(onClick.trim())){
						b.addListener(new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x,float y) {
								cx.evaluateString(scope, onClick,  null, 1, null);
							}
						});
					}
					body.addActor(b);
				}
			}
		}
	}
	public void parseHeader(Element head,Context cx,Scriptable scope) throws IOException{
		Elements es=head.select("script");
		 for (Iterator<Element> iter = es.iterator(); iter.hasNext();) {
			 Element scriptE=iter.next();
			 String src=scriptE.attr("src");
			 if(!"".equals(src.trim())){
				 cx.evaluateReader(scope, new FileReader(src), null, 1, null);
			 }
			 String txt=scriptE.html();
			 if(!"".equals(txt.trim())){
				 cx.evaluateString(scope, txt, null, 1, null);
			 }
		 }
	}
	
}
