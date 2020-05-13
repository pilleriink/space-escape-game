package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0200.entities.*;
import ee.taltech.iti0200.entities.Enemy0;
import ee.taltech.iti0200.entities.Entity;
import ee.taltech.iti0200.server.packets.*;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TiledGameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
    private SpaceEscape game;
    final Client client;
    SpriteBatch Batch;


    // stage
    public Stage stage;
    public float deltaTime, currentTime, xCoolDownPoint, cCoolDownPoint, vCoolDownPoint;
    public String timeStr = "0", xLabelText, cLabelText, vLabelText;
    public BitmapFont font;
    public Label.LabelStyle labelStyle;
    public Label label, cLabel, vLabel, xLabel, zLabel;
    public Image xSkill, cSkill, vSkill, zSkill;
    public Texture xSkillTexture, cSkillTexture, vSkillTexture, zSkillTexture, corpseTexture;
    public int xCooldownTime, cCooldownTime, vCooldownTime, shakeIntensityRange = 30, shakeIntensityBuffer = 15;
    public boolean shaking, gonnaShake, bombShake, droneShake, removeV, cHasToBeGrounded;
    public Random random;
    public double shakingTime = 0.15;
    public List<Entity> dead;





    // cooldowns
//    public float currentTime;
//    public float xCooldownTime = 4;
//    public float xCoolDownPoint = 0;
//    public String xLabelText = "";
//    public int cCooldownTime = 3;
//    public float cCoolDownPoint;
//    public String cLabelText = "";
//    public int vCooldownTime = 5;
//    public float vCoolDownPoint;
//    public String vLabelText = "";
//    public int zCooldownTime = 4;
//    public float zCoolDownPoint;
//    public String zLabelText = "";


    public OrthographicCamera camera;

    GameMap gameMap;
    List<Player> otherPlayers;
    List<String> playerIds;
    Texture background;
    String id;
    Entity me;

    public GameScreen(SpaceEscape game, PlayerType playerType, Client client, String id) {
        this.client = client;
        this.game = game;
        this.id = id;
        otherPlayers = new ArrayList<>();
        playerIds = new ArrayList<>();

        gameMap = new TiledGameMap();
        gameMap.addPlayer(playerType, client, id);
        me = gameMap.getPlayer();

        playerIds.add(id);
        background = new Texture("menubackground.png");

        for (int i = 0; i < 4; i++) {
            gameMap.addEntity(new Enemy0(1000, 600, gameMap, 10, 100, gameMap.getEntities(), "" + i, client));
            playerIds.add("" + i);
        }
        for (int i = 4; i < 8; i++) {
            gameMap.addEntity(new Enemy1(1000, 600, gameMap, 10, 1, gameMap.getEntities(), "" + i, client));
            playerIds.add("" + i);
        }
        for (int i = 8; i < 12; i++) {
            gameMap.addEntity(new Enemy2(1000, 600, gameMap, 5, 1, gameMap.getEntities(), "" + i, client));
            playerIds.add("" + i);
        }

        this.client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Player) {
                    if (((Player) object).id.equals(gameMap.getPlayer().getId())) {
                        gameMap.getPlayer().setPosX(((Player) object).x);
                        gameMap.getPlayer().setPosY(((Player) object).y);
                    } else {
                        OtherPlayer otherPlayer = new OtherPlayer(((Player) object).x, ((Player) object).y, gameMap, ((Player) object).lives, ((Player) object).id, getPlayerType(((Player) object).playerType));
                        gameMap.addEntity(otherPlayer);
                    }
                }
                if (object instanceof Move) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity instanceof OtherPlayer && entity.getId().equals(((Move) object).id)) {
                            entity.setPosX(((Move) object).x);
                            entity.setPosY(((Move) object).y);
                            ((OtherPlayer) entity).setTexture(((Move) object).texture);
                        }
                    }
                }

                if (object instanceof Gun) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity instanceof OtherPlayer && entity.getId().equals(((Gun) object).id)) {
                            ((OtherPlayer) entity).setGunfire(((Gun) object).gun);
                            ((OtherPlayer) entity).setGunPos(((Gun) object).x);
                        }
                    }
                }

                if (object instanceof Ability) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity instanceof OtherPlayer && entity.getId().equals(((Ability) object).id)) {
                            ((OtherPlayer) entity).setAbility(((Ability) object).texture);
                            ((OtherPlayer) entity).setAbilityX(((Ability) object).x);
                            ((OtherPlayer) entity).setAbilityY(((Ability) object).y);
                        }
                    }
                }

                if (object instanceof Drone) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity instanceof OtherPlayer && entity.getId().equals(((Drone) object).id)) {
                            ((OtherPlayer) entity).setDroneX(((Drone) object).x);
                            ((OtherPlayer) entity).setDroneY(((Drone) object).y);
                        }
                    }
                }

                if (object instanceof SmallDrone) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity instanceof OtherPlayer && entity.getId().equals(((SmallDrone) object).id)) {
                            ((OtherPlayer) entity).setSmallDroneX(((SmallDrone) object).x);
                            ((OtherPlayer) entity).setSmallDroneY(((SmallDrone) object).y);
                            ((OtherPlayer) entity).setSmallDrone(((SmallDrone) object).texture);
                        }
                    }
                }

                if (object instanceof LivesLost) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity.getId().equals(((LivesLost) object).id)) {
                            entity.setLives(((LivesLost) object).lives);
                            entity.setLives(((LivesLost) object).lives);
                        }
                    }
                }

                if (object instanceof Enemy) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity.getId().equals(((Enemy) object).id)) {
                            entity.setPosX(((Enemy) object).x);
                            entity.setPosY(((Enemy) object).y);
                            if (((Enemy) object).lives > 10) {
                                entity.setLives(10);
                            } else {
                                entity.setLives(((Enemy) object).lives);
                            }
                        }
                    }
                }

                if (object instanceof MoveEnemy) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity.getId().equals(((MoveEnemy) object).id)) {
                            entity.setPosX(((MoveEnemy) object).x);
                            entity.setPosY(((MoveEnemy) object).y);
                        }
                    }
                }

                if (object instanceof Death) {
                    for (Entity entity : gameMap.getEntities()) {
                        if (entity.getId().equals(((Death) object).id) && entity.getType().equals(EntityType.PLAYER)) {
                            gameMap.getEntities().remove(entity);
                            break;
                        }
                    }
                }

            }});


        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label.LabelStyle labelStyleTwo = new Label.LabelStyle(font, Color.RED);

        label = new Label(timeStr, labelStyle);
        label.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 50);
        stage.addActor(label);

        corpseTexture = new Texture("corpse.png");

        // --------------   DRAWING  ------------

        // Screen size
        int screenCenterX = Gdx.graphics.getWidth() / 2;
        int screenCenterY = Gdx.graphics.getHeight() / 2;

        // abilities
        initializePlayerType(playerType);


        zSkill = new Image(zSkillTexture);
        zSkill.setSize(75, 75);
        zSkill.setPosition(screenCenterX - 260, screenCenterY - 500);
//        zLabel = new Label(zLabelText, labelStyleTwo);
//        zLabel.setPosition(screenCenterX - 240, screenCenterY - 460);
//        zLabel.addAction(Actions.alpha(0));
//        zLabel.setFontScale(5f);


        xSkill = new Image(xSkillTexture);
        xSkill.setSize(75, 75);
        xSkill.setPosition(screenCenterX - 150, screenCenterY - 500);
        xLabel = new Label(xLabelText, labelStyleTwo);
        xLabel.setPosition(screenCenterX - 130, screenCenterY - 460);
        xLabel.addAction(Actions.alpha(0));
        xLabel.setFontScale(5f);

        cSkill = new Image(cSkillTexture);
        cSkill.setSize(75, 75);
        cSkill.setPosition(screenCenterX - 40, screenCenterY - 500);
        cLabel = new Label(cLabelText, labelStyleTwo);
        cLabel.setPosition(screenCenterX - 20, screenCenterY - 460);
        cLabel.addAction(Actions.alpha(0));
        cLabel.setFontScale(5f);


        vSkill = new Image(vSkillTexture);
        vSkill.setSize(75, 75);
        vSkill.setPosition(screenCenterX + 70, screenCenterY - 500);
        if (!removeV) {
            vLabel = new Label(vLabelText, labelStyleTwo);
            vLabel.setPosition(screenCenterX + 90, screenCenterY - 460);
            vLabel.addAction(Actions.alpha(0));
            vLabel.setFontScale(5f);
        }

        stage.addActor(zSkill);
//        stage.addActor(zLabel);
        stage.addActor(xSkill);
        stage.addActor(xLabel);
        stage.addActor(cSkill);
        stage.addActor(cLabel);
        stage.addActor(vSkill);
        if (!removeV) stage.addActor(vLabel);


        // ------------------- FINISHED DRAWING -------------


        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        random = new Random();
        dead = new ArrayList<>();



    }

    public GameScreen(SpaceEscape escape, PlayerType playerType, Client client) {
        // constructor for testing
        this.client = client;
        this.game = escape;
        initializePlayerType(playerType);
    }

    public EnemyType getEnemyType(String id) {
        switch (id) {
            case "enemy0":
                return EnemyType.ENEMY0;
            default:
                return EnemyType.ENEMY1;
        }
    }

    public void initializePlayerType(PlayerType playerType) {
        if (playerType == PlayerType.PLAYER0) {
            zSkillTexture = new Texture("PlayerAbilities/Player0/zSkill.png");
            xSkillTexture = new Texture("PlayerAbilities/Player0/xSkill.png");
            cSkillTexture = new Texture("PlayerAbilities/Player0/cSkill.png");
            vSkillTexture = new Texture("PlayerAbilities/Player0/vSkill.png");

            xCooldownTime = 4;
            xLabelText = "";
            cCooldownTime = 3;
            cLabelText = "";
            vCooldownTime = 5;
            vLabelText = "";
            bombShake = true;
        } else if (playerType == PlayerType.PLAYER1) {
            zSkillTexture = new Texture("PlayerAbilities/Player1/zSkill.png");
            xSkillTexture = new Texture("PlayerAbilities/Player1/xSkill.png");
            cSkillTexture = new Texture("PlayerAbilities/Player1/cSkill.png");
            vSkillTexture = new Texture("PlayerAbilities/Player1/vSkill.png");

            xCooldownTime = 4;
            xLabelText = "";
            cCooldownTime = 8;
            cLabelText = "";
            removeV = true;
        } else if (playerType == PlayerType.PLAYER2) {
            zSkillTexture = new Texture("PlayerAbilities/Player2/zSkill.png");
            xSkillTexture = new Texture("PlayerAbilities/Player2/xSkill.png");
            cSkillTexture = new Texture("PlayerAbilities/Player2/cSkill.png");
            vSkillTexture = new Texture("PlayerAbilities/Player2/vSkill.png");

            xCooldownTime = 4;
            xLabelText = "";
            cCooldownTime = 6;
            cLabelText = "";
            vCooldownTime = 5;
            vLabelText = "";
            droneShake = true;
        } else {
            zSkillTexture = new Texture("PlayerAbilities/Player3/zSkill.png");
            xSkillTexture = new Texture("PlayerAbilities/Player3/xSkill.png");
            cSkillTexture = new Texture("PlayerAbilities/Player3/cSkill.png");
            vSkillTexture = new Texture("PlayerAbilities/Player3/vSkill.png");

            xCooldownTime = 4;
            xLabelText = "";
            cCooldownTime = 4;
            cLabelText = "";
            vCooldownTime = 5;
            vLabelText = "";
            bombShake = true;
            cHasToBeGrounded = true;
        }
    }

    public PlayerType getPlayerType(String id) {
        switch (id) {
            case "character0":
                return PlayerType.PLAYER0;
            case "character1":
                return PlayerType.PLAYER1;
            case "character2":
                return PlayerType.PLAYER2;
            default:
                return PlayerType.PLAYER3;
        }
    }


    @Override
    public void render(float delta) {
        if (!gameMap.getEntities().contains(me)) {
            game.setScreen(new EndScreen(game));
            dispose();
        }

        camera.update();

        Gdx.gl.glClearColor(35 / 255f, 34 / 255f, 47 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // current time
        deltaTime += Gdx.graphics.getDeltaTime();
        currentTime = System.currentTimeMillis();


        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, game.batch);


        // stage
        stage.act(Gdx.graphics.getDeltaTime());


        timeStr = Double.toString(Math.round(deltaTime * 100.0) / 100.0);
        label.setText(timeStr);

//        zLabelText = String.valueOf(Math.round(zCoolDownPoint + zCooldownTime - deltaTime));
//        zLabel.setText(zLabelText);
//        if (Integer.parseInt(zLabelText) < 0) {
//            zLabel.addAction(Actions.alpha(0));
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
//            if (deltaTime > zCoolDownPoint + zCooldownTime) {
//                zLabel.addAction(Actions.alpha(1));
//                zSkill.addAction(Actions.alpha(0));
//                zSkill.addAction(Actions.fadeIn(5f));
//                zCoolDownPoint = deltaTime;
//            }
//        }

        xLabelText = String.valueOf(Math.round(xCoolDownPoint + xCooldownTime - deltaTime));
        xLabel.setText(xLabelText);
        if (Integer.parseInt(xLabelText) < 0) {
            xLabel.addAction(Actions.alpha(0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            if (deltaTime > xCoolDownPoint + xCooldownTime) {
                xLabel.addAction(Actions.alpha(1));
                xSkill.addAction(Actions.alpha(0));
                xSkill.addAction(Actions.fadeIn(4f));
                xCoolDownPoint = deltaTime;
                if (bombShake) gonnaShake = true;
            }
        }


        cLabelText = String.valueOf(Math.round(cCoolDownPoint + cCooldownTime - deltaTime));
        cLabel.setText(cLabelText);
        if (Integer.parseInt(cLabelText) < 0) {
            cLabel.addAction(Actions.alpha(0));
        }
        if (!cHasToBeGrounded) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                if (deltaTime > cCoolDownPoint + cCooldownTime) {
                    cLabel.addAction(Actions.alpha(1));
                    cSkill.addAction(Actions.alpha(0));
                    cSkill.addAction(Actions.fadeIn(3f));
                    cCoolDownPoint = deltaTime;
                    if (droneShake) gonnaShake = true;
                }
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.C) && me.isGrounded()) {
                if (deltaTime > cCoolDownPoint + cCooldownTime) {
                    cLabel.addAction(Actions.alpha(1));
                    cSkill.addAction(Actions.alpha(0));
                    cSkill.addAction(Actions.fadeIn(3f));
                    cCoolDownPoint = deltaTime;
                    if (droneShake) gonnaShake = true;
                }
            }
        }

        if (!removeV) {
            vLabelText = String.valueOf(Math.round(vCoolDownPoint + vCooldownTime - deltaTime));
            vLabel.setText(vLabelText);
            if (Integer.parseInt(vLabelText) < 0) {
                vLabel.addAction(Actions.alpha(0));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
                if (deltaTime > vCoolDownPoint + vCooldownTime) {
                    vLabel.addAction(Actions.alpha(1));
                    vSkill.addAction(Actions.alpha(0));
                    vSkill.addAction(Actions.fadeIn(5f));
                    vCoolDownPoint = deltaTime;
                }
            }
        }



        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        stage.draw();



        // CAMERA SHAKING OR STATIC
        if (gonnaShake) {
            if (bombShake) {
                if (deltaTime >= xCoolDownPoint + 2) {
                    shaking = true;
                }
            } else if (droneShake) {
                if (deltaTime >= cCoolDownPoint + 2) {
                    shaking = true;
                }
            }
        }
        if (shaking) {
            camera.position.x = Math.round(gameMap.getPlayer().getX()
                    + (random.nextInt(shakeIntensityRange) - shakeIntensityBuffer));
            camera.position.y = Math.round(gameMap.getPlayer().getY()
                    + (random.nextInt(shakeIntensityRange) - shakeIntensityBuffer));
            if (bombShake) {
                if (deltaTime >= xCoolDownPoint + 2 + shakingTime) {
                    gonnaShake = false;
                    shaking = false;
                }
            } else if (droneShake) {
                if (deltaTime >= cCoolDownPoint + 2 + shakingTime) {
                    gonnaShake = false;
                    shaking = false;
                }
            }
        } else {
            camera.position.x = Math.round(gameMap.getPlayer().getX());
            camera.position.y = Math.round(gameMap.getPlayer().getY());
        }
        camera.update();



//        // REMOVING THE DEAD
//        for (Entity entity : gameMap.getEntities()) {
//            if (entity.getLives() <= 0) dead.add(entity);
//        }
//        System.out.println(dead);
//        for (Entity corpse : dead) {
//            game.batch.draw(corpseTexture, corpse.getX(), corpse.getY());
//        }
//        for (Entity deadEntity : dead) {
//            gameMap.removeEntity(deadEntity);
//        }
//        dead.clear();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
