package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0200.SpaceEscape;
import ee.taltech.iti0200.entities.Enemy0;
import ee.taltech.iti0200.entities.Enemy1;
import ee.taltech.iti0200.entities.EnemyType;
import ee.taltech.iti0200.entities.Entity;
import ee.taltech.iti0200.entities.EntityType;
import ee.taltech.iti0200.entities.Player0;
import ee.taltech.iti0200.entities.PlayerType;
import ee.taltech.iti0200.server.GameServer;
import ee.taltech.iti0200.server.packets.Ability;
import ee.taltech.iti0200.server.packets.Death;
import ee.taltech.iti0200.server.packets.Drone;
import ee.taltech.iti0200.server.packets.Enemy;
import ee.taltech.iti0200.server.packets.Gun;
import ee.taltech.iti0200.server.packets.LivesLost;
import ee.taltech.iti0200.server.packets.Move;
import ee.taltech.iti0200.server.packets.MoveEnemy;
import ee.taltech.iti0200.server.packets.Player;
import ee.taltech.iti0200.server.packets.Register;
import ee.taltech.iti0200.server.packets.SmallDrone;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TileType;
import ee.taltech.iti0200.world.TiledGameMap;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpaceEscapeTest {

    public static final float X_AXIS = 2;
    public static final float Y_AXIS = 1;

    HeadlessApplicationConfiguration conf;
    SpaceEscape escape;
    GameMap map;
    Client client;
    SpriteBatch batch;

    @org.junit.Before
    public void BeforeEach() {
        conf = new HeadlessApplicationConfiguration();
        escape = mock(SpaceEscape.class);
        new HeadlessApplication(escape, conf);
        Gdx.gl = mock(GL20.class);
        map = mock(GameMap.class);
        client = new Client();
        batch = mock(SpriteBatch.class);
    }

    @org.junit.Test
    public void testStartScreen() {
        StartScreen startScreen = new StartScreen(escape);
        // override methods
        startScreen.show();
        startScreen.resize(1, 2);
        startScreen.pause();
        startScreen.resume();
        startScreen.hide();
        startScreen.dispose();
    }

    @org.junit.Test
    public void testMenuScreen() {
        // doesnt throw errors
        MenuScreen menuScreen = new MenuScreen(escape, false);
        // override methods
        menuScreen.show();
        menuScreen.resize(1, 2);
        menuScreen.pause();
        menuScreen.resume();
        menuScreen.hide();
        menuScreen.dispose();
    }

    @org.junit.Test
    public void testGameScreen() {
        TiledGameMap gameMap = mock(TiledGameMap.class);
        GameScreen gameScreen = mock(GameScreen.class);
    }

}
