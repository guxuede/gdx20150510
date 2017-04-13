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

package com.badlogic.gdx.graphics.g2d;

import static com.badlogic.gdx.graphics.Texture.TextureWrap.*;
import static com.badlogic.gdx.graphics.g2d.TextureAtlas.indexComparator;
import static com.badlogic.gdx.graphics.g2d.TextureAtlas.readValue;
import static com.badlogic.gdx.graphics.g2d.TextureAtlas.tuple;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Page;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/** Loads images from texture atlases created by TexturePacker.<br>
 * <br>
 * A TextureAtlas must be disposed to free up the resources consumed by the backing textures.
 * @author Nathan Sweet */
public class GdxTextureAtlasData extends TextureAtlas.TextureAtlasData {
    static final String[] tuple = new String[36];


    public GdxTextureAtlasData(FileHandle packFile, FileHandle imagesDir, boolean flip) {
        super(packFile.parent().child("EMPTY.atlas"),imagesDir,flip);
        BufferedReader reader = new BufferedReader(new InputStreamReader(packFile.read()), 64);
        try {
            Page pageImage = null;
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.trim().length() == 0)
                    pageImage = null;
                else if (pageImage == null) {
                    FileHandle file = imagesDir.child(line);

                    float width = 0, height = 0;
                    if (readTuple(reader) == 2) { // size is only optional for an atlas packed with an old TexturePacker.
                        width = Integer.parseInt(tuple[0]);
                        height = Integer.parseInt(tuple[1]);
                        readTuple(reader);
                    }
                    Format format = Format.valueOf(tuple[0]);

                    readTuple(reader);
                    TextureFilter min = TextureFilter.valueOf(tuple[0]);
                    TextureFilter max = TextureFilter.valueOf(tuple[1]);

                    String direction = readValue(reader);
                    TextureWrap repeatX = ClampToEdge;
                    TextureWrap repeatY = ClampToEdge;
                    if (direction.equals("x"))
                        repeatX = Repeat;
                    else if (direction.equals("y"))
                        repeatY = Repeat;
                    else if (direction.equals("xy")) {
                        repeatX = Repeat;
                        repeatY = Repeat;
                    }

                    pageImage = new Page(file, width, height, min.isMipMap(), format, min, max, repeatX, repeatY);
                    pages.add(pageImage);
                } else {
                    boolean rotate = Boolean.valueOf(readValue(reader));

                    readTuple(reader);
                    int left = Integer.parseInt(tuple[0]);
                    int top = Integer.parseInt(tuple[1]);

                    readTuple(reader);
                    int width = Integer.parseInt(tuple[0]);
                    int height = Integer.parseInt(tuple[1]);

                    Region region = new Region();
                    region.page = pageImage;
                    region.left = left;
                    region.top = top;
                    region.width = width;
                    region.height = height;
                    region.name = line;
                    region.rotate = rotate;

                    final int split = readTuple(reader);
                    if (split == 4 || split==36) { // split is optional
                        if(split==4){
                            region.splits = new int[] {Integer.parseInt(tuple[0]), Integer.parseInt(tuple[1]),
                                    Integer.parseInt(tuple[2]), Integer.parseInt(tuple[3])};
                        }else {
                            region.splits = new int[] {
                                    Integer.parseInt(tuple[0]), Integer.parseInt(tuple[1]),Integer.parseInt(tuple[2]),Integer.parseInt(tuple[3]),
                                    Integer.parseInt(tuple[4]),Integer.parseInt(tuple[5]),Integer.parseInt(tuple[6]), Integer.parseInt(tuple[7]),
                                    Integer.parseInt(tuple[8]),Integer.parseInt(tuple[9]), Integer.parseInt(tuple[10]),Integer.parseInt(tuple[11]),

                                    Integer.parseInt(tuple[12]), Integer.parseInt(tuple[13]),Integer.parseInt(tuple[14]),Integer.parseInt(tuple[15]),
                                    Integer.parseInt(tuple[16]),Integer.parseInt(tuple[17]),Integer.parseInt(tuple[18]), Integer.parseInt(tuple[19]),
                                    Integer.parseInt(tuple[20]),Integer.parseInt(tuple[21]), Integer.parseInt(tuple[22]),Integer.parseInt(tuple[23]),

                                    Integer.parseInt(tuple[24]), Integer.parseInt(tuple[25]),Integer.parseInt(tuple[26]),Integer.parseInt(tuple[27]),
                                    Integer.parseInt(tuple[28]), Integer.parseInt(tuple[29]),Integer.parseInt(tuple[30]),Integer.parseInt(tuple[31]),
                                    Integer.parseInt(tuple[32]), Integer.parseInt(tuple[33]),Integer.parseInt(tuple[34]),Integer.parseInt(tuple[35]),
                            };
                        }

                        if (readTuple(reader) == 4) { // pad is optional, but only present with splits
                            region.pads = new int[] {Integer.parseInt(tuple[0]), Integer.parseInt(tuple[1]),
                                    Integer.parseInt(tuple[2]), Integer.parseInt(tuple[3])};

                            readTuple(reader);
                        }
                    }
                    region.originalWidth = Integer.parseInt(tuple[0]);
                    region.originalHeight = Integer.parseInt(tuple[1]);

                    readTuple(reader);
                    region.offsetX = Integer.parseInt(tuple[0]);
                    region.offsetY = Integer.parseInt(tuple[1]);

                    region.index = Integer.parseInt(readValue(reader));

                    if (flip) region.flip = true;

                    regions.add(region);
                }
            }
        } catch (Exception ex) {
            throw new GdxRuntimeException("Error reading pack file: " + packFile, ex);
        } finally {
            StreamUtils.closeQuietly(reader);
        }

        regions.sort(indexComparator);
    }

    /** Returns the number of tuple values read (1, 2 or 4, or 27). */
    static int readTuple (BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(':');
        if (colon == -1) throw new GdxRuntimeException("Invalid line: " + line);
        int i = 0, lastMatch = colon + 1;
        for (i = 0; i < 36; i++) {
            int comma = line.indexOf(',', lastMatch);
            if (comma == -1) break;
            tuple[i] = line.substring(lastMatch, comma).trim();
            lastMatch = comma + 1;
        }
        tuple[i] = line.substring(lastMatch).trim();
        return i + 1;
    }

}
