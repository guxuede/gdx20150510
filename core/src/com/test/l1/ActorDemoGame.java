package com.test.l1;

import com.badlogic.gdx.ApplicationListener; 
import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ActorDemoGame implements ApplicationListener {
	
    private Stage stage; 
    
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
		 
		 TextButtonStyle bs=new TextButtonStyle(up,down,null,font);
		  button=new TextButton("ABCD你好确定",bs);
		 button.setBounds(100, 100,100,100);
		 stage.addActor(button);
		
		 grop=new Group();
		grop.setColor(Color.BLUE);
		grop.setBounds(100, 100, 1, 1);
		grop.addActor(button);
		
		stage.addActor(grop);
    }
    TextButton button;
    Group grop;
    @Override 
    public void render() { 
    	//System.out.println(button.isOver()+","+button.isPressed());
    	Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         stage.act(); 
         stage.draw();
         button.moveBy(0.02f, 0);
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
		// TODO Auto-generated method stub
		
	}

}