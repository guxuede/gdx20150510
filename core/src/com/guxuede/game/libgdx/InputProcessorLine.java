package com.guxuede.game.libgdx;

import com.badlogic.gdx.InputProcessor;

public class InputProcessorLine implements InputProcessor{

	InputProcessor[] processors;
	
	public InputProcessorLine(InputProcessor ... aarrays ) {
		this.processors = aarrays;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		boolean result = false;
		for(InputProcessor i:processors){
			result=i.keyDown(keycode);
		}
		return result;
	}

	@Override
	public boolean keyUp(int keycode) {
		boolean result = false;
		for(InputProcessor i:processors){
			result=i.keyUp(keycode);
		}
		return result;
	}

	@Override
	public boolean keyTyped(char character) {
		boolean result = false;
		for(InputProcessor i:processors){
			result=i.keyTyped(character);
		}
		return result;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		boolean result = false;
		for(InputProcessor i:processors){
			result=i.touchDown(screenX,screenY,pointer,button);
		}
		return result;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		boolean result = false;
		for(InputProcessor i:processors){
			result=i.touchUp(screenX,screenY,pointer,button);
		}
		return result;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		boolean result = false;
		for(InputProcessor i:processors){
			result=i.touchDragged(screenX,screenY,pointer);
		}
		return result;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
