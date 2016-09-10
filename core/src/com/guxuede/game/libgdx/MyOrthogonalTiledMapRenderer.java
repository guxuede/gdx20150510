package com.guxuede.game.libgdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;

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
}
