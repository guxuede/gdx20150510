package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GdxTextureAtlas;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by guxuede on 2017/4/8 .
 */
public class GdxSkin extends Skin {

    /** Creates an empty skin. */
    public GdxSkin () {
    }

    /** Creates a skin containing the resources in the specified skin JSON file. If a file in the same directory with a ".atlas"
     * extension exists, it is loaded as a {@link TextureAtlas} and the texture regions added to the skin. The atlas is
     * automatically disposed when the skin is disposed. */
    public GdxSkin (FileHandle skinFile) {
        FileHandle atlasFile = skinFile.sibling(skinFile.nameWithoutExtension() + ".atlas");
        if (atlasFile.exists()) {
            atlas = new GdxTextureAtlas(atlasFile);
            addRegions(atlas);
        }

        load(skinFile);
    }

    /** Creates a skin containing the resources in the specified skin JSON file and the texture regions from the specified atlas.
     * The atlas is automatically disposed when the skin is disposed. */
    public GdxSkin (FileHandle skinFile, GdxTextureAtlas atlas) {
        this.atlas = atlas;
        addRegions(atlas);
        load(skinFile);
    }

    /** Creates a skin containing the texture regions from the specified atlas. The atlas is automatically disposed when the skin
     * is disposed. */
    public GdxSkin (GdxTextureAtlas atlas) {
        this.atlas = atlas;
        addRegions(atlas);
    }


    /** Returns a registered ninepatch. If no ninepatch is found but a region exists with the name, a ninepatch is created from the
     * region and stored in the skin. If the region is an {@link TextureAtlas.AtlasRegion} then the {@link TextureAtlas.AtlasRegion#splits} are used,
     * otherwise the ninepatch will have the region as the center patch. */
    public NinePatch getPatch (String name) {
        NinePatch patch = optional(name, NinePatch.class);
        if (patch != null) return patch;

        try {
            TextureRegion region = getRegion(name);
            if (region instanceof TextureAtlas.AtlasRegion) {
                int[] splits = ((TextureAtlas.AtlasRegion)region).splits;
                if (splits != null) {
                    if(splits.length == 4){
                        patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
                        int[] pads = ((TextureAtlas.AtlasRegion)region).pads;
                        if (pads != null) patch.setPadding(pads[0], pads[1], pads[2], pads[3]);
                    }else{
                        patch = new NinePatch(
                                new TextureRegion(region,splits[0], splits[1], splits[2], splits[3]),
                                new TextureRegion(region,splits[4], splits[5], splits[6], splits[7]),
                                new TextureRegion(region,splits[8], splits[9], splits[10], splits[11]),

                                new TextureRegion(region,splits[12], splits[13], splits[14], splits[15]),
                                new TextureRegion(region,splits[16], splits[17], splits[18], splits[19]),
                                new TextureRegion(region,splits[20], splits[21], splits[22], splits[23]),

                                new TextureRegion(region,splits[24], splits[25], splits[26], splits[27]),
                                new TextureRegion(region,splits[28], splits[29], splits[30], splits[31]),
                                new TextureRegion(region,splits[32], splits[33], splits[34], splits[35])
                        );
                    }
                }
            }
            if (patch == null) patch = new NinePatch(region);
            add(name, patch, NinePatch.class);
            return patch;
        } catch (GdxRuntimeException ex) {
            ex.printStackTrace();
            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name,ex);
        }
    }
}
