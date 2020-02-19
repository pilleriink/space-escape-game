package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {
    final SpaceEscape game;
    final Player player;
    final Physics physics;

    private OrthographicCamera camera;

    public GameScreen(SpaceEscape game) {
        this.game = game;
        this.physics = new Physics();

        this.player = new Player(Gdx.files.internal("box.jpg"), 50, 50);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1800, 900);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(player.getCharacterImage(), player.getObject().getX(), player.getObject().getY());
        game.batch.end();
        physics.moveLeftAndRight(player);
        physics.moveUpAndDown(player);
        player.boundsLeftAndRight(camera);
        player.boundsUpAndDown(camera);
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
