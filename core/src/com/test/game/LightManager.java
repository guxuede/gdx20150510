package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.ResourceManager;

/**
 * Created by guxuede on 2016/9/6 .
 */
public class LightManager {

    private Stage stage;
    private SpriteBatch batch;
    private FrameBuffer lightBuffer;

    private TextureRegion lightBufferRegion;
    private TextureRegion lightRegion;
    private TextureRegion coneLightRegion;
    private Color ambiance;

    Array<Light> lights = new Array<Light>();

    public LightManager(Stage stage){
        this.stage = stage;
        this.batch = new SpriteBatch();
        Texture lightTex =ResourceManager.getTexture("light");
        lightRegion = new TextureRegion(lightTex);
        coneLightRegion = new TextureRegion(lightTex, 0, 0, 64, 64);
        coneLightRegion.flip(true, false);
        ambiance = new Color(0.3f, 0.38f, 0.4f, 1);
        createLight(0,0);
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }
    private void createLight(float x, float y) {
        lights.add(new Light(x, y, /*MathUtils.randomBoolean()?*/lightRegion/* : coneLightRegion*/));
    }

    public void render (Camera gameCamera) {
        //batch.setProjectionMatrix(gameCamera.projection);
        // start rendering to the lightBuffer
        lightBuffer.begin();
        // setup the right blending
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        // set the ambient color values, this is the "global" light of your scene
        // imagine it being the sun.  Usually the alpha value is just 1, and you change the darkness/brightness with the Red, Green and Blue values for best effect
        Gdx.gl.glClearColor(ambiance.r, ambiance.g, ambiance.b, ambiance.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // start rendering the lights to our spriteBatch
        batch.begin();
//        for (Light light : lights) {
//            batch.setColor(light.color);
//            // note: this is very dumb
//            if (light.region == coneLightRegion) {
//                batch.draw(light.region, light.x, light.y, 0, 0, light.width, light.height, 1, 1, light.rotation);
//            } else {
//                batch.draw(light.region, light.x, light.y, light.width, light.height);
//            }
//        }
        drawEntryLight(gameCamera);
        batch.end();
        lightBuffer.end();
        //batch.setProjectionMatrix(gameCamera.combined);
        // now we render the lightBuffer to the default "frame buffer"
        // with the right blending !
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        // reset the batch color or we will get last light color tint
        batch.setColor(Color.WHITE);
        batch.begin();
        //batch.setProjectionMatrix(gameCamera.combined);
        batch.draw(lightBufferRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        // draw fbo without fancy blending, for debug
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    Vector2 tVector2 = new Vector2();
    public void drawEntryLight(Camera gameCamera){
        for(Actor actor : stage.getActors()){
            if(actor instanceof AnimationEntity){
                AnimationEntity entity = (AnimationEntity) actor;
                batch.setColor(entity.primaryColor);
                tVector2.set(entity.getCenterX(),entity.getCenterY());
                stage.stageToScreenCoordinates(tVector2);
                //(4.75f + 0.25f * (float)Math.sin(zAngle) + .2f* MathUtils.random())
                float visualRadius = entity.visualRadius;//+MathUtils.random(entity.visualRadius*0.1f);
                batch.draw(lightRegion ,tVector2.x-visualRadius/2, Gdx.graphics.getHeight()-tVector2.y-visualRadius/2, visualRadius, visualRadius);
            }
        }
    }

    public void resize (int width, int height) {
        // Fakedlight system (alpha blending)
        // if lightBuffer was created before, dispose, we recreate a new one
        if (lightBuffer!=null)
            lightBuffer.dispose();
        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, MathUtils.nextPowerOfTwo(width), MathUtils.nextPowerOfTwo(height), false);
        lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), 0, 0, MathUtils.nextPowerOfTwo(width), MathUtils.nextPowerOfTwo(height));
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
}
