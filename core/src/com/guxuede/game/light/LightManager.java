package com.guxuede.game.light;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by guxuede on 2016/9/10 .
 */
public interface LightManager {

    public void onMapLoad(TiledMap map);

    public void render ();

    public void act(float delta);
}
