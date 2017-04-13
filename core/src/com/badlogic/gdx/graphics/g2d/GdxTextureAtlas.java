package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by guxuede on 2017/4/8 .
 */
public class GdxTextureAtlas extends TextureAtlas {

    /** Creates an empty atlas to which regions can be added. */
    public GdxTextureAtlas () {
    }

    /** Loads the specified pack file using {@link Files.FileType#Internal}, using the parent directory of the pack file to find the page
     * images. */
    public GdxTextureAtlas (String internalPackFile) {
        this(Gdx.files.internal(internalPackFile));
    }

    /** Loads the specified pack file, using the parent directory of the pack file to find the page images. */
    public GdxTextureAtlas (FileHandle packFile) {
        this(packFile, packFile.parent());
    }

    /** @param flip If true, all regions loaded will be flipped for use with a perspective where 0,0 is the upper left corner.
     * @see #GdxTextureAtlas(FileHandle) */
    public GdxTextureAtlas (FileHandle packFile, boolean flip) {
        this(packFile, packFile.parent(), flip);
    }

    public GdxTextureAtlas (FileHandle packFile, FileHandle imagesDir) {
        this(packFile, imagesDir, false);
    }

    /** @param flip If true, all regions loaded will be flipped for use with a perspective where 0,0 is the upper left corner. */
    public GdxTextureAtlas (FileHandle packFile, FileHandle imagesDir, boolean flip) {
        this(new GdxTextureAtlasData(packFile, imagesDir, flip));
    }

    /** @param data May be null. */
    public GdxTextureAtlas (GdxTextureAtlasData data) {
        super(data);
    }

    /** Returns the first region found with the specified name as a {@link NinePatch}. The region must have been packed with
     * ninepatch splits. This method uses string comparison to find the region and constructs a new ninepatch, so the result should
     * be cached rather than calling this method multiple times.
     * @return The ninepatch, or null. */
    public NinePatch createPatch (String name) {
        for (int i = 0, n = getRegions().size; i < n; i++) {
            AtlasRegion region =  getRegions().get(i);
            if (region.name.equals(name)) {
                int[] splits = region.splits;
                if (splits == null) throw new IllegalArgumentException("Region does not have ninepatch splits: " + name);

                if(splits.length==4){
                    NinePatch patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
                    if (region.pads != null) patch.setPadding(region.pads[0], region.pads[1], region.pads[2], region.pads[3]);
                    return patch;
                }else {
                    NinePatch patch = new NinePatch(
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
                    return patch;
                }

            }
        }
        return null;
    }
}
