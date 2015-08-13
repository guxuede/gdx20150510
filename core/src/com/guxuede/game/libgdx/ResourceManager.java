package com.guxuede.game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ResourceManager {

    //一些资源初始化
	public static Skin skin=new Skin(Gdx.files.internal("uiskin.json"));
	public static BitmapFont font = skin.getFont("default-font");
    public static Texture but=new Texture(Gdx.files.internal("imgbut.PNG"));
    public static TextureRegion region_up=new TextureRegion(but, 0,0,65,19);
    public static TextureRegion region_down=new TextureRegion(but, 0,19,65,19);
    public static NinePatchDrawable up= new NinePatchDrawable(new NinePatch(region_up,5,5,5,5));
    public static NinePatchDrawable down= new NinePatchDrawable(new NinePatch(region_down,5,5,5,5));

	 //添加一个button用来测试对比该
    public static TextButtonStyle bs=new TextButtonStyle(up,down,null,font);
    public static TextureAtlas atlas=new TextureAtlas(Gdx.files.internal("pack"));
    
    public static TextureRegionDrawable BTNImmolationOn=new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("BTNImmolationOn.png"))));
    public static MiniatureTextureRegionDrawable BTNImmolationOn1=new MiniatureTextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("BTNImmolationOn.png"))));
    
    public static TextureRegion bult= new TextureRegion(new Texture(Gdx.files.internal("Image 166.png")));

}
