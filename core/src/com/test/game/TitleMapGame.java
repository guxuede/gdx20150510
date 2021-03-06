package com.test.game;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guxuede.game.libgdx.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.DebugButton;
import com.guxuede.game.resource.ResourceManager;

import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class TitleMapGame implements ApplicationListener{

	Context cx;Scriptable scope;
//    private TitleMapStage mapStage;
    private OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    private Stage uiStage;

    private Batch batch;
    ParticleEffect effect;
    ProgressBar progressBar ;
    @Override
	public void create() {
//        cx = Context.enter();
//        scope = cx.initStandardObjects();
		camera=new MovebleOrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiStage = new Stage();
        batch = uiStage.getBatch();
        shapeRenderer = new ShapeRenderer();
        GdxSkin skin =new GdxSkin(Gdx.files.internal("skin/ui_big_pieces.json"));
        TextureAtlas enqiments =new TextureAtlas(Gdx.files.internal("enqiments.atlas"));

        Table window = new Table();
        window.setHeight(400);window.setWidth(400);//window.setKeepWithinStage(false);
        {
            Table skillHold = new Table();
//            skillHold.setDebug(true);
            skillHold.setClip(true);
            skillHold.setWidth(24);
            skillHold.setHeight(24);
            Button.ButtonStyle skillStyle = new Button.ButtonStyle(new TextureRegionDrawable(enqiments.findRegion("伶俐指环")), new MiniatureTextureRegionDrawable(enqiments.findRegion("伶俐指环")), null);
            SkillCoolingImager skillCoolingImager = new SkillCoolingImager(skillStyle);
            skillCoolingImager.setWidth(24);
            skillCoolingImager.setHeight(24);
            skillHold.add(skillCoolingImager);
            window.add(skillHold);
        }
        {
            Table skillHold = new Table();
            skillHold.setDebug(true);
            skillHold.setClip(true);
            skillHold.setWidth(32);
            skillHold.setHeight(32);
            Button.ButtonStyle skillStyle = new Button.ButtonStyle(new TextureRegionDrawable(ResourceManager.getTextureRegion("BTNImmolationOn")), new MiniatureTextureRegionDrawable(ResourceManager.getTextureRegion("BTNImmolationOn")), null);
            SkillCoolingImager skillCoolingImager = new SkillCoolingImager(skillStyle);
            skillCoolingImager.setWidth(32);
            skillCoolingImager.setHeight(32);
            skillHold.add(skillCoolingImager);
            window.add(skillHold);
        }
        {
            Table skillHold = new Table();
            skillHold.setDebug(true);
            skillHold.setClip(true);
            skillHold.setWidth(32);
            skillHold.setHeight(32);
            Button.ButtonStyle skillStyle = new Button.ButtonStyle(new TextureRegionDrawable(ResourceManager.getTextureRegion("BTNImmolationOn")), new MiniatureTextureRegionDrawable(ResourceManager.getTextureRegion("BTNImmolationOn")), null);
            SkillCoolingImager skillCoolingImager = new SkillCoolingImager(skillStyle);
            skillCoolingImager.setWidth(32);
            skillCoolingImager.setHeight(32);
            skillHold.add(skillCoolingImager);
            window.add(skillHold);
        }
        {
            Table skillHold = new Table();
            skillHold.setDebug(true);
            skillHold.setClip(true);
            skillHold.setWidth(32);
            skillHold.setHeight(32);
            Button.ButtonStyle skillStyle = new Button.ButtonStyle(new TextureRegionDrawable(ResourceManager.getTextureRegion("BTNImmolationOn")), new MiniatureTextureRegionDrawable(ResourceManager.getTextureRegion("BTNImmolationOn")), null);
            SkillCoolingImager skillCoolingImager = new SkillCoolingImager(skillStyle);
            skillCoolingImager.setWidth(32);
            skillCoolingImager.setHeight(32);
            skillHold.add(skillCoolingImager);
            window.add(skillHold);
        }
        CheckBox checkBox = new CheckBox("as",skin);
        progressBar = new ProgressBar(0,100,1,false,skin);
        progressBar.setValue(0);

        TextField textField = new TextField("123",skin);
        TextButton textButton = new TextButton("but",skin);
//        window.add(textButton);
//        window.add(checkBox);
//        window.add(progressBar);
//        window.add(textField);

        uiStage.addActor(window);

        Gdx.input.setInputProcessor(new InputProcessorLine(uiStage)); //InputMultiplexer

//		mapStage = new TitleMapStage("desert1.tmx");
//
//		 //添加一个button用来测试对比该
//		 TextButtonStyle bs=new TextButtonStyle(ResourceManager.up,ResourceManager.down,null,ResourceManager.font);
//		 DebugButton button=new DebugButton("ABCD你好确定",bs);
//		 button.setPosition(100, 100);
//		// button.addListener();
//		 mapStage.addActor(button);
//
//		 uiStage = new Stage();
//
//
//        TouchpadStyle touchpadStyle=new TouchpadStyle(ResourceManager.down,ResourceManager.BTNImmolationOn1 );
//		 final Touchpad touchpad=new Touchpad(12, touchpadStyle);
//		 touchpad.setPosition(24, 24);
//		 touchpad.addListener(new ChangeListener() {
//			@Override
//			public void changed(ChangeEvent event, Actor actor1) {
//				//System.out.println(touchpad.getKnobPercentX()+","+touchpad.getKnobPercentY());
//				if(touchpad.getKnobPercentX()==0 && touchpad.getKnobPercentY()==0){
//					mapStage.viewActor.stop();
//				}else if(Math.abs(touchpad.getKnobPercentX()) > Math.abs(touchpad.getKnobPercentY())){
//					if(touchpad.getKnobPercentX()>0){
//						//System.out.println("touchpad left");
//						mapStage.viewActor.moveLeft();
//					}else{
//                        //System.out.println("touchpad right");
//						mapStage.viewActor.moveRight();
//					}
//				}else{
//					if(touchpad.getKnobPercentY()>0){
//						//System.out.println("touchpad down");
//						mapStage.viewActor.moveDown();
//					}else{
//						mapStage.viewActor.moveUp();
//						//System.out.println("touchpad up");
//					}
//				}
//			}
//		});
//		 uiStage.addActor(touchpad);
//
//		 ImageButton imageButton = new ImageButton(ResourceManager.BTNImmolationOn, ResourceManager.BTNImmolationOn1);
//		 //imageButton.setSize(20f, 20f);;
//		 imageButton.setPosition(100f, 10f);
//		 uiStage.addActor(imageButton);
//		 Gdx.input.setInputProcessor(new InputProcessorLine(uiStage, mapStage)); //InputMultiplexer
//		 mapStage.addListener(new ClickListener() {
//             @Override
//             public boolean handle(Event e) {
//                 //System.out.println("mapStage ClickListener:"+e);
//
//                 if (!(e instanceof InputEvent)) return false;
//                 InputEvent event = (InputEvent) e;
//                 if (mapStage.viewActor != null) {
//                     mapStage.viewActor.handleInput(event);
//                     return true;
//                 }
//                 return super.handle(e);
//             }
//         });
//		//openDialog("script.html",null);
//
//		batch = new SpriteBatch();
//		effect = new ParticleEffect();
//        effect.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
//        effect.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
//        effect.start();
//
//		ScriptableObject.putProperty(scope, "out", System.out);
//		ScriptableObject.putProperty(scope, "mapStage", mapStage);
//		ScriptableObject.putProperty(scope, "camera", camera);
//		ScriptableObject.putProperty(scope, "effect", effect);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		((OrthographicCamera) uiStage.getCamera()).setToOrtho(false, width, height);
	}





    float a1=1f,a2,a3;

    @Override
	public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        mapStage.act();
//        mapStage.draw();
//        progressBar.setValue(progressBar.getValue()+1f);
        uiStage.act();
        uiStage.draw();
//        batch.begin();
//        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//        shapeRenderer.setAutoShapeType(true);
//        shapeRenderer.begin();
//        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(0, 1, 0, 0.5f);
//        shapeRenderer.arc(200,200,100,0,a2++,5);
//        shapeRenderer.identity();
//        //shapeRenderer.rect(200, 200, 50, 50);
////        shapeRenderer.curve();
//        //shapeRenderer.circle(200,200,100, (int) (a1+=0.1));
//        shapeRenderer.end();
//        batch.end();
       // batch.begin();
		//effect.draw(batch, Gdx.app.getGraphics().getDeltaTime());
        //System.out.println(Gdx.graphics.getFramesPerSecond());
		//batch.end();
        if(this.CurFPS != Gdx.graphics.getFramesPerSecond()) {
            Gdx.graphics.setTitle("FPS:"+String.valueOf(Gdx.graphics.getFramesPerSecond()));
            this.CurFPS = Gdx.graphics.getFramesPerSecond();
        }
	}
    float CurFPS;

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
