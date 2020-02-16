package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {
    final SpaceEscape game;

    private OrthographicCamera camera;
    private Texture characterImage;
    private Rectangle character;

    public GameScreen(final SpaceEscape game) {
        this.game = game;

        character = new Rectangle();
        characterImage = new Texture("badlogic.jpg");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        character.x = 800 / 2 - 64 / 2;
        character.y = 20;
        character.width = 20;
        character.height = 20;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(characterImage, character.x, character.y);
        game.batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) character.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) character.x += 200 * Gdx.graphics.getDeltaTime();
        if(character.x < 0) character.x = 0;
        if(character.x > 800 - 64) character.x = 800 - 64;
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
        characterImage.dispose();
    }
}
