package com.test.game;

import com.badlogic.gdx.ApplicationListener; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.guxuede.game.actor.DebugButton;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.libgdx.Skin;
public class MyMapLayerGame implements ApplicationListener {
	
    private Stage stage; 
    TextButton button;
    @Override 
    public void create() {

        stage = new Stage(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()))); 
        Gdx.input.setInputProcessor(stage); 
        //一些资源初始化
         BitmapFont font = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(13,"你好确定取消",false);
		 Texture but=new Texture(Gdx.files.internal("imgbut.PNG"));
		 TextureRegion region_up=new TextureRegion(but, 0,0,65,19);
		 TextureRegion region_down=new TextureRegion(but, 0,19,65,19);
		 NinePatchDrawable up= new NinePatchDrawable(new NinePatch(region_up,5,5,5,5));
		 NinePatchDrawable down= new NinePatchDrawable(new NinePatch(region_down,5,5,5,5));

		 //自定义的maplayer
		 final MapLayerGroup mapLayer=new MapLayerGroup();
		 stage.addActor(mapLayer);
		 

		 //添加一个button用来测试对比该
		 TextButtonStyle bs=new TextButtonStyle(up,down,null,font);
		 button=new DebugButton("ABCD你好确定",bs);
		 //button.setPosition(100, 100);
		// button.addListener();
		 mapLayer.addActor(button);
		 
		 
		 TouchpadStyle touchpadStyle=new TouchpadStyle(down,up );
		 final Touchpad touchpad=new Touchpad(12, touchpadStyle);touchpad.setPosition(24, 24);
		 touchpad.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println(touchpad.getKnobPercentX()+","+touchpad.getKnobPercentY());
				if(touchpad.getKnobPercentX()==0 && touchpad.getKnobPercentY()==0){
					mapLayer.actor.stop();
				}else if(Math.abs(touchpad.getKnobPercentX()) > Math.abs(touchpad.getKnobPercentY())){
					if(touchpad.getKnobPercentX()>0){
						System.out.println("touchpad left");
						mapLayer.actor.moveLeft();
					}else{
						System.out.println("touchpad right");
						mapLayer.actor.moveRight();
					}
				}else{
					if(touchpad.getKnobPercentY()>0){
						System.out.println("touchpad down");
						mapLayer.actor.moveDown();
					}else{
						mapLayer.actor.moveUp();
						System.out.println("touchpad up");
					}
				}
			}
		});
		 stage.addActor(touchpad);
		 
		//添加一个button用来测试对比该
		 //TextButton TB=new DebugButton("ABCD你好确定",bs);
		// TB.setX(200);
		 //stage.addActor(TB);
		 
		 //addADialog(bs);
    }



    @Override 
    public void render() { 
    	//System.out.println(button.isOver()+","+button.isPressed());
    	Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         stage.act(); 
         stage.draw();
    }

    @Override 
    public void dispose() { 

    }

    @Override 
    public void pause() { 

    }
    
    @Override 
    public void resize(int width, int height) { 

    }

    @Override 
    public void resume() { 

    } 
    
    
    private void addADialog(TextButtonStyle bs){
    	Skin skin=new Skin(Gdx.files.internal("uiskin.json"));
    	Dialog a=new Dialog("MOVEME", skin); 
    	a.setModal(false);
    	a.setMovable(true);
    	a.addActor(new TextButton("你",bs));
    	a.setWidth(200);
    	a.setHeight(100);
    	ScrollPane b=new ScrollPane(a,skin); 
    	b.setWidth(100);
    	b.setHeight(300);
    	stage.addActor(b);
    }
}