package com.guxuede.game.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.GdxSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.StageWorld;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class DefaultLightManager implements LightManager {

    private StageWorld world;

    private Stage stage;
    private SpriteBatch batch;
    private FrameBuffer lightBuffer;

    private TextureRegion lightBufferRegion;
    private TextureRegion lightRegion;
    private Color ambiance;
    private Array<Light> lights = new Array<Light>();


    public DefaultLightManager(StageWorld world) {
        this.world = world;

        this.stage = world.getStage();

        Texture lightTex = ResourceManager.getTexture("light");
        lightRegion = new TextureRegion(lightTex);
        ambiance = new Color(0.3f, 0.38f, 0.4f, 1);
        createLight(0,0);
        createLight(100,100);
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }


    private void createLight(float x, float y) {
        lights.add(new Light(x, y, /*MathUtils.randomBoolean()?*/lightRegion/* : coneLightRegion*/));
    }


    @Override
    public void onMapLoad(TiledMap map) {

    }

    @Override
    public void render() {
        lightBuffer.begin();
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        Gdx.gl.glClearColor(ambiance.r, ambiance.g, ambiance.b, ambiance.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawEntryLight();
        batch.end();
        lightBuffer.end();
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.setColor(Color.WHITE);
        batch.begin();
        batch.draw(lightBufferRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void drawEntryLight(){
        final Vector2 tVector2 = TempObjects.temp1Vector2;
        for(Actor actor : stage.getActors()){
            if(actor.isVisible() && actor instanceof AnimationEntity){
                AnimationEntity entity = (AnimationEntity) actor;
                if(!entity.isInScreen)continue;
                batch.setColor(entity.primaryColor);
                tVector2.set(entity.getCenterX(),entity.getCenterY());
                stage.stageToScreenCoordinates(tVector2);
                //(4.75f + 0.25f * (float)Math.sin(zAngle) + .2f* MathUtils.random())
                float visualRadius = entity.visualRadius;//+MathUtils.random(entity.visualRadius*0.1f);
                batch.draw(lightRegion ,tVector2.x-visualRadius/2, Gdx.graphics.getHeight()-tVector2.y-visualRadius/2, visualRadius, visualRadius);
            }
        }
    }

    @Override
    public void resize (int width, int height) {
        // Fakedlight system (alpha blending)
        // if lightBuffer was created before, dispose, we recreate a new one
        if (lightBuffer!=null)
            lightBuffer.dispose();
        if(batch!=null){
            batch.dispose();
        }
        batch = new SpriteBatch();
        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), 0, 0, width, height);
        lightBufferRegion.flip(false, true);
    }


    public void dispose () {
        lightBuffer.dispose();
        lightRegion.getTexture().dispose();
    }


    private static class Light {
        public float x = 0;
        public float y = 0;
        public float width = 16;
        public float height = 16;
        public float rotation;
        public Color color = new Color(0.9f, 0.4f, 0f, 1f);
        public TextureRegion region;

        public Light (float x, float y, TextureRegion region) {
            this.x = x;
            this.y = y;
            this.region = region;

            width = height = MathUtils.random(100, 300);
            color.set(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
            rotation = MathUtils.random(360);
        }
    }

    @Override
    public void act(float delta) {

    }
}
