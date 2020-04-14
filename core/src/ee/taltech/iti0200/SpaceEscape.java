package ee.taltech.iti0200;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.server.packets.*;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TiledGameMap;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class SpaceEscape extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	GameMap gameMap;
	Client client;

	public SpaceEscape() {
		client = new Client();
		client.start();
		try {
			client.connect(5000, InetAddress.getLocalHost(), 54556, 54778);
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.getKryo().register(Register.class);
		client.getKryo().register(Move.class);
		client.getKryo().register(LivesLost.class);
		client.getKryo().register(Player.class);
		client.getKryo().register(ArrayList.class);
		client.getKryo().register(Gun.class);
		client.getKryo().register(Enemy.class);
		client.getKryo().register(MoveEnemy.class);

	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		gameMap = new TiledGameMap();
		this.setScreen(new MenuScreen(this, client));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		gameMap.dispose();
	}
}
