package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class FontScaleAction extends RelativeTemporalAction  {
	private float amountX, amountY;

	@Override
	protected void updateRelative(float percentDelta) {
		Label lab = (Label) target;
		float x=lab.getFontScaleX() + amountX * percentDelta;
		float y=lab.getFontScaleY() + amountY * percentDelta;
		lab.setFontScale(x,y);
	}


	public void setAmount (float x, float y) {
		amountX = x;
		amountY = y;
	}

	public void setAmount (float scale) {
		amountX = scale;
		amountY = scale;
	}

	public float getAmountX () {
		return amountX;
	}

	public void setAmountX (float x) {
		this.amountX = x;
	}

	public float getAmountY () {
		return amountY;
	}

	public void setAmountY (float y) {
		this.amountY = y;
	}



}
