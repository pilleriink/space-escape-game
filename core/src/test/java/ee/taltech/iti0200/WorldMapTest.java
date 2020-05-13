package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.entities.*;
import ee.taltech.iti0200.entities.Enemy0;
import ee.taltech.iti0200.entities.Entity;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TileType;
import org.junit.Assert;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class WorldMapTest {

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
    public void testGameMap() {
        GameMap testMap = new GameMap() {
            @Override
            public void dispose() {
            }

            @Override
            public TileType getTileTypeByCoordinate(int layer, int col, int row) {
                return TileType.WATER_FULL;
            }

            @Override
            public int getWidth() {
                return 10;
            }

            @Override
            public int getHeight() {
                return 5;
            }

            @Override
            public int getLayers() {
                return 1;
            }
        };
        ArrayList<Entity> entities = new ArrayList<>();
        Player0 player0 = new Player0(90, 10, map, 10, 100, entities, PlayerType.PLAYER0, client, "player0");
        Enemy0 enemy0 = new Enemy0(5, 10, map, 10, 100, entities, "enemy0", client);
        entities.add(player0);
        entities.add(enemy0);
        enemy0.entities = entities;
        testMap.entities = new ArrayList<>();
        testMap.addEntity(player0);
        testMap.addEntity(enemy0);
        testMap.addPlayer(PlayerType.PLAYER0, client, "player0");
        testMap.addPlayer(PlayerType.PLAYER1, client, "player1");
        testMap.addPlayer(PlayerType.PLAYER2, client, "player2");
        testMap.addPlayer(PlayerType.PLAYER3, client, "player3");
        Assert.assertEquals(6, testMap.getEntities().size());

        // entities update doesnt throw errors
        testMap.update(1);

        Assert.assertFalse(testMap.doesRectCollideMap(1, 1, 10, 10));
        Assert.assertEquals(player0, testMap.getPlayer());
        Assert.assertEquals(TileType.WATER_FULL, testMap.getTileTypeByLocation(1, 1, 1));

        // TileType tests
        TileType testTile = TileType.WATER_FULL;
        Assert.assertEquals(91, testTile.getId());
        Assert.assertFalse(testTile.isCollision());
        Assert.assertEquals("", testTile.getName());
        Assert.assertEquals(0, testTile.getDamage(), 0.1);


        Assert.assertEquals(TileType.WATER_FULL, TileType.getTileTypeById(91));
    }

}
