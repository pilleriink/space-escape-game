package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.entities.PlayerType;
import ee.taltech.iti0200.server.packets.Register;

import java.time.LocalDateTime;


public class StartScreen implements Screen {

    final SpaceEscape game;
    Client client;

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture spaceEscape, multiPlayer, singlePlayer, MPHover, SPHover, background;
    int positionX;
    PlayerType playerType;
    boolean isMP;

    public StartScreen(final SpaceEscape game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        spaceEscape = new Texture("space_escape.png");
        multiPlayer = new Texture("multi_player.png");
        MPHover = new Texture("multi_player_hover.png");
        singlePlayer = new Texture("single_player.png");
        SPHover = new Texture("single_player_hover.png");
        background = new Texture("menubackground.png");
        positionX = 0;
        //Gdx.input.setCursorCatched(true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(background, (float) (-0.5 * background.getWidth()), (float) (-0.5 * background.getHeight()));
        batch.draw(spaceEscape, (float) (-0.5 * spaceEscape.getWidth()), spaceEscape.getHeight() * 4);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game, isMP));
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
