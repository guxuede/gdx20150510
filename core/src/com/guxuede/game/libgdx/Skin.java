package com.guxuede.game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;

public class Skin extends com.badlogic.gdx.scenes.scene2d.ui.Skin{
	
	

	public Skin() {
		super();
	}

	public Skin(FileHandle skinFile, TextureAtlas atlas) {
		super(skinFile, atlas);
	}

	public Skin(FileHandle skinFile) {
		super(skinFile);
	}

	public Skin(TextureAtlas atlas) {
		super(atlas);
	}

	@Override
	protected Json getJsonLoader(final FileHandle skinFile) {
		Json json= super.getJsonLoader(skinFile);
		json.setSerializer(BitmapFont.class, new ReadOnlySerializer<BitmapFont>() {
			public BitmapFont read (Json json, JsonValue jsonData, Class type) {
				String path = json.readValue("file", String.class, jsonData);

				FileHandle fontFile = skinFile.parent().child(path);
				if (!fontFile.exists()) fontFile = Gdx.files.internal(path);
				if (!fontFile.exists()) throw new SerializationException("Font file not found: " + fontFile);
				//SUPPORT FOR FreeTypeFont
				if("ttf".equalsIgnoreCase(fontFile.extension())){
					String charsFile = json.readValue("charsFile", String.class,"", jsonData);
					String chars = json.readValue("chars", String.class, "",jsonData);
					Integer size = json.readValue("size", Integer.class, -1,jsonData);
					if(size==-1) throw new SerializationException("Font size must set");
					String charsTxt = null;
					if(charsFile!=null){
						charsTxt = skinFile.parent().child(charsFile).readString("utf-8");
					}else if("".equals(chars)){
						charsTxt=chars;
					}
					FreeTypeFontParameter param = new FreeTypeFontParameter();
					param.size=size;
					FreeTypeFontGenerator fontGenerator=new FreeTypeFontGenerator(fontFile);
					BitmapFont bitmapFont=null;
					if(charsTxt==null){
						bitmapFont= fontGenerator.generateFont(param);
					}else{
						param.characters = charsTxt;
						bitmapFont= fontGenerator.generateFont(param);
					}
					fontGenerator.dispose();
					return bitmapFont;
				}
				// Use a region with the same name as the font, else use a PNG file in the same directory as the FNT file.
				String regionName = fontFile.nameWithoutExtension();
				try {
					TextureRegion region = Skin.this.optional(regionName, TextureRegion.class);
					if (region != null)
						return new BitmapFont(fontFile, region, false);
					else {
						FileHandle imageFile = fontFile.parent().child(regionName + ".png");
						if (imageFile.exists())
							return new BitmapFont(fontFile, imageFile, false);
						else
							return new BitmapFont(fontFile, false);
					}
				} catch (RuntimeException ex) {
					throw new SerializationException("Error loading bitmap font: " + fontFile, ex);
				}
			}
		});
		return json;
	}

	
}
