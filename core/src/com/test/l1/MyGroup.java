package com.test.l1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;

public class MyGroup extends Group{

	private OrthographicCamera orthographicCamera;
	
	public MyGroup() {
		orthographicCamera=new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		orthographicCamera.position.set(0, 0, 0);
		orthographicCamera.update();
		
		
	}
	
	
}
