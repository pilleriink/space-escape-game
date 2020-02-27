package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TiledGameMap;

public class GameScreen implements Screen {
    private SpaceEscape game;
//    private Player player;
//    private Physics1 physics;

    public OrthographicCamera camera;

    GameMap gameMap;

    public GameScreen(SpaceEscape game) {
        this.game = game;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.x = 40;
        camera.position.y = 300;
        //        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        gameMap = new TiledGameMap();



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, game.batch);
        camera.position.x = gameMap.getPlayer().getX();
        camera.position.y = gameMap.getPlayer().getY();
        camera.update();

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

    }
}
