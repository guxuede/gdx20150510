package com.mygdx.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Json;

import java.io.File;
import java.io.FileReader;

/**
 * Created by guxuede on 2017/4/13 .
 */
public class TexturePackerUtil extends TexturePacker {

    public TexturePackerUtil(File rootDir, Settings settings) {
        super(rootDir, settings);
    }

    public TexturePackerUtil(Settings settings) {
        super(settings);
    }

    static public void main (String[] args) throws Exception {
        args= new String[]{"G:\\游戏素材\\pic079_新浪提供图标589个","G:\\游戏素材\\pic079_新浪提供图标589个","enqiments"};
        Settings settings = null;
        String input = null, output = null, packFileName = "pack.atlas";

        switch (args.length) {
            case 4:
                settings = new Json().fromJson(Settings.class, new FileReader(args[3]));
            case 3:
                packFileName = args[2];
            case 2:
                output = args[1];
            case 1:
                input = args[0];
                break;
            default:
                System.out.println("Usage: inputDir [outputDir] [packFileName] [settingsFileName]");
                System.exit(0);
        }

        if (output == null) {
            File inputFile = new File(input);
            output = new File(inputFile.getParentFile(), inputFile.getName() + "-packed").getAbsolutePath();
        }
        if (settings == null) settings = new Settings();

        process(settings, input, output, packFileName);
    }
}
