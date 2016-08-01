package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.spine.test.*;
import com.test.game.MyGdxGame;
import com.test.game.MyMapLayerGame;
import com.test.game.TitleMapGame;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new TitleMapGame(), config);
        //new LwjglApplication(new Box2DExample(),config);
        //new LwjglApplication(new MixTest(),config);

//        {
//            if (args.length == 0)
//                args = new String[] {"spineboy-old/spineboy-old", "walk"};
//            else if (args.length == 1) //
//                args = new String[] {args[0], null};
//
//            new LwjglApplication(new NormalMapTest(args[0], args[1]));
//        }

        //new LwjglApplication(new SimpleTest1());
       // new LwjglApplication(new SimpleTest2());
        //new LwjglApplication(new SimpleTest3());
        new LwjglApplication(new SkeletonAttachmentTest(),config);
	}
}
