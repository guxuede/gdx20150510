package com.guxuede.game.libgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;

import static com.badlogic.gdx.graphics.g2d.Batch.*;
import static com.badlogic.gdx.graphics.g2d.Batch.U2;
import static com.badlogic.gdx.graphics.g2d.Batch.U3;

public class MyOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer{

	
	private static final int[] LAYER1= new int[]{0}; 
	private static final int[] LAYER2= new int[]{1}; 
	private static final int[] LAYER3= new int[]{2}; 
	

	public MyOrthogonalTiledMapRenderer (TiledMap map) {
		super(map);
	}

	public MyOrthogonalTiledMapRenderer (TiledMap map, Batch batch) {
		super(map, batch);
	}

	public MyOrthogonalTiledMapRenderer (TiledMap map, float unitScale) {
		super(map, unitScale);
	}

	public MyOrthogonalTiledMapRenderer (TiledMap map, float unitScale, Batch batch) {
		super(map, unitScale, batch);
	}


	public void renderLayer1(){
		render(LAYER1);
	}
	
	public void renderLayer2(){
		render(LAYER2);
	}
	
	public void renderLayer3(){
		render(LAYER3);
	}

    @Override
    public void renderObject(MapObject object) {
        if(object instanceof TiledMapTileMapObject){
            TiledMapTileMapObject cell = (TiledMapTileMapObject) object;
            final TiledMapTile tile = cell.getTile();
            if (tile != null) {
                final Color cellColor = cell.getColor();
                final float color = Color.toFloatBits(cellColor.r, cellColor.g, cellColor.b, cellColor.a * cell.getOpacity());

                final float x = cell.getX();
                final float y = cell.getY();
                final boolean flipX = cell.isFlipHorizontally();//.getFlipHorizontally();
                final boolean flipY = cell.isFlipVertically();//.getFlipVertically();
                final int rotations = (int) cell.getRotation();//.getRotation();

                TextureRegion region = tile.getTextureRegion();

                float x1 = x + tile.getOffsetX() * unitScale;
                float y1 = y + tile.getOffsetY() * unitScale;
                float x2 = x1 + region.getRegionWidth() * unitScale;
                float y2 = y1 + region.getRegionHeight() * unitScale;

                float u1 = region.getU();
                float v1 = region.getV2();
                float u2 = region.getU2();
                float v2 = region.getV();

                vertices[X1] = x1;
                vertices[Y1] = y1;
                vertices[C1] = color;
                vertices[U1] = u1;
                vertices[V1] = v1;

                vertices[X2] = x1;
                vertices[Y2] = y2;
                vertices[C2] = color;
                vertices[U2] = u1;
                vertices[V2] = v2;

                vertices[X3] = x2;
                vertices[Y3] = y2;
                vertices[C3] = color;
                vertices[U3] = u2;
                vertices[V3] = v2;

                vertices[X4] = x2;
                vertices[Y4] = y1;
                vertices[C4] = color;
                vertices[U4] = u2;
                vertices[V4] = v1;

                if (flipX) {
                    float temp = vertices[U1];
                    vertices[U1] = vertices[U3];
                    vertices[U3] = temp;
                    temp = vertices[U2];
                    vertices[U2] = vertices[U4];
                    vertices[U4] = temp;
                }
                if (flipY) {
                    float temp = vertices[V1];
                    vertices[V1] = vertices[V3];
                    vertices[V3] = temp;
                    temp = vertices[V2];
                    vertices[V2] = vertices[V4];
                    vertices[V4] = temp;
                }
                if (rotations != 0) {
                    switch (rotations) {
                        case TiledMapTileLayer.Cell.ROTATE_90: {
                            float tempV = vertices[V1];
                            vertices[V1] = vertices[V2];
                            vertices[V2] = vertices[V3];
                            vertices[V3] = vertices[V4];
                            vertices[V4] = tempV;

                            float tempU = vertices[U1];
                            vertices[U1] = vertices[U2];
                            vertices[U2] = vertices[U3];
                            vertices[U3] = vertices[U4];
                            vertices[U4] = tempU;
                            break;
                        }
                        case TiledMapTileLayer.Cell.ROTATE_180: {
                            float tempU = vertices[U1];
                            vertices[U1] = vertices[U3];
                            vertices[U3] = tempU;
                            tempU = vertices[U2];
                            vertices[U2] = vertices[U4];
                            vertices[U4] = tempU;
                            float tempV = vertices[V1];
                            vertices[V1] = vertices[V3];
                            vertices[V3] = tempV;
                            tempV = vertices[V2];
                            vertices[V2] = vertices[V4];
                            vertices[V4] = tempV;
                            break;
                        }
                        case TiledMapTileLayer.Cell.ROTATE_270: {
                            float tempV = vertices[V1];
                            vertices[V1] = vertices[V4];
                            vertices[V4] = vertices[V3];
                            vertices[V3] = vertices[V2];
                            vertices[V2] = tempV;

                            float tempU = vertices[U1];
                            vertices[U1] = vertices[U4];
                            vertices[U4] = vertices[U3];
                            vertices[U3] = vertices[U2];
                            vertices[U2] = tempU;
                            break;
                        }
                    }
                }
                batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
            }
        }
    }
}
