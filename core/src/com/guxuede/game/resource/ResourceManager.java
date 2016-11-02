package com.guxuede.game.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.libgdx.Skin;
import com.guxuede.game.resource.ActorJsonParse;
import com.guxuede.game.resource.AnimationHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager {
    private static final Map<String,Texture> TEXTURE_MAP = new HashMap<String, Texture>();
    private static final Map<String,TextureRegion> TEXTURE_REGION_MAP =  new HashMap<String, TextureRegion>();
    public static final TextureAtlas TEXTURE_ATLAS_PACK =new TextureAtlas(Gdx.files.internal("pack"));
    public static final Map<String,Sound> soundMap = new HashMap<String, Sound>();
    public static final List<AnimationHolder> ANIMATION_HOLDER_LIST = ActorJsonParse.parse("actor.json");

    //一些资源初始化
	public static Skin skin=new Skin(Gdx.files.internal("uiskin.json"));
	public static BitmapFont font = skin.getFont("default-font");

    public static TextureRegion region_up=getTextureRegion("imgbut", 0, 0, 65, 19);
    public static TextureRegion region_down=getTextureRegion("imgbut", 0,19,65,19);
    public static NinePatchDrawable up= new NinePatchDrawable(new NinePatch(region_up,5,5,5,5));
    public static NinePatchDrawable down= new NinePatchDrawable(new NinePatch(region_down,5,5,5,5));
    public static Sprite mouseAreaIndicator=new GdxSprite(getTextureRegion("Gun2", 0, 0, 192, 192));
    public static Sprite mouseTargetIndicator=new GdxSprite(getTextureRegion("Gun2", 84, 84, 24, 24));
    static {
        mouseAreaIndicator.setOriginCenter();
        mouseTargetIndicator.setOriginCenter();
    }

    public static Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor_1.gif")), 0, 0);


    //添加一个button用来测试对比该
    public static TextButtonStyle bs=new TextButtonStyle(up,down,null,font);

    public static TextureRegion humanShadow= getTextureRegion("180-Switch03",96,96,32,32);
    public static Sprite shadow = new Sprite(getTextureRegion("180-Switch03",96,96,32,32));
    public static Texture getTexture(String name){
        Texture texture = null;
        texture = TEXTURE_MAP.get(name);
        if(texture==null){
            if(name.contains(".")){
                throw new RuntimeException("resource name not support contain dot or suffix");
            }
            FileHandle fileHandle = Gdx.files.internal(name+".PNG");
            if(fileHandle.exists()){
                texture = new Texture(fileHandle);
                TEXTURE_MAP.put(name, texture);
                TEXTURE_REGION_MAP.put(name, new TextureRegion(texture));
            }else{
                System.err.println(name + " resource not found.");
            }
        }
        return texture;
    }

    public static TextureRegion getTextureRegion(String name,int x,int y,int w,int h){
        TextureRegion textureRegion = null;
        String key = name+"_"+x+"x"+y+"x"+"x"+w+"x"+h;
        textureRegion = getTextureRegion(key);
        if(textureRegion==null){
            TextureRegion fullTextureRegion = getTextureRegion(name);
            if(fullTextureRegion!=null){
                textureRegion = new TextureRegion(fullTextureRegion,x,y,w,h);
                TEXTURE_REGION_MAP.put(key, textureRegion);
            }
        }
        return textureRegion;
    }
    public static TextureRegion getTextureRegion(String name){
        TextureRegion textureRegion = null;
        textureRegion = TEXTURE_REGION_MAP.get(name);
        if(textureRegion==null){
            textureRegion = TEXTURE_ATLAS_PACK.findRegion(name);
        }
        if(textureRegion==null){
            Texture texture = getTexture(name);
            if(texture !=null){
                textureRegion = TEXTURE_REGION_MAP.get(name);
            }
        }
        return textureRegion;
    }

    public static AnimationHolder getAnimationHolder(String name){
        for(AnimationHolder animationHolder : ANIMATION_HOLDER_LIST){
            if(name.equals(animationHolder.name)){
                return animationHolder;//.getCopy();
            }
        }
        if("spine".equalsIgnoreCase(name)){

            new AnimationHolder();
        }
        return null;
    }

    public static Sound getSoundOrLoad(String soundFile){
        if(soundMap.containsKey(soundFile)){
            return soundMap.get(soundFile);
        }
        if(Gdx.files.internal("sounds/"+soundFile).exists()){
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/"+soundFile));
            soundMap.put(soundFile,sound);
        }
        return soundMap.get(soundFile);
    }
}
