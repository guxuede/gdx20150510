package com.guxuede.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by guxuede on 2016/9/25 .
 */
public class Shaders {

    public static class Highlight {
        private static String vertexShader =
                "void main()" +
                        "{" +
                        "   gl_FrontColor = gl_Color;" +
                        "   gl_Position = ftransform();" +
                        "}";
        private static String fragmentShader =
                "void main()" +
                        "{" +
                        "   gl_FragColor = gl_Color;" +
                        "}";

        public static final ShaderProgram SHADER = new ShaderProgram(vertexShader, fragmentShader);
    }

    static ShaderProgram shaderOutline;

    public static ShaderProgram getShaderOutline() {
        if(shaderOutline == null){
            loadShader();
        }
        return shaderOutline;
    }

    public static void loadShader() {
        String vertexShader;
        String fragmentShader;
        vertexShader = Gdx.files.internal("shaders/df_vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("shaders/outline_border_fragment.glsl").readString();
        shaderOutline = new ShaderProgram(vertexShader, fragmentShader);
        if (!shaderOutline.isCompiled()) throw new GdxRuntimeException("Couldn't compile shader: " + shaderOutline.getLog());

        //                ShaderProgram shaderOutline = Shaders.getShaderOutline();
//                batch.end();
//                shaderOutline.begin();
//                shaderOutline.setUniformf("u_viewportInverse", new Vector2(1f / sprite.getWidth(), 1f / sprite.getHeight()));
//                shaderOutline.setUniformf("u_offset", 1);
//                shaderOutline.setUniformf("u_step", Math.min(1f, sprite.getWidth() / 70f));
//                shaderOutline.setUniformf("u_color", new Vector3(1, 0, 0));
//                batch.setShader(shaderOutline);
//                batch.begin();
//                sprite.draw(batch, parentAlpha, getRotation(), getScaleX(), getScaleY(), getColor());
//                batch.end();
//                batch.setShader(null);
//                shaderOutline.end();
//                batch.begin();
    }
}
