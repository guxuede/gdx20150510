package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.test.game.MutilStageGame;
import com.test.game.TitleMapGame;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //config.width = 1000; config.height = 1000;
        config.vSyncEnabled = true;
        //config.useGL30 = false;//使用这个后fps骤降？
		new LwjglApplication(new MutilStageGame(), config);
        //Gdx.app.setLogLevel();
	}
}
