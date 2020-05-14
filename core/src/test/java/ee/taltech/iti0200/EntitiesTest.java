package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.entities.*;
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

    // Class tests are ordered like they are in project root

    @org.junit.Test
    public void testEnemy0() {
        ArrayList<Entity> entities = new ArrayList<>();
        FirstPlayer firstPlayer = new FirstPlayer(90, 10, map, 10, 100, entities, PlayerType.PLAYER0, client, "player0");
        FirstEnemy firstEnemy = new FirstEnemy(5, 10, map, 10, 100, entities, "enemy0", client);
        entities.add(firstPlayer);
        entities.add(firstEnemy);
        firstEnemy.entities = entities;

        Assert.assertEquals("enemy0", firstEnemy.getId());
        // is moving
        float oldX = firstEnemy.getX();
        firstEnemy.moveRight((float) 0.1);
        Assert.assertNotEquals(oldX, firstEnemy.getX());
        firstEnemy.moveLeft((float) 0.1);
        Assert.assertEquals(oldX, firstEnemy.getX(), 0.1);

        // shoot does dmg !!!
        float oldPlayerHPRight = firstPlayer.getLives();
        // Player on the right
        firstEnemy.shoot();
        Assert.assertEquals(oldPlayerHPRight, firstPlayer.getLives(), 0.1);
        firstEnemy.moveRight((float) 0.01);
        firstEnemy.shoot();
        Assert.assertNotEquals(oldPlayerHPRight, firstPlayer.getLives());
        // Player on the left
        firstPlayer.setPosX(-90);
        float oldPlayerHPLeft = firstPlayer.getLives();
        firstEnemy.shoot();
        Assert.assertEquals(oldPlayerHPLeft, firstPlayer.getLives(), 0.1);
        firstEnemy.moveLeft((float) 0.01);
        firstEnemy.shoot();
        Assert.assertNotEquals(oldPlayerHPLeft, firstPlayer.getLives());
        // Player out of range
        firstPlayer.setPosX(-100000);
        firstEnemy.shoot();
        Assert.assertEquals(8, firstPlayer.getLives(), 0.1);
        firstEnemy.moveRight((float) 0.01);
        firstPlayer.setPosX(100000);
        firstEnemy.shoot();
        Assert.assertEquals(8, firstPlayer.getLives(), 0.1);


        // Enemy follows player

        // player out of range
        firstEnemy.setPosX(5);
        firstEnemy.follow((float) 0.01);
        Assert.assertEquals(5, firstEnemy.getX(), 0.1);
        // player on the right
        firstPlayer.setPosX(100);
        firstEnemy.follow((float) 0.01);
        Assert.assertNotEquals(5, firstEnemy.getX());
        // player on the left
        firstPlayer.setPosX(-100);
        float oldEnemyX = firstEnemy.getX();
        firstEnemy.follow((float) 0.01);
        Assert.assertNotEquals(oldEnemyX, firstEnemy.getX());
        // player got out of range
        firstPlayer.setPosX(-1000);
        firstEnemy.follow((float) 0.01);
        float newEnemyX = firstEnemy.getX();
        Assert.assertEquals(newEnemyX, firstEnemy.getX(), 0.1);

        // update changes values
        oldEnemyX = firstEnemy.getX();
        firstPlayer.setPosX(-100);
        firstEnemy.update(1, -9.8f);
        Assert.assertNotEquals(oldEnemyX, firstEnemy.getX());

        // jump doesnt throw errors
        firstEnemy.jump();

        // death
        // death
        firstEnemy.setLives(0);
        firstEnemy.update(1, -9.8f);
    }

    @org.junit.Test
    public void testEnemy1() {
        ArrayList<Entity> entities = new ArrayList<>();
        FirstPlayer firstPlayer = new FirstPlayer(90, 10, map, 10, 100, entities, PlayerType.PLAYER0, client, "player0");
        SecondEnemy secondEnemy = new SecondEnemy(5, 10, map, 10, 100, entities, "enemy1", client);
        entities.add(firstPlayer);
        entities.add(secondEnemy);
        secondEnemy.entities = entities;

        Assert.assertEquals("enemy1", secondEnemy.getId());
        // is moving
        float oldX = secondEnemy.getX();
        secondEnemy.moveRight((float) 0.1);
        Assert.assertNotEquals(oldX, secondEnemy.getX());
        secondEnemy.moveLeft((float) 0.1);
        Assert.assertEquals(oldX, secondEnemy.getX(), 0.1);

        // shoot does dmg !!!
        float oldPlayerHPRight = firstPlayer.getLives();
        // Player on the right
        secondEnemy.shoot();
        Assert.assertEquals(oldPlayerHPRight, firstPlayer.getLives(), 0.1);
        secondEnemy.moveRight((float) 0.01);
        secondEnemy.shoot();
        Assert.assertNotEquals(oldPlayerHPRight, firstPlayer.getLives());
        // Player on the left
        firstPlayer.setPosX(-90);
        float oldPlayerHPLeft = firstPlayer.getLives();
        secondEnemy.shoot();
        Assert.assertEquals(oldPlayerHPLeft, firstPlayer.getLives(), 0.1);
        secondEnemy.moveLeft((float) 0.01);
        secondEnemy.shoot();
        Assert.assertNotEquals(oldPlayerHPLeft, firstPlayer.getLives());
        // Player out of range
        firstPlayer.setPosX(-100000);
        secondEnemy.shoot();
        Assert.assertEquals(8, firstPlayer.getLives(), 0.1);
        secondEnemy.moveRight((float) 0.01);
        firstPlayer.setPosX(100000);
        secondEnemy.shoot();
        Assert.assertEquals(8, firstPlayer.getLives(), 0.1);


        // Enemy follows player

        // player out of range
        secondEnemy.setPosX(5);
        secondEnemy.follow((float) 0.01);
        Assert.assertEquals(5, secondEnemy.getX(), 0.1);
        // player on the right
        firstPlayer.setPosX(100);
        secondEnemy.follow((float) 0.01);
        Assert.assertNotEquals(5, secondEnemy.getX());
        // player on the left
        firstPlayer.setPosX(-100);
        float oldEnemyX = secondEnemy.getX();
        secondEnemy.follow((float) 0.01);
        Assert.assertNotEquals(oldEnemyX, secondEnemy.getX());
        // player got out of range
        firstPlayer.setPosX(-1000);
        secondEnemy.follow((float) 0.01);
        float newEnemyX = secondEnemy.getX();
        Assert.assertEquals(newEnemyX, secondEnemy.getX(), 0.1);

        // update changes values
        oldEnemyX = secondEnemy.getX();
        firstPlayer.setPosX(-10);
        secondEnemy.update(1, -9.8f);
        Assert.assertNotEquals(oldEnemyX, secondEnemy.getX());

        // jump doesnt throw errors
        secondEnemy.jump();

        // death
        secondEnemy.setLives(0);
        secondEnemy.update(1, -9.8f);
    }


    @org.junit.Test
    public void testEnemy2() {
        ArrayList<Entity> entities = new ArrayList<>();
        FirstPlayer firstPlayer = new FirstPlayer(90, 10, map, 10, 100, entities, PlayerType.PLAYER0, client, "player0");
        ThirdEnemy thirdEnemy = new ThirdEnemy(5, 10, map, 10, 100, entities, "enemy2", client);
        entities.add(firstPlayer);
        entities.add(thirdEnemy);

        Assert.assertEquals("enemy2", thirdEnemy.getId());
        // is moving
        float oldX = thirdEnemy.getX();
        thirdEnemy.moveRight((float) 0.1);
        Assert.assertNotEquals(oldX, thirdEnemy.getX());
        thirdEnemy.moveLeft((float) 0.1);
        Assert.assertEquals(oldX, thirdEnemy.getX(), 0.1);

        // shoot does dmg !!! for Enemy2, entity has to physically touch player to do dmg
        float oldPlayerHP = firstPlayer.getLives();
        // out of range
        thirdEnemy.setPosX(10);
        thirdEnemy.setPosY(10);
        firstPlayer.setPosX(20);
        firstPlayer.setPosY(20);
        thirdEnemy.shoot();
        Assert.assertEquals(oldPlayerHP, firstPlayer.getLives(), 0.1);
        // in range
        firstPlayer.setPosX(10);
        firstPlayer.setPosY(10);
        thirdEnemy.shoot();
        Assert.assertNotEquals(oldPlayerHP, firstPlayer.getLives(), 0.1);

        thirdEnemy.setOldTargets(new ArrayList<Entity>());
        thirdEnemy.setTarget(new ArrayList<Entity>());
        // Enemy follows player
        // player is far
        thirdEnemy.setSprint(false);
        // player top right
        firstPlayer.setPosX(200);
        firstPlayer.setPosY(200);
        thirdEnemy.follow(1f);
        Assert.assertEquals(10.5, thirdEnemy.getX(), 0.1);
        Assert.assertEquals(10.5, thirdEnemy.getY(), 0.1);
        // player bottom left
        firstPlayer.setPosX(-200);
        firstPlayer.setPosY(-200);
        thirdEnemy.follow(1f);
        Assert.assertEquals(10, thirdEnemy.getX(), 0.1);
        Assert.assertEquals(10, thirdEnemy.getY(), 0.1);
        // player is close
        thirdEnemy.setSprint(true);
        // player top right
        firstPlayer.setPosX(20);
        firstPlayer.setPosY(20);
        thirdEnemy.follow(1f);
        Assert.assertEquals(11.5, thirdEnemy.getX(), 0.1);
        Assert.assertEquals(11.5, thirdEnemy.getY(), 0.1);
        // player bottom left
        firstPlayer.setPosX(-20);
        firstPlayer.setPosY(-20);
        thirdEnemy.follow(1f);
        Assert.assertEquals(10, thirdEnemy.getX(), 0.1);
        Assert.assertEquals(10, thirdEnemy.getY(), 0.1);
        // player too close changes bool isTooClose
        firstPlayer.setPosX(10);
        firstPlayer.setPosY(10);
        thirdEnemy.follow(1f);
        Assert.assertTrue(thirdEnemy.isTooClose());
        Assert.assertFalse(thirdEnemy.getSprint());


        // update changes values with sprint
        float oldEnemyX = thirdEnemy.getX();
        firstPlayer.setPosX(-100);
        thirdEnemy.update(1, -9.8f);
        Assert.assertNotEquals(oldEnemyX, thirdEnemy.getX());
        // update changes values without sprint
        thirdEnemy.setSprint(false);
        firstPlayer.setPosX(-100);
        thirdEnemy.update(1, -9.8f);
        Assert.assertNotEquals(oldEnemyX, thirdEnemy.getX());

        // jump doesnt throw errors
        thirdEnemy.jump();

        // death
        thirdEnemy.setLives(0);
        thirdEnemy.update(1, -9.8f);

        // enemy chooses new target
        FirstPlayer firstPlayer01 = new FirstPlayer(90, 10, map, 10, 100, entities, PlayerType.PLAYER0, client, "player0");
        entities.add(firstPlayer01);
        thirdEnemy.follow(1f);
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
        EntityType entityEnemy2 = EntityType.ENEMY2;
        Assert.assertSame("enemy2", entityEnemy2.getId());
        Assert.assertSame(5, entityEnemy2.getWidth());
        Assert.assertSame(5, entityEnemy2.getHeight());
        Assert.assertEquals(5, entityEnemy2.getWeight(), 0.1);
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
        FirstEnemy firstEnemy = new FirstEnemy(5, 10, map, 10, 100, entities, "enemy0", client);
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
        FirstEnemy firstEnemy = new FirstEnemy(5, 10, map, 10, 100, entities, "enemy0", client);
        FirstPlayer firstPlayer = new FirstPlayer(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player0");
        FirstPlayer firstPlayerMock = mock(FirstPlayer.class);

        entities.add(firstPlayer);
        entities.add(firstEnemy);


        // lives lost package doesnt throw errors
        firstPlayer.livesLostPackage(firstEnemy);
        // ability package doesnt throw errors
        firstPlayer.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(firstPlayer.isRight());
        Assert.assertFalse(firstPlayer.isMoving());
        Assert.assertFalse(firstPlayer.isShoot());
        Assert.assertEquals("player0", firstPlayer.getId());
        Assert.assertEquals(1000, firstPlayer.getLives(), 0.1);
        Assert.assertEquals(1000, firstPlayer.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        firstPlayer.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        firstPlayer.moveLeft(1);

        // player doesnt move right without pressing the button
        firstPlayer.moveRight(1);

        // shoot
        // looking right
        firstPlayer.moveRight(1);
        firstPlayer.isRight = true;
        firstPlayer.setPosX(6);
        firstPlayer.setPosY(10);
        firstEnemy.setPosX(-50);
        float enemy0OldHP = firstEnemy.getLives();
        firstPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(50);
        firstPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());
        // looking left
        firstPlayer.isRight = false;
        firstPlayer.moveLeft(1);
        firstPlayer.setPosX(5);
        firstEnemy.setPosX(6);
        enemy0OldHP = firstEnemy.getLives();
        firstPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(-10);
        firstPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());

        // xSkill
        firstEnemy.setLives(15);
        firstPlayer.xSkill();
        Assert.assertTrue(firstPlayer.xSkill);
        firstPlayer.explosionTime = true;
        firstPlayer.xSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.1);

        // cSkill fixing the needed booleans since the time is not an option here
        // enemy on the left
        firstEnemy.setLives(15);
        firstPlayer.cSkill();
        Assert.assertTrue(firstPlayer.cSkill);
        firstPlayer.cDoesDmg = true;
        firstPlayer.cSkill();
        Assert.assertEquals(5, firstEnemy.getLives(), 0.1);
        firstEnemy.setLives(5);
        firstPlayer.cDidDmg = false;
        firstPlayer.cSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.1);
        // enemy on the right
        firstPlayer.setPosX(5);
        firstPlayer.isRight = true;
        firstEnemy.setPosX(50);
        firstEnemy.setLives(15);
        firstPlayer.cDidDmg = false;
        firstPlayer.cSkill();
        Assert.assertEquals(5, firstEnemy.getLives(), 0.1);
        firstEnemy.setLives(5);
        firstPlayer.cDidDmg = false;
        firstPlayer.cSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.1);

        // vSkill
        firstPlayer.setLives(500);
        firstPlayer.vSkill();
        Assert.assertEquals(900, firstPlayer.getLives(), 0.1);
        firstPlayer.vSkill = false;
        firstPlayer.vSkill();
        Assert.assertEquals(1000, firstPlayer.getLives(), 0.1);

        // update doesnt throw errors
        firstPlayer.update(1, 9.8f);

        // update upon death
        firstPlayer.setLives(0);
        firstPlayer.update(1, 9.8f);
    }

    @org.junit.Test
    public void testPlayer1() {
        ArrayList<Entity> entities = new ArrayList<>();
        FirstEnemy firstEnemy = new FirstEnemy(5, 10, map, 10, 100, entities, "enemy0", client);
        SecondPlayer secondPlayer = new SecondPlayer(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player1");

        entities.add(secondPlayer);
        entities.add(firstEnemy);


        // lives lost package doesnt throw errors
        secondPlayer.livesLostPackage(firstEnemy);
        // ability package doesnt throw errors
        secondPlayer.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(secondPlayer.isRight());
        Assert.assertFalse(secondPlayer.isMoving());
        Assert.assertFalse(secondPlayer.isShoot());
        Assert.assertEquals("player1", secondPlayer.getId());
        Assert.assertEquals(1000, secondPlayer.getLives(), 0.1);
        Assert.assertEquals(1000, secondPlayer.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        secondPlayer.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        secondPlayer.moveLeft(1);

        // player doesnt move right without pressing the button
        secondPlayer.moveRight(1);

        // shoot
        // looking right
        secondPlayer.isRight = true;
        secondPlayer.moveRight(1);
        secondPlayer.setPosX(6);
        secondPlayer.setPosY(10);
        firstEnemy.setPosX(-50);
        float enemy0OldHP = firstEnemy.getLives();
        secondPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(50);
        secondPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());
        // looking left
        secondPlayer.isRight = false;
        secondPlayer.moveLeft(1);
        secondPlayer.setPosX(5);
        firstEnemy.setPosX(6);
        enemy0OldHP = firstEnemy.getLives();
        secondPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(-10);
        secondPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());

        // xSkill
        // enemy in range
        float enemyHpBeforeX = firstEnemy.getLives();
        secondPlayer.setPosX(5);
        secondPlayer.setPosY(5);
        firstEnemy.setPosX(10);
        firstEnemy.setPosY(10);
        secondPlayer.xSkill();
        System.out.println(secondPlayer.entities);
        Assert.assertEquals(enemyHpBeforeX - 0.4f, firstEnemy.getLives(), 0.1f);
        // kills upon reaching 0 hp
        firstEnemy.setLives(0.1f);
        secondPlayer.xSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.001f);
        // enemy too far, skill doesnt do dmg
        firstEnemy.setLives(10);
        firstEnemy.setPosX(100);
        firstEnemy.setPosY(100);
        secondPlayer.xSkill();
        Assert.assertEquals(10, firstEnemy.getLives(), 0.001f);


        // cSkill fixing the needed booleans since the time is not an option here
        secondPlayer.cSkill();
        Assert.assertEquals(firstEnemy, secondPlayer.closestEnemy);
        secondPlayer.cExploding = true;
        firstEnemy.setLives(51);
        secondPlayer.cSkill();
        Assert.assertEquals(1, firstEnemy.getLives(), 0.01);
        firstEnemy.setLives(5);
        secondPlayer.cExploding = true;
        secondPlayer.cSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.01);
        // cSkill picks new enemy
        secondPlayer.cSkill = false;
        FirstEnemy firstEnemy01 = new FirstEnemy(5, 10, map, 10, 100, entities, "enemy01", client);
        entities.add(firstEnemy01);
        firstEnemy.setLives(10);
        secondPlayer.setPosX(0);
        secondPlayer.setPosY(0);
        firstEnemy.setPosX(10);
        firstEnemy.setPosY(10);
        firstEnemy01.setPosX(5);
        firstEnemy01.setPosY(5);
        secondPlayer.cSkill();
        secondPlayer.cExploding = true;
        secondPlayer.cSkill();
        Assert.assertEquals(0, firstEnemy01.getLives(), 0.1);
        // update doesnt throw errors
        secondPlayer.update(1, 9.8f);

        // update upon death
        secondPlayer.setLives(0);
        secondPlayer.update(1, 9.8f);
    }

    @org.junit.Test
    public void testPlayer2() {
        ArrayList<Entity> entities = new ArrayList<>();
        FirstEnemy firstEnemy = new FirstEnemy(5, 10, map, 10, 100, entities, "enemy0", client);
        ThirdPlayer thirdPlayer = new ThirdPlayer(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player2");

        entities.add(thirdPlayer);
        entities.add(firstEnemy);


        // lives lost package doesnt throw errors
        thirdPlayer.livesLostPackage(firstEnemy);
        // ability package doesnt throw errors
        thirdPlayer.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(thirdPlayer.isRight());
        Assert.assertFalse(thirdPlayer.isMoving());
        Assert.assertFalse(thirdPlayer.isShoot());
        Assert.assertEquals("player2", thirdPlayer.getId());
        Assert.assertEquals(1000, thirdPlayer.getLives(), 0.1);
        Assert.assertEquals(1000, thirdPlayer.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        thirdPlayer.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        thirdPlayer.moveLeft(1);

        // player doesnt move right without pressing the button
        thirdPlayer.moveRight(1);

        // drone package creates correctly
        thirdPlayer.dronePackage(0, 0);

        // shoot
        // looking right
        thirdPlayer.isRight = true;
        thirdPlayer.moveRight(1);
        thirdPlayer.setPosX(6);
        thirdPlayer.setPosY(10);
        firstEnemy.setPosX(-50);
        float enemy0OldHP = firstEnemy.getLives();
        thirdPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(50);
        thirdPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());
        // looking left
        thirdPlayer.isRight = false;
        thirdPlayer.moveLeft(1);
        thirdPlayer.setPosX(5);
        firstEnemy.setPosX(6);
        enemy0OldHP = firstEnemy.getLives();
        thirdPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(-10);
        thirdPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());

        // xSkill
        // enemy in the up right
        thirdPlayer.droneX = 1;
        thirdPlayer.droneY = 1;
        firstEnemy.setPosX(35);
        firstEnemy.setPosY(35);
        thirdPlayer.xSkill();
        Assert.assertEquals(firstEnemy, thirdPlayer.closestEnemy);
        Assert.assertEquals(6, thirdPlayer.xSkillX, 0.1);
        Assert.assertEquals(6, thirdPlayer.xSkillY, 0.1);
        thirdPlayer.xSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.1);
        // enemy in bottom left
        firstEnemy.setLives(10);
        thirdPlayer.xSkill = false;
        thirdPlayer.xExplosion = false;
        thirdPlayer.xSkillX = 1;
        thirdPlayer.xSkillY = 1;
        firstEnemy.setPosX(-34);
        firstEnemy.setPosY(-34);
        thirdPlayer.xSkill();
        Assert.assertEquals(firstEnemy, thirdPlayer.closestEnemy);
        Assert.assertEquals(-4, thirdPlayer.xSkillX, 0.1);
        Assert.assertEquals(-4, thirdPlayer.xSkillY, 0.1);
        thirdPlayer.xSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.1);

        // cSkill
        firstEnemy.setLives(35);
        thirdPlayer.droneX = 1;
        thirdPlayer.droneY = 1;
        firstEnemy.setPosX(10);
        firstEnemy.setPosY(10);
        thirdPlayer.cSkill();
        Assert.assertEquals(35, firstEnemy.getLives(), 0.1);
        thirdPlayer.droneExplosion = true;
        thirdPlayer.cSkill();
        Assert.assertEquals(5, firstEnemy.getLives(), 0.1);


        // vSKill
        thirdPlayer.setLives(500);
        thirdPlayer.vSkill();
        Assert.assertEquals(900, thirdPlayer.getLives(), 0.1);
        thirdPlayer.vSkill = false;
        thirdPlayer.vSkill();
        Assert.assertEquals(1000, thirdPlayer.getLives(), 0.1);

        // drone just moves around player being close
        thirdPlayer.isRight = true;
        thirdPlayer.droneX = 21;
        thirdPlayer.droneY = 21;
        thirdPlayer.droneCanMove = true;
        thirdPlayer.setPosX(20);
        thirdPlayer.setPosY(0);
        // player2 looking right
        thirdPlayer.dronePosition();
        Assert.assertEquals(19, thirdPlayer.droneX, 0.1);
        thirdPlayer.dronePosition();
        Assert.assertEquals(17, thirdPlayer.droneX, 0.1);
        thirdPlayer.droneX = 0;
        thirdPlayer.dronePosition();
        Assert.assertEquals(0, thirdPlayer.droneX, 0.1);
        // player2 looking left
        thirdPlayer.isRight = false;
        thirdPlayer.droneX = 20;
        thirdPlayer.dronePosition();
        Assert.assertEquals(22, thirdPlayer.droneX, 0.1);
        thirdPlayer.droneX = 40;
        thirdPlayer.dronePosition();
        Assert.assertEquals(40, thirdPlayer.droneX, 0.1);

        // drone is coming back after being far away
        thirdPlayer.droneCanMove = false;
        thirdPlayer.droneIsComingBack = true;
        thirdPlayer.droneX = -5;
        thirdPlayer.droneY = -5;
        thirdPlayer.dronePosition();
        Assert.assertEquals(0, thirdPlayer.droneX, 0.1);
        Assert.assertEquals(0, thirdPlayer.droneY, 0.1);
        thirdPlayer.droneX = 46;
        thirdPlayer.droneY = 31;
        thirdPlayer.dronePosition();
        Assert.assertEquals(41, thirdPlayer.droneX, 0.1);
        Assert.assertEquals(26, thirdPlayer.droneY, 0.1);
        // drone in bounds
        thirdPlayer.droneX = 40;
        thirdPlayer.droneY = 25;
        thirdPlayer.dronePosition();
        Assert.assertTrue(thirdPlayer.droneCanMove);
        Assert.assertFalse(thirdPlayer.droneIsComingBack);

        // update doesnt throw errors
        thirdPlayer.update(1, 9.8f);

        // update upon death
        thirdPlayer.setLives(0);
        thirdPlayer.update(1, 9.8f);
    }


    @org.junit.Test
    public void testPlayer3() {
        ArrayList<Entity> entities = new ArrayList<>();
        FirstEnemy firstEnemy = new FirstEnemy(5, 10, map, 10, 100, entities, "enemy0", client);
        FourthPlayer fourthPlayer = new FourthPlayer(5, 10, map, 1000, 100, entities,
                PlayerType.PLAYER0, client, "player3");

        entities.add(fourthPlayer);
        entities.add(firstEnemy);


        // lives lost package doesnt throw errors
        fourthPlayer.livesLostPackage(firstEnemy);
        // ability package doesnt throw errors
        fourthPlayer.abilityPackage(5, 10, "texture.png");

        // getters setters
        Assert.assertFalse(fourthPlayer.isRight());
        Assert.assertFalse(fourthPlayer.isMoving());
        Assert.assertFalse(fourthPlayer.isShoot());
        Assert.assertEquals("player3", fourthPlayer.getId());
        Assert.assertEquals(1000, fourthPlayer.getLives(), 0.1);
        Assert.assertEquals(1000, fourthPlayer.getTotalHealth(), 0.1);

        // jump doesnt work without pressing space
        fourthPlayer.jump(10, 9.8f);

        // player doesnt move left without pressing the button
        fourthPlayer.moveLeft(1);

        // player doesnt move right without pressing the button
        fourthPlayer.moveRight(1);

        // shoot
        // looking right
        fourthPlayer.isRight = true;
        fourthPlayer.moveRight(1);
        fourthPlayer.setPosX(6);
        fourthPlayer.setPosY(10);
        firstEnemy.setPosX(-50);
        float enemy0OldHP = firstEnemy.getLives();
        fourthPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(50);
        fourthPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());
        // looking left
        fourthPlayer.isRight = false;
        fourthPlayer.moveLeft(1);
        fourthPlayer.setPosX(5);
        firstEnemy.setPosX(6);
        enemy0OldHP = firstEnemy.getLives();
        fourthPlayer.shoot();
        Assert.assertEquals(enemy0OldHP, firstEnemy.getLives(), 0.1);
        firstEnemy.setPosX(-10);
        fourthPlayer.shoot();
        Assert.assertNotEquals(enemy0OldHP, firstEnemy.getLives());

        // xSkill
        fourthPlayer.setPosX(0);
        fourthPlayer.setPosY(0);
        fourthPlayer.isRight = true;
        fourthPlayer.xSkill();
        Assert.assertTrue(fourthPlayer.xRight);
        Assert.assertEquals(fourthPlayer.getWidth(), fourthPlayer.xSkillX, 0.1);
        fourthPlayer.xSkill = false;
        fourthPlayer.isRight = false;
        fourthPlayer.xRight = false;
        fourthPlayer.xSkill();
        Assert.assertFalse(fourthPlayer.xRight);
        Assert.assertEquals(0, fourthPlayer.xSkillX, 0.1);
        firstEnemy.setPosX(0);
        firstEnemy.setPosY(0);
        fourthPlayer.xExplosion = true;
        fourthPlayer.setLives(500);
        fourthPlayer.xSkill();
        Assert.assertEquals(0, firstEnemy.getLives(), 0.1);
        Assert.assertEquals(800, fourthPlayer.getLives(), 0.1);
        fourthPlayer.xExplosion = true;
        firstEnemy.setLives(20);
        fourthPlayer.xSkill();
        Assert.assertEquals(5, firstEnemy.getLives(), 0.1);

        // cSkill
        fourthPlayer.grounded = true;
        fourthPlayer.cSkill();
        fourthPlayer.setLives(100);
        fourthPlayer.cSkillIsReady = true;
        fourthPlayer.cSkill();
        Assert.assertEquals(300, fourthPlayer.getLives(), 0.1);
        fourthPlayer.setLives(900);
        fourthPlayer.cSkillIsReady = true;
        fourthPlayer.cSkill();
        Assert.assertEquals(1000, fourthPlayer.getLives(), 0.1);

        // vSkill
        fourthPlayer.setLives(500);
        fourthPlayer.vSkill();
        Assert.assertEquals(800, fourthPlayer.getLives(), 0.1);
        fourthPlayer.vSkill = false;
        fourthPlayer.vSkill();
        Assert.assertEquals(1000, fourthPlayer.getLives(), 0.1);

        // update doesnt throw errors
        fourthPlayer.update(1, 9.8f);

        // update upon death
        fourthPlayer.setLives(0);
        fourthPlayer.update(1, 9.8f);
    }
}
