package com.guxuede.game.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.guxuede.game.StageWorld;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationDoor;

/**
 * Created by guxuede on 2016/9/11 .
 */
public class MapManager {

    public StageWorld world;

    public MapManager(StageWorld world) {
        this.world = world;
    }

    public void onMapLoad(TiledMap map){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        float unitScale = 1;
        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();

        final float layerTileWidth = layer.getTileWidth() * unitScale;
        final float layerTileHeight = layer.getTileHeight() * unitScale;

        final int col1 = 0;
        final int col2 = layerWidth;

        final int row1 = 0;
        final int row2 = layerHeight;

        float y = row2 * layerTileHeight;
        float xStart = col1 * layerTileWidth;

        for (int row = row2; row >= row1; row--) {
            float x = xStart;
            for (int col = col1; col < col2; col++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    x += layerTileWidth;
                    continue;
                }
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    MapProperties properties = tile.getProperties();
                    float x1 = x + tile.getOffsetX() * unitScale;
                    float y1 = y + tile.getOffsetY() * unitScale;
                    if ("true".equals(properties.get("isDoor"))) {
                        String targetMapName = properties.get("targetMapName",String.class);
                        float doorTargetMapPosX = Float.parseFloat(properties.get("doorTargetMapPosX",String.class));
                        float doorTargetMapPosY = Float.parseFloat(properties.get("doorTargetMapPosY",String.class));
                        AnimationDoor animationDoor = ActorFactory.creatDoor("light1",world);
                        animationDoor.stageName = targetMapName;
                        animationDoor.point = new Vector2(doorTargetMapPosX,doorTargetMapPosY);
                        animationDoor.setPosition(x1,y1);
                        world.getStage().addActor(animationDoor);
                    }
                }
                x += layerTileWidth;
            }
            y -= layerTileHeight;
        }
    }
}
