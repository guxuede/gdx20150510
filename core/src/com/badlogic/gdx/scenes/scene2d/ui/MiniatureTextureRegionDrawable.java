package com.badlogic.gdx.scenes.scene2d.ui;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

/** Drawable for a {@link TextureRegion}.
 * @author Nathan Sweet */
public class MiniatureTextureRegionDrawable extends BaseDrawable{
    private static final int MiniatureSize=1;
    private Drawable drawable;

    public MiniatureTextureRegionDrawable (Drawable drawable) {
        super(drawable);
        this.drawable = drawable;
    }

    public MiniatureTextureRegionDrawable (TextureRegion region) {
        this(new TextureRegionDrawable(region));
    }



    public void draw (Batch batch, float x, float y, float width, float height) {
        drawable.draw(batch,x+MiniatureSize, y+MiniatureSize, width-MiniatureSize*2, height-MiniatureSize*2);
    }

}