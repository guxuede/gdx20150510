/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.guxuede.game.libgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

/** Drawable for a {@link TextureRegion}.
 * @author Nathan Sweet */
public class MiniatureTextureRegionDrawable extends BaseDrawable implements TransformDrawable {
	private static final int MiniatureSize=1;
	private TextureRegion region;

	public MiniatureTextureRegionDrawable () {
	}

	public MiniatureTextureRegionDrawable (TextureRegion region) {
		setRegion(region);
	}

	public MiniatureTextureRegionDrawable (TextureRegionDrawable drawable) {
		super(drawable);
		setRegion(drawable.getRegion());
	}

	public void draw (Batch batch, float x, float y, float width, float height) {
		batch.draw(region, x+MiniatureSize, y+MiniatureSize, width-MiniatureSize*2, height-MiniatureSize*2);
	}

	public void draw (Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX,float scaleY, float rotation) {
		batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
	}

	public void setRegion (TextureRegion region) {
		this.region = region;
		setMinWidth(region.getRegionWidth());
		setMinHeight(region.getRegionHeight());
	}

	public TextureRegion getRegion () {
		return region;
	}

}
