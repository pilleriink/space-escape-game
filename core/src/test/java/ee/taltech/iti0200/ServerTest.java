package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0200.SpaceEscape;
import ee.taltech.iti0200.server.GameServer;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.server.packets.*;
import org.junit.Assert;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public class ServerTest {

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
    public void ability() {
        Ability ability = new Ability();
        ability.texture = "texture";
        ability.id = "id";
        ability.x = X_AXIS;
        ability.y = Y_AXIS;
        Assert.assertSame("texture", ability.texture);
        Assert.assertSame("id", ability.id);
        Assert.assertEquals(X_AXIS, ability.x, 0.1);
        Assert.assertEquals(Y_AXIS, ability.y, 0.1);
    }

    @org.junit.Test
    public void death() {
        Death death = new Death();
        death.id = "ii";
        Assert.assertSame("ii", death.id);
    }

    @org.junit.Test
    public void drone() {
        Drone drone = new Drone();
        drone.id = "id";
        drone.x = X_AXIS;
        drone.y = Y_AXIS;
        Assert.assertEquals(X_AXIS, drone.x, 0.1);
        Assert.assertEquals(Y_AXIS, drone.y, 0.1);
        Assert.assertSame("id", drone.id);
    }

    @org.junit.Test
    public void enemy() {
        Enemy enemy = new Enemy();
        enemy.x = X_AXIS;
        enemy.y = Y_AXIS;
        enemy.lives = 10;
        enemy.id = "id";
        enemy.enemyType = "bad";
        Assert.assertEquals(X_AXIS, enemy.x, 0.1);
        Assert.assertEquals(Y_AXIS, enemy.y, 0.1);
        Assert.assertEquals(10, enemy.lives, 0.1);
        Assert.assertSame("id", enemy.id);
        Assert.assertSame("bad", enemy.enemyType);
    }

    @org.junit.Test
    public void gun() {
        Gun gun = new Gun();
        gun.x = X_AXIS;
        gun.id = "id";
        gun.gun = "gun";
        Assert.assertSame("id", gun.id);
        Assert.assertSame("gun", gun.gun);
        Assert.assertEquals(X_AXIS, gun.x, 0.1);
    }

    @org.junit.Test
    public void livesLost() {
        LivesLost livesLost = new LivesLost();
        livesLost.lives = 10;
        livesLost.id = "id";
        Assert.assertEquals(10, livesLost.lives, 0.1);
        Assert.assertSame("id", livesLost.id);
    }

    @org.junit.Test
    public void Move() {
        Move move = new Move();
        move.x = X_AXIS;
        move.y = Y_AXIS;
        move.id = "id";
        move.texture = "texture";
        Assert.assertEquals(X_AXIS, move.x, 0.1);
        Assert.assertEquals(Y_AXIS, move.y, 0.1);
        Assert.assertSame("id", move.id);
        Assert.assertSame("texture", move.texture);
    }

    @org.junit.Test
    public void moveEnemy() {
        MoveEnemy moveEnemy = new MoveEnemy();
        moveEnemy.id = "id";
        moveEnemy.x = X_AXIS;
        moveEnemy.y = Y_AXIS;
        Assert.assertSame("id", moveEnemy.id);
        Assert.assertEquals(X_AXIS, moveEnemy.x, 0.1);
        Assert.assertEquals(Y_AXIS, moveEnemy.y, 0.1);
    }

    @org.junit.Test
    public void player() {
        Player player = new Player();
        player.id = "id";
        player.x = X_AXIS;
        player.y = Y_AXIS;
        player.lives= 10;
        player.playerType = "playerType";
        Assert.assertEquals(X_AXIS, player.x, 0.1);
        Assert.assertEquals(Y_AXIS, player.y, 0.1);
        Assert.assertEquals(10, player.lives, 0.1);
        Assert.assertSame("id", player.id);
        Assert.assertSame("playerType", player.playerType);
    }

    @org.junit.Test
    public void register() {
        Register register = new Register();
        register.id = "id";
        register.playerType = "playerType";
        Assert.assertSame("id", register.id);
        Assert.assertSame("playerType", register.playerType);
    }

    @org.junit.Test
    public void smallDrone() {
        SmallDrone smallDrone = new SmallDrone();
        smallDrone.id = "id";
        smallDrone.texture = "texture";
        smallDrone.x = X_AXIS;
        smallDrone.y = Y_AXIS;
        Assert.assertSame("id", smallDrone.id);
        Assert.assertSame("texture", smallDrone.texture);
        Assert.assertEquals(X_AXIS, smallDrone.x, 0.1);
        Assert.assertEquals(Y_AXIS, smallDrone.y, 0.1);
    }

    @org.junit.Test
    public void gameServer() throws IOException {
        GameServer gameServer = new GameServer();
        Assert.assertNotNull(gameServer.firstConnection);
        Assert.assertNotNull(gameServer.players);
        Assert.assertNotNull(gameServer.enemies);
        Assert.assertEquals(12, gameServer.enemies.size());


    }
}
