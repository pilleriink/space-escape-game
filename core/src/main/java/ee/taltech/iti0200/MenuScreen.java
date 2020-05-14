
package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.entities.*;
import ee.taltech.iti0200.server.GameServer;
import ee.taltech.iti0200.server.packets.*;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MenuScreen implements Screen {

    private SpaceEscape game;
    private Client client;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture img0, img1, img2, img3, img0Hover, img1Hover, img2Hover, img3Hover, chooseCharacter, chooseBackground;
    private int positionX;
    private PlayerType playerType;

    public MenuScreen(final SpaceEscape game) {
        this.game = game;

        client = new Client();
        client.start();
        try {
            client.connect(5000, "64.227.126.245", 5200, 5201);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                new GameServer();
                client.connect(5000, InetAddress.getLocalHost(), 5200, 5201);
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        client.getKryo().register(Register.class);
        client.getKryo().register(Move.class);
        client.getKryo().register(LivesLost.class);
        client.getKryo().register(Player.class);
        client.getKryo().register(ArrayList.class);
        client.getKryo().register(Gun.class);
        client.getKryo().register(Enemy.class);
        client.getKryo().register(MoveEnemy.class);
        client.getKryo().register(Death.class);
        client.getKryo().register(Ability.class);
        client.getKryo().register(Drone.class);
        client.getKryo().register(SmallDrone.class);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = game.batch;

        img0 = new Texture("character0.png");
        img1 = new Texture("character1.png");
        img2 = new Texture("character2.png");
        img3 = new Texture("character3.png");

        img0Hover = new Texture("character0_hover.png");
        img1Hover = new Texture("character1_hover.png");
        img2Hover = new Texture("character2_hover.png");
        img3Hover = new Texture("character3_hover.png");

        chooseCharacter = new Texture("choose_character.png");
        chooseBackground = new Texture("choosebackground.png");
        positionX = 0;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && positionX > 0) positionX--;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && positionX < 3) positionX++;


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(chooseBackground, (float) (-0.5 * chooseBackground.getWidth()), (float) (-0.5 * chooseBackground.getHeight()));

        batch.draw(chooseCharacter, (float) (-0.5 * chooseCharacter.getWidth()), chooseCharacter.getHeight() * 6);

        batch.draw(img0, -img0.getWidth() * 2 - 200, (float) (-img0.getHeight() / 1.5));
        batch.draw(img1, -img1.getWidth() - 75, (float) (-img1.getHeight() / 1.5));
        batch.draw(img2, 75, (float) (-img2.getHeight() / 1.5));
        batch.draw(img3, img3.getWidth() + 200, (float) (-img3.getHeight() / 1.5));

        switch (positionX) {
            case 0:
                batch.draw(img0Hover, -img0Hover.getWidth() * 2 - 200, (float) (-img0Hover.getHeight() / 1.5));
                playerType = PlayerType.PLAYER0;
                break;
            case 1:
                batch.draw(img1Hover, -img1Hover.getWidth() - 75, (float) (-img1Hover.getHeight() / 1.5));
                playerType = PlayerType.PLAYER1;
                break;
            case 2:
                batch.draw(img2Hover, 75, (float) (-img2Hover.getHeight() / 1.5));
                playerType = PlayerType.PLAYER2;
                break;
            case 3:
                batch.draw(img3Hover, img3Hover.getWidth() + 200, (float) (-img3Hover.getHeight() / 1.5));
                playerType = PlayerType.PLAYER3;
                break;
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Register register = new Register();
            register.id = LocalDateTime.now().toString();
            register.playerType = playerType.getId();
            game.setScreen(new GameScreen(game, playerType, client, register.id));
            client.sendTCP(register);
            dispose();
        }


    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
