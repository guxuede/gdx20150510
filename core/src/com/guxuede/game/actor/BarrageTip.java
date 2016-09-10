package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guxuede.game.action.FontScaleAction;
import com.guxuede.game.libgdx.ResourceManager;

public class BarrageTip extends Label{

	public BarrageTip(CharSequence text,float x,float y) {
		super(text, new LabelStyle(ResourceManager.font, Color.RED));
		FontScaleAction s = new FontScaleAction();
		s.setAmount(1, 1);
		s.setDuration(0.125f);
		FontScaleAction s1 = new FontScaleAction();
		s1.setAmount(-1, -1);
		s1.setDuration(0.125f);
		this.addAction(
                Actions.parallel(Actions.moveBy(0, 50, 3),
                    Actions.sequence(s,s1),
                        Actions.sequence(Actions.fadeOut(3), Actions.removeActor())
                        )
        );
		this.setPosition(x, y);
		//this.setFontScale(6);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}
}
