package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class DebugButton extends TextButton{
	private float speed = 1.0f;

	public DebugButton(String text, TextButtonStyle bs) {
		super(text, bs);
		addListener( new ClickListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				System.err.println("DebugButton enter");
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				System.err.println("DebugButton exit");
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				Actor hit = event.getListenerActor().hit(x, y, true);
				System.err.println("DebugButton:"+hit+":"+x+":"+y);
				super.touchDragged(event, x, y, pointer);
				//System.err.println(isPressed());
			}
		});
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		Actor r=super.hit(x, y, touchable);
		//System.err.println(x+","+y+"~~~~"+this.getWidth()+":"+this.getHeight()+"~~~"+touchable+"~~~"+(touchable && getTouchable() == Touchable.disabled)+"~~~"+(x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) +"~~~"+(x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) +"~~"+r);
		return r;
	}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		//System.err.println(getX()+","+getY());
	
		super.draw(batch, parentAlpha);
		
		
		
		if(Gdx.input.isKeyPressed(Keys.W)){
			this.moveBy(0, speed);
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			this.moveBy(0, -speed);
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			this.moveBy(speed, 0);
		}else if(Gdx.input.isKeyPressed(Keys.A)){
			this.moveBy(-speed, 0);
		} 
	}

	
}
