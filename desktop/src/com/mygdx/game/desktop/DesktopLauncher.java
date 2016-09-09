package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.test.game.TitleMapGame;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
        config.useGL30 = true;
		new LwjglApplication(new TitleMapGame(), config);
	}
}
