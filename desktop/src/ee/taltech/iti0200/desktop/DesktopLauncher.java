package ee.taltech.iti0200.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0200.SpaceEscape;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Space Escape";
		config.width = 800;
		config.height = 480;
		config.fullscreen = true;
		new LwjglApplication(new SpaceEscape(), config);

	}

}
