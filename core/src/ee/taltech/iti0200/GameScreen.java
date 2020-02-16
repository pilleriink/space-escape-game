package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {
    final SpaceEscape game;
    final Player player;

    private OrthographicCamera camera;

    public GameScreen(final SpaceEscape game) {
        this.game = game;
        this.player = new Player(Gdx.files.internal("badlogic.jpg"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        player.setCharacterSize();
        player.setCharacterStartingPoint();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(player.getCharacterImage(), player.getObject().x, player.getObject().y);
        game.batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.getObject().x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.getObject().x += 200 * Gdx.graphics.getDeltaTime();
        if(player.getObject().x < 0) player.getObject().x = 0;
        if(player.getObject().x > 800 - 64) player.getObject().x = 800 - 64;
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
        player.getCharacterImage().dispose();
    }
}
