package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guxuede.game.libgdx.ResourceManager;

public class BarrageTip extends Label{

	public BarrageTip(CharSequence text,float x,float y) {
		super(text, new LabelStyle(ResourceManager.font, Color.RED));
		this.addAction(Actions.parallel(Actions.moveBy(0, 100, 4),Actions.fadeOut(5)));
		this.setPosition(x, y);
	}
	
}
