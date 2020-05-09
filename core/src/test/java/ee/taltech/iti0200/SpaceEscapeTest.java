package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.entities.*;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TileType;
import org.junit.Assert;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

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
        MenuScreen menuScreen = new MenuScreen(escape);
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
//        TiledGameMap gameMap = mock(TiledGameMap.class);
//        GameScreen gameScreen = mock(GameScreen.class);
        SpaceEscape escape = new SpaceEscape();
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        conf.width = 1;
        conf.height = 1;
        GameScreen gameScreen = new GameScreen(escape, PlayerType.PLAYER0, client);
        // cooldowns and textures are assigned correctly
        Assert.assertEquals(4, gameScreen.xCooldownTime);
        Assert.assertEquals(3, gameScreen.cCooldownTime);
        Assert.assertEquals(5, gameScreen.vCooldownTime);
        gameScreen.initializePlayerType(PlayerType.PLAYER1);
        Assert.assertEquals(4, gameScreen.xCooldownTime);
        Assert.assertEquals(8, gameScreen.cCooldownTime);
        gameScreen.initializePlayerType(PlayerType.PLAYER2);
        Assert.assertEquals(4, gameScreen.xCooldownTime);
        Assert.assertEquals(6, gameScreen.cCooldownTime);
        Assert.assertEquals(5, gameScreen.vCooldownTime);
        gameScreen.initializePlayerType(PlayerType.PLAYER3);
        Assert.assertEquals(4, gameScreen.xCooldownTime);
        Assert.assertEquals(4, gameScreen.cCooldownTime);
        Assert.assertEquals(5, gameScreen.vCooldownTime);


        // getPlayerType and getEnemyType returns correct player type based on texture prefix
        Assert.assertEquals(PlayerType.PLAYER0, gameScreen.getPlayerType("character0"));
        Assert.assertEquals(PlayerType.PLAYER1, gameScreen.getPlayerType("character1"));
        Assert.assertEquals(PlayerType.PLAYER2, gameScreen.getPlayerType("character2"));
        Assert.assertEquals(PlayerType.PLAYER3, gameScreen.getPlayerType("character3"));
        Assert.assertEquals(EnemyType.ENEMY0, gameScreen.getEnemyType("enemy0"));
        Assert.assertEquals(EnemyType.ENEMY1, gameScreen.getEnemyType("enemy1"));
        gameScreen.stage = mock(Stage.class);
        GameMap mapp = new GameMap() {
            @Override
            public void dispose() {

            }

            @Override
            public TileType getTileTypeByCoordinate(int layer, int col, int row) {
                return null;
            }

            @Override
            public int getWidth() {
                return 0;
            }

            @Override
            public int getHeight() {
                return 0;
            }

            @Override
            public int getLayers() {
                return 0;
            }
        };

        // override methods
        gameScreen.resize(1, 1);
        gameScreen.show();
        gameScreen.hide();
        gameScreen.pause();
        gameScreen.resume();
        gameScreen.dispose();
    }

}
