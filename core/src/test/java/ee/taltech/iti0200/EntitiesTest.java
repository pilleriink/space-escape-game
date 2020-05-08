package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.entities.Enemy0;
import ee.taltech.iti0200.entities.Enemy1;
import ee.taltech.iti0200.entities.EnemyType;
import ee.taltech.iti0200.entities.Entity;
import ee.taltech.iti0200.entities.EntityType;
import ee.taltech.iti0200.entities.OtherPlayer;
import ee.taltech.iti0200.entities.Player0;
import ee.taltech.iti0200.entities.Player1;
import ee.taltech.iti0200.entities.Player2;
import ee.taltech.iti0200.entities.Player3;
import ee.taltech.iti0200.entities.PlayerType;
import ee.taltech.iti0200.world.GameMap;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class EntitiesTest {

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
    public void testEnemy0() {
        ArrayList<Entity> entities = new ArrayList<>();
        Player0 player0 = new Player0(90, 10, map, 10, 100, entities, PlayerType.PLAYER0, client, "player0");
        Enemy0 enemy0 = new Enemy0(5, 10, map, 10, 100, entities, "enemy0", client);
        entities.add(player0);
        entities.add(enemy0);
        enemy0.entities = entities;

        Assert.assertEquals("enemy0", enemy0.getId());
        // is moving
        float oldX = enemy0.getX();
        enemy0.moveRight((float) 0.1);
        Assert.assertNotEquals(oldX, enemy0.getX());
        enemy0.moveLeft((float) 0.1);
        Assert.assertEquals(oldX, enemy0.getX(), 0.1);

        // shoot does dmg !!!
        float oldPlayerHPRight = player0.getLives();
        // Player on the right
        enemy0.shoot();
        Assert.assertEquals(oldPlayerHPRight, player0.getLives(), 0.1);
        enemy0.moveRight((float) 0.01);
        enemy0.shoot();
        Assert.assertNotEquals(oldPlayerHPRight, player0.getLives());
        // Player on the left
        player0.setPosX(-90);
        float oldPlayerHPLeft = player0.getLives();
        enemy0.shoot();
        Assert.assertEquals(oldPlayerHPLeft, player0.getLives(), 0.1);
        enemy0.moveLeft((float) 0.01);
        enemy0.shoot();
        Assert.assertNotEquals(oldPlayerHPLeft, player0.getLives());
        // Player out of range
        player0.setPosX(-100000);
        enemy0.shoot();
        Assert.assertEquals(8, player0.getLives(), 0.1);
        enemy0.moveRight((float) 0.01);
        player0.setPosX(100000);
        enemy0.shoot();
        Assert.assertEquals(8, player0.getLives(), 0.1);


        // Enemy follows player

        // player out of range
        enemy0.setPosX(5);
        enemy0.follow((float) 0.01);
        Assert.assertEquals(5, enemy0.getX(), 0.1);
        // player on the right
        player0.setPosX(100);
        enemy0.follow((float) 0.01);
        Assert.assertNotEquals(5, enemy0.getX());
        // player on the left
        player0.setPosX(-100);
        float oldEnemyX = enemy0.getX();
        enemy0.follow((float) 0.01);
        Assert.assertNotEquals(oldEnemyX, enemy0.getX());
        // player got out of range
        player0.setPosX(-1000);
        enemy0.follow((float) 0.01);
        float newEnemyX = enemy0.getX();
        Assert.assertEquals(newEnemyX, enemy0.getX(), 0.1);

        // update changes values
        oldEnemyX = enemy0.getX();
        player0.setPosX(-100);
        enemy0.update(1, -9.8f);
        Assert.assertNotEquals(oldEnemyX, enemy0.getX());

        // jump doesnt throw errors
        enemy0.jump();

        // death
        // death
        enemy0.setLives(0);
        enemy0.update(1, -9.8f);
    }

    @org.junit.Test
    public void testEnemy1() {
        ArrayList<Entity> entities = new ArrayList<>();
        Player0 player0 = new Player0(90, 10, map, 10, 100, entities, PlayerType.PLAYER0, client, "player0");
        Enemy1 enemy1 = new Enemy1(5, 10, map, 10, 100, entities, "enemy1", client);
        entities.add(player0);
        entities.add(enemy1);
        enemy1.entities = entities;

        Assert.assertEquals("enemy1", enemy1.getId());
        // is moving
        float oldX = enemy1.getX();
        enemy1.moveRight((float) 0.1);
        Assert.assertNotEquals(oldX, enemy1.getX());
        enemy1.moveLeft((float) 0.1);
        Assert.assertEquals(oldX, enemy1.getX(), 0.1);

        // shoot does dmg !!!
        float oldPlayerHPRight = player0.getLives();
        // Player on the right
        enemy1.shoot();
        Assert.assertEquals(oldPlayerHPRight, player0.getLives(), 0.1);
        enemy1.moveRight((float) 0.01);
        enemy1.shoot();
        Assert.assertNotEquals(oldPlayerHPRight, player0.getLives());
        // Player on the left
        player0.setPosX(-90);
        float oldPlayerHPLeft = player0.getLives();
        enemy1.shoot();
        Assert.assertEquals(oldPlayerHPLeft, player0.getLives(), 0.1);
        enemy1.moveLeft((float) 0.01);
        enemy1.shoot();
        Assert.assertNotEquals(oldPlayerHPLeft, player0.getLives());
        // Player out of range
        player0.setPosX(-100000);
        enemy1.shoot();
        Assert.assertEquals(8, player0.getLives(), 0.1);
        enemy1.moveRight((float) 0.01);
        player0.setPosX(100000);
        enemy1.shoot();
        Assert.assertEquals(8, player0.getLives(), 0.1);


        // Enemy follows player

        // player out of range
        enemy1.setPosX(5);
        enemy1.follow((float) 0.01);
        Assert.assertEquals(5, enemy1.getX(), 0.1);
        // player on the right
        player0.setPosX(100);
        enemy1.follow((float) 0.01);
        Assert.assertNotEquals(5, enemy1.getX());
        // player on the left
        player0.setPosX(-100);
        float oldEnemyX = enemy1.getX();
        enemy1.follow((float) 0.01);
        Assert.assertNotEquals(oldEnemyX, enemy1.getX());
        // player got out of range
        player0.setPosX(-1000);
        enemy1.follow((float) 0.01);
        float newEnemyX = enemy1.getX();
        Assert.assertEquals(newEnemyX, enemy1.getX(), 0.1);

        // update changes values
        oldEnemyX = enemy1.getX();
        player0.setPosX(-10);
        enemy1.update(1, -9.8f);
        Assert.assertNotEquals(oldEnemyX, enemy1.getX());

        // jump doesnt throw errors
        enemy1.jump();

        // death
        enemy1.setLives(0);
        enemy1.update(1, -9.8f);
    }

    @org.junit.Test
    public void testEnemyType() {
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

    @org.junit.Test
    public void testEntity() {
        map = mock(GameMap.class);
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
    public void testEntityType() {
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
    public void testPlayerType() {
        PlayerType player0 = PlayerType.PLAYER0;
        Assert.assertEquals("character0", player0.getId());
        // images for textures
        ArrayList<String> rightImages = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                rightImages.add("character0/character0_running_right_" + i + ".png");
            }
        }
        Assert.assertEquals(rightImages, player0.getRight());
        ArrayList<String> leftImages = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                leftImages.add("character0/character0_running_left_" + i + ".png");
            }
        }
        Assert.assertEquals(leftImages, player0.getLeft());
        // Texture loads
        Assert.assertNotNull(player0.getRunningLeft());
        Assert.assertNotNull(player0.getRunningRight());
        Assert.assertNotNull(player0.getStandingLeft());
        Assert.assertNotNull(player0.getStandingRight());
        Assert.assertNotNull(player0.getLeftJumpingUp());
        Assert.assertNotNull(player0.getRightJumpingUp());
    }

    @org.junit.Test
    public void testOtherPlayer() {
        ArrayList<Entity> entities = new ArrayList<>();
        Enemy0 enemy0 = new Enemy0(5, 10, map, 10, 100, entities, "enemy0", client);
        OtherPlayer otherPlayer = new OtherPlayer(5, 10, map, 10, "otherPlayer", PlayerType.PLAYER0);
        // getters setters
        otherPlayer.setSmallDrone("test");
        Assert.assertEquals("test", otherPlayer.getSmallDrone());
        otherPlayer.setSmallDroneX(1f);
        otherPlayer.setSmallDroneY(2f);
        Assert.assertEquals(1, otherPlayer.getSmallDroneX(), 0.1);
        Assert.assertEquals(2, otherPlayer.getSmallDroneY(), 0.1);
        otherPlayer.setDroneX(1f);
        otherPlayer.setDroneY(2f);
        otherPlayer.setAbility("poof");
        otherPlayer.setAbilityX(1f);
        otherPlayer.setAbilityY(2f);
        otherPlayer.setGunPos(1f);
        otherPlayer.setTexture("texture.png");
        otherPlayer.setGunfire("gunfireTexture.png");
        Assert.assertEquals("otherPlayer", otherPlayer.getId());
    }

    @org.junit.Test
    public void testPlayer0() {
        ArrayList<Entity> entities = new ArrayList<>();
        Enemy0 enemy0 = new Enemy0(5, 10, map, 10, 100, entities, "enemy0", client);
        Player0 player0 = new Player0(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player0");
        Player0 player0mock = mock(Player0.class);

        entities.add(player0);
        entities.add(enemy0);


        // lives lost package doesnt throw errors
        player0.livesLostPackage(enemy0);
        // ability package doesnt throw errors
        player0.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(player0.isRight());
        Assert.assertFalse(player0.isMoving());
        Assert.assertFalse(player0.isShoot());
        Assert.assertEquals("player0", player0.getId());
        Assert.assertEquals(1000, player0.getLives(), 0.1);
        Assert.assertEquals(1000, player0.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        player0.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        player0.moveLeft(1);

        // player doesnt move right without pressing the button
        player0.moveRight(1);

        // shoot
        // looking right
        player0.moveRight(1);
        player0.isRight = true;
        player0.setPosX(6);
        player0.setPosY(10);
        enemy0.setPosX(-50);
        float enemy0OldHP = enemy0.getLives();
        player0.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(50);
        player0.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());
        // looking left
        player0.isRight = false;
        player0.moveLeft(1);
        player0.setPosX(5);
        enemy0.setPosX(6);
        enemy0OldHP = enemy0.getLives();
        player0.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(-10);
        player0.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());

        // xSkill
        enemy0.setLives(15);
        player0.xSkill();
        Assert.assertTrue(player0.xSkill);
        player0.explosionTime = true;
        player0.xSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.1);

        // cSkill fixing the needed booleans since the time is not an option here
        // enemy on the left
        enemy0.setLives(15);
        player0.cSkill();
        Assert.assertTrue(player0.cSkill);
        player0.cDoesDmg = true;
        player0.cSkill();
        Assert.assertEquals(5, enemy0.getLives(), 0.1);
        enemy0.setLives(5);
        player0.cDidDmg = false;
        player0.cSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.1);
        // enemy on the right
        player0.setPosX(5);
        player0.isRight = true;
        enemy0.setPosX(50);
        enemy0.setLives(15);
        player0.cDidDmg = false;
        player0.cSkill();
        Assert.assertEquals(5, enemy0.getLives(), 0.1);
        enemy0.setLives(5);
        player0.cDidDmg = false;
        player0.cSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.1);

        // vSkill
        player0.setLives(500);
        player0.vSkill();
        Assert.assertEquals(900, player0.getLives(), 0.1);
        player0.vSkill = false;
        player0.vSkill();
        Assert.assertEquals(1000, player0.getLives(), 0.1);

        // update doesnt throw errors
        player0.update(1, 9.8f);

        // update upon death
        player0.setLives(0);
        player0.update(1, 9.8f);
    }

    @org.junit.Test
    public void testPlayer1() {
        ArrayList<Entity> entities = new ArrayList<>();
        Enemy0 enemy0 = new Enemy0(5, 10, map, 10, 100, entities, "enemy0", client);
        Player1 player1 = new Player1(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player1");

        entities.add(player1);
        entities.add(enemy0);


        // lives lost package doesnt throw errors
        player1.livesLostPackage(enemy0);
        // ability package doesnt throw errors
        player1.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(player1.isRight());
        Assert.assertFalse(player1.isMoving());
        Assert.assertFalse(player1.isShoot());
        Assert.assertEquals("player1", player1.getId());
        Assert.assertEquals(1000, player1.getLives(), 0.1);
        Assert.assertEquals(1000, player1.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        player1.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        player1.moveLeft(1);

        // player doesnt move right without pressing the button
        player1.moveRight(1);

        // shoot
        // looking right
        player1.isRight = true;
        player1.moveRight(1);
        player1.setPosX(6);
        player1.setPosY(10);
        enemy0.setPosX(-50);
        float enemy0OldHP = enemy0.getLives();
        player1.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(50);
        player1.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());
        // looking left
        player1.isRight = false;
        player1.moveLeft(1);
        player1.setPosX(5);
        enemy0.setPosX(6);
        enemy0OldHP = enemy0.getLives();
        player1.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(-10);
        player1.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());

        // xSkill
        player1.xSkill();
        player1.xSkillXY.put(1f, 1f);
        enemy0.setPosX(1);
        enemy0.setPosY(1);
        player1.xSkill();
        Assert.assertEquals(7.9f, enemy0.getLives(), 0.01);
        enemy0.setLives(0.01f);
        player1.xSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.01);

        // cSkill fixing the needed booleans since the time is not an option here
        player1.cSkill();
        Assert.assertEquals(enemy0, player1.closestEnemy);
        player1.cExploding = true;
        enemy0.setLives(51);
        player1.cSkill();
        Assert.assertEquals(1, enemy0.getLives(), 0.01);
        enemy0.setLives(5);
        player1.cExploding = true;
        player1.cSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.01);

        // update doesnt throw errors
        player1.update(1, 9.8f);

        // update upon death
        player1.setLives(0);
        player1.update(1, 9.8f);
    }

    @org.junit.Test
    public void testPlayer2() {
        ArrayList<Entity> entities = new ArrayList<>();
        Enemy0 enemy0 = new Enemy0(5, 10, map, 10, 100, entities, "enemy0", client);
        Player2 player2 = new Player2(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player2");

        entities.add(player2);
        entities.add(enemy0);


        // lives lost package doesnt throw errors
        player2.livesLostPackage(enemy0);
        // ability package doesnt throw errors
        player2.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(player2.isRight());
        Assert.assertFalse(player2.isMoving());
        Assert.assertFalse(player2.isShoot());
        Assert.assertEquals("player2", player2.getId());
        Assert.assertEquals(1000, player2.getLives(), 0.1);
        Assert.assertEquals(1000, player2.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        player2.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        player2.moveLeft(1);

        // player doesnt move right without pressing the button
        player2.moveRight(1);

        // drone package creates correctly
        player2.dronePackage(0, 0);

        // shoot
        // looking right
        player2.isRight = true;
        player2.moveRight(1);
        player2.setPosX(6);
        player2.setPosY(10);
        enemy0.setPosX(-50);
        float enemy0OldHP = enemy0.getLives();
        player2.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(50);
        player2.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());
        // looking left
        player2.isRight = false;
        player2.moveLeft(1);
        player2.setPosX(5);
        enemy0.setPosX(6);
        enemy0OldHP = enemy0.getLives();
        player2.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(-10);
        player2.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());

        // xSkill
        // enemy in the up right
        player2.droneX = 1;
        player2.droneY = 1;
        enemy0.setPosX(35);
        enemy0.setPosY(35);
        player2.xSkill();
        Assert.assertEquals(enemy0, player2.closestEnemy);
        Assert.assertEquals(6, player2.xSkillX, 0.1);
        Assert.assertEquals(6, player2.xSkillY, 0.1);
        player2.xSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.1);
        // enemy in bottom left
        enemy0.setLives(10);
        player2.xSkill = false;
        player2.xExplosion = false;
        player2.xSkillX = 1;
        player2.xSkillY = 1;
        enemy0.setPosX(-34);
        enemy0.setPosY(-34);
        player2.xSkill();
        Assert.assertEquals(enemy0, player2.closestEnemy);
        Assert.assertEquals(-4, player2.xSkillX, 0.1);
        Assert.assertEquals(-4, player2.xSkillY, 0.1);
        player2.xSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.1);

        // cSkill
        enemy0.setLives(35);
        player2.droneX = 1;
        player2.droneY = 1;
        enemy0.setPosX(10);
        enemy0.setPosY(10);
        player2.cSkill();
        Assert.assertEquals(35, enemy0.getLives(), 0.1);
        player2.droneExplosion = true;
        player2.cSkill();
        Assert.assertEquals(5, enemy0.getLives(), 0.1);
        player2.droneExplosion = true;
        player2.cSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.1);


        // vSKill
        player2.setLives(500);
        player2.vSkill();
        Assert.assertEquals(900, player2.getLives(), 0.1);
        player2.vSkill = false;
        player2.vSkill();
        Assert.assertEquals(1000, player2.getLives(), 0.1);

        // drone just moves around player being close
        player2.isRight = true;
        player2.droneX = 21;
        player2.droneY = 21;
        player2.droneCanMove = true;
        player2.setPosX(20);
        player2.setPosY(0);
        // player2 looking right
        player2.dronePosition();
        Assert.assertEquals(19, player2.droneX, 0.1);
        player2.dronePosition();
        Assert.assertEquals(17, player2.droneX, 0.1);
        player2.droneX = 0;
        player2.dronePosition();
        Assert.assertEquals(0, player2.droneX, 0.1);
        // player2 looking left
        player2.isRight = false;
        player2.droneX = 20;
        player2.dronePosition();
        Assert.assertEquals(22, player2.droneX, 0.1);
        player2.droneX = 40;
        player2.dronePosition();
        Assert.assertEquals(40, player2.droneX, 0.1);

        // drone is coming back after being far away
        player2.droneCanMove = false;
        player2.droneIsComingBack = true;
        player2.droneX = -5;
        player2.droneY = -5;
        player2.dronePosition();
        Assert.assertEquals(0, player2.droneX, 0.1);
        Assert.assertEquals(0, player2.droneY, 0.1);
        player2.droneX = 46;
        player2.droneY = 31;
        player2.dronePosition();
        Assert.assertEquals(41, player2.droneX, 0.1);
        Assert.assertEquals(26, player2.droneY, 0.1);
        // drone in bounds
        player2.droneX = 40;
        player2.droneY = 25;
        player2.dronePosition();
        Assert.assertTrue(player2.droneCanMove);
        Assert.assertFalse(player2.droneIsComingBack);

        // update doesnt throw errors
        player2.update(1, 9.8f);

        // update upon death
        player2.setLives(0);
        player2.update(1, 9.8f);
    }


    @org.junit.Test
    public void testPlayer3() {
        ArrayList<Entity> entities = new ArrayList<>();
        Enemy0 enemy0 = new Enemy0(5, 10, map, 10, 100, entities, "enemy0", client);
        Player3 player3 = new Player3(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player3");

        entities.add(player3);
        entities.add(enemy0);


        // lives lost package doesnt throw errors
        player3.livesLostPackage(enemy0);
        // ability package doesnt throw errors
        player3.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(player3.isRight());
        Assert.assertFalse(player3.isMoving());
        Assert.assertFalse(player3.isShoot());
        Assert.assertEquals("player3", player3.getId());
        Assert.assertEquals(1000, player3.getLives(), 0.1);
        Assert.assertEquals(1000, player3.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        player3.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        player3.moveLeft(1);

        // player doesnt move right without pressing the button
        player3.moveRight(1);

        // shoot
        // looking right
        player3.isRight = true;
        player3.moveRight(1);
        player3.setPosX(6);
        player3.setPosY(10);
        enemy0.setPosX(-50);
        float enemy0OldHP = enemy0.getLives();
        player3.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(50);
        player3.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());
        // looking left
        player3.isRight = false;
        player3.moveLeft(1);
        player3.setPosX(5);
        enemy0.setPosX(6);
        enemy0OldHP = enemy0.getLives();
        player3.shoot();
        Assert.assertEquals(enemy0OldHP, enemy0.getLives(), 0.1);
        enemy0.setPosX(-10);
        player3.shoot();
        Assert.assertNotEquals(enemy0OldHP, enemy0.getLives());

        // xSkill
        player3.setPosX(0);
        player3.setPosY(0);
        player3.isRight = true;
        player3.xSkill();
        Assert.assertTrue(player3.xRight);
        Assert.assertEquals(player3.getWidth(), player3.xSkillX, 0.1);
        player3.xSkill = false;
        player3.isRight = false;
        player3.xRight = false;
        player3.xSkill();
        Assert.assertFalse(player3.xRight);
        Assert.assertEquals(0, player3.xSkillX, 0.1);
        enemy0.setPosX(0);
        enemy0.setPosY(0);
        player3.xExplosion = true;
        player3.setLives(500);
        player3.xSkill();
        Assert.assertEquals(0, enemy0.getLives(), 0.1);
        Assert.assertEquals(800, player3.getLives(), 0.1);
        player3.xExplosion = true;
        enemy0.setLives(20);
        player3.xSkill();
        Assert.assertEquals(5, enemy0.getLives(), 0.1);

        // cSkill
        player3.grounded = true;
        player3.cSkill();
        player3.setLives(100);
        player3.cSkillIsReady = true;
        player3.cSkill();
        Assert.assertEquals(300, player3.getLives(), 0.1);
        player3.setLives(900);
        player3.cSkillIsReady = true;
        player3.cSkill();
        Assert.assertEquals(1000, player3.getLives(), 0.1);

        // vSkill
        player3.setLives(500);
        player3.vSkill();
        Assert.assertEquals(800, player3.getLives(), 0.1);
        player3.vSkill = false;
        player3.vSkill();
        Assert.assertEquals(1000, player3.getLives(), 0.1);

        // update doesnt throw errors
        player3.update(1, 9.8f);

        // update upon death
        player3.setLives(0);
        player3.update(1, 9.8f);
    }
}