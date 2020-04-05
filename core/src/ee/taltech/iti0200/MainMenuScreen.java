
package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.entities.Player;
import ee.taltech.iti0200.entities.PlayerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenuScreen implements Screen {

    final SpaceEscape game;

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture img0, img1, img2, img3, img0Hover, img1Hover, img2Hover, img3Hover, chooseCharacter;
    int positionX;
    PlayerType playerType;

    public MainMenuScreen(final SpaceEscape game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        img0 = new Texture("character0.png");
        img1 = new Texture("character1.png");
        img2 = new Texture("character2.png");
        img3 = new Texture("character3.png");

        img0Hover = new Texture("character0_hover.png");
        img1Hover = new Texture("character1_hover.png");
        img2Hover = new Texture("character2_hover.png");
        img3Hover = new Texture("character3_hover.png");

        chooseCharacter = new Texture("choose_character.png");
        positionX = 0;
        Gdx.input.setCursorCatched(true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && positionX > 0) {
            positionX--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && positionX < 3) {
            positionX++;
        }


        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(chooseCharacter, -chooseCharacter.getWidth() / 2, chooseCharacter.getHeight() * 7);

        batch.draw(img0, -img0.getWidth() * 2 - 200, -img0.getHeight() / 2);
        batch.draw(img1, -img1.getWidth() - 75, -img1.getHeight() / 2);
        batch.draw(img2, 75, -img2.getHeight() / 2);
        batch.draw(img3, img3.getWidth() + 200, -img3.getHeight() / 2);

        if (positionX == 0) {
            batch.draw(img0Hover, -img0Hover.getWidth() * 2 - 200, -img0Hover.getHeight() / 2);
            playerType = PlayerType.PLAYER0;
        } else if (positionX == 1) {
            batch.draw(img1Hover, -img1Hover.getWidth() - 75, -img1Hover.getHeight() / 2);
            playerType = PlayerType.PLAYER1;
        } else if (positionX == 2) {
            batch.draw(img2Hover, 75, -img2Hover.getHeight() / 2);
            playerType = PlayerType.PLAYER2;
        } else if (positionX == 3) {
            batch.draw(img3Hover, img3Hover.getWidth() + 200, -img3Hover.getHeight() / 2);
            playerType = PlayerType.PLAYER3;
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game, playerType));
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
