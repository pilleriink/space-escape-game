package ee.taltech.iti0200;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0200.SpaceEscape;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpaceEscapeTest {

    public static final float X_AXIS = 2;
    public static final float Y_AXIS = 1;

    @org.junit.Before
    public void BeforeEach() {
    }

    @org.junit.Test
    public void create() {
        Player0 player0 = mock(Player0.class);

        when(player0.getId()).thenReturn("id");

        Assert.assertEquals("id", player0.getId());

    }



    @Test
    public void testCharacters()  {

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
        Assert.assertEquals(8, gameServer.enemies.size());


    }


    @org.junit.Test
    public void entityTypeValues() {
        EntityType entityPlayer = EntityType.PLAYER;
        Assert.assertSame("player", entityPlayer.getId());
        Assert.assertSame(14, entityPlayer.getWidth());
        Assert.assertSame(24, entityPlayer.getHeight());
        Assert.assertEquals(40, entityPlayer.getWeight(), 0.1);
        EntityType entityEnemy0 = EntityType.ENEMY0;
        Assert.assertSame("enemy0", entityEnemy0.getId());
        Assert.assertSame(14, entityEnemy0.getWidth());
        Assert.assertSame(32, entityEnemy0.getHeight());
        Assert.assertEquals(40, entityEnemy0.getWeight(), 0.1);
        EntityType entityEnemy1 = EntityType.ENEMY1;
        Assert.assertSame("enemy1", entityEnemy1.getId());
        Assert.assertSame(60, entityEnemy1.getWidth());
        Assert.assertSame(54, entityEnemy1.getHeight());
        Assert.assertEquals(40, entityEnemy1.getWeight(), 0.1);
    }

    @org.junit.Test
    public void tileType() {
        TileType tileType = TileType.BLUE_BACKGROUND;
        Assert.assertEquals(12, tileType.getId());
        Assert.assertEquals(0, tileType.getDamage(), 0.1);
        Assert.assertEquals("Blue background", tileType.getName());
        Assert.assertFalse(tileType.isCollision());
        Assert.assertEquals(tileType, TileType.getTileTypeById(12));
    }

    @org.junit.Test
    public void gameMapFunctionality() {
//        SpaceEscape escape = mock(SpaceEscape.class);
//        EndScreen endScreen = mock(EndScreen.class);
//        GameScreen gameScreen = mock(GameScreen.class);
//        SpriteBatch batch = null;
//        when(endScreen.batch).thenReturn(null);

//        Assert.assertNull(endScreen.batch);
//        this.getClass().getClassLoader().getResourceAsStream("character0.png");
//        Texture texture = new Texture("character0.png");


//        TiledGameMap tiledGameMap = new TiledGameMap();
    }

    @org.junit.Test
    public void entityValues() {
        GameMap map = mock(GameMap.class);
        Entity entity = new Entity(0, 0, EntityType.PLAYER, map, 10, "id") {
            @Override
            public void render(SpriteBatch batch) {

            }
        };
        Assert.assertFalse(entity.isRight());
        Assert.assertFalse(entity.isMoving());
        Assert.assertFalse(entity.isShoot());
        Assert.assertEquals(10, entity.getTotalHealth(), 0.1);
        Assert.assertSame("id", entity.getId());
        entity.setPosX(10);
        Assert.assertEquals(10, entity.getX(), 0.1);
        Assert.assertEquals(10, entity.getPos().x, 0.1);
        entity.setPosY(5);
        Assert.assertEquals(5, entity.getY(), 0.1);
        Assert.assertEquals(5, entity.getPos().y, 0.1);

        Assert.assertEquals(10, entity.getLives(), 0.1);
        entity.setLives(5);
        Assert.assertEquals(5, entity.getLives(), 0.1);
        Assert.assertEquals(EntityType.PLAYER, entity.getType());
        Assert.assertFalse(entity.isGrounded());

        // Update doesnt affect pos.x
        entity.update(1, 9.8f);
        Assert.assertEquals(10, entity.getX(), 0.1);

        // move X changes x values
        entity.moveX(10);
        Assert.assertEquals(20, entity.getX(), 0.1);
    }

    @org.junit.Test
    public void enemyType() {
        EnemyType enemy0 = EnemyType.ENEMY0;
        Assert.assertSame("enemy0", enemy0.getId());
        List<String> imageList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                imageList.add("enemy0/enemy0_moving_" + i + ".png");
            }
        }
        Assert.assertEquals(imageList, enemy0.getMovingString());
    }

}
