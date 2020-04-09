package ee.taltech.iti0200.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ee.taltech.iti0200.SpaceEscape;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Space Escape";
		config.width = 800;
		config.height = 480;
		config.fullscreen = true;
		new LwjglApplication(new SpaceEscape(), config);
	}
}
