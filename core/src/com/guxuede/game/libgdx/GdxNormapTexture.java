package com.guxuede.game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

/**
 * Created by guxuede on 2017/1/8 .
 */
public class GdxNormapTexture extends Texture {

    public Texture normap;

    public GdxNormapTexture(String internalPath) {
        super(internalPath);
        loadNormapIfExsit(Gdx.files.internal(internalPath),null,false);
    }

    public GdxNormapTexture(FileHandle file) {
        super(file);
        loadNormapIfExsit(file,null,false);
    }

    public GdxNormapTexture(FileHandle file, boolean useMipMaps) {
        super(file, useMipMaps);
        loadNormapIfExsit(file,null,useMipMaps);
    }

    public GdxNormapTexture(FileHandle file, Pixmap.Format format, boolean useMipMaps) {
        super(file, format, useMipMaps);
        loadNormapIfExsit(file,format,useMipMaps);
    }

    public GdxNormapTexture(Pixmap pixmap) {
        super(pixmap);
    }

    public GdxNormapTexture(Pixmap pixmap, boolean useMipMaps) {
        super(pixmap, useMipMaps);
    }

    public GdxNormapTexture(Pixmap pixmap, Pixmap.Format format, boolean useMipMaps) {
        super(pixmap, format, useMipMaps);
    }

    public GdxNormapTexture(int width, int height, Pixmap.Format format) {
        super(width, height, format);
    }

    public GdxNormapTexture(TextureData data) {
        super(data);
    }

    protected GdxNormapTexture(int glTarget, int glHandle, TextureData data) {
        super(glTarget, glHandle, data);
    }

    protected void loadNormapIfExsit(FileHandle file, Pixmap.Format format, boolean useMipMaps){
        FileHandle normapFile = file.sibling(file.nameWithoutExtension() + "_n." + file.extension());
        if(normapFile.exists()){
            normap = new Texture(normapFile,format,useMipMaps);
        }
    }

    @Override
    public void bind() {
        super.bind();
//        if(normap!=null){
//            normap.bind(1);
//        }
    }
}
