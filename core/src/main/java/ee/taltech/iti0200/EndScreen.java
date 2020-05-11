package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

public class EndScreen implements Screen {

    final SpaceEscape game;

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture spaceEscape, background, tryAgain, tryAgainHover, exitGame, exitGameHover;
    int positionX;
    boolean isMP;

    public EndScreen(final SpaceEscape game) {
        this.game = game;

        batch = new SpriteBatch();

        spaceEscape = new Texture("game_over.png");
        background = new Texture("end_screen.png");

        tryAgain = new Texture("try_again.png");
        tryAgainHover = new Texture("try_again_hover.png");
        exitGame = new Texture("exit_game.png");
        exitGameHover = new Texture("exit_game_hover.png");

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

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(background, (float) (-0.5 * background.getWidth()), (float) (-0.5 * background.getHeight()));
        batch.draw(spaceEscape, (float) (-0.5 * spaceEscape.getWidth()), spaceEscape.getHeight() * 2);

        if (positionX == 0) {
            batch.draw(exitGame, (float) (-1.25 * exitGame.getWidth()), (float) (-1.5 * exitGame.getHeight()));
            batch.draw(tryAgainHover, (float) (0.25 * tryAgainHover.getWidth()), (float) (-1.5 * exitGame.getHeight()));
        } else {
            batch.draw(exitGameHover, (float) (-1.25 * exitGame.getWidth()), (float) (-1.5 * exitGame.getHeight()));
            batch.draw(tryAgain, (float) (0.25 * tryAgainHover.getWidth()), (float) (-1.5 * exitGame.getHeight()));
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && positionX == 0) {
            positionX = 1;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && positionX == 1) {
            positionX = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (positionX == 0) {
                Gdx.app.exit();
            } else {
                game.setScreen(new MenuScreen(game));
            }
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
