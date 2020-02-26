package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.LinkedList;
import java.util.List;

public class GameScreen implements Screen {
    private SpaceEscape game;
    private List<Player> players;
    private List<Enemy> enemies;
    private Player player;
    private Enemy enemy;

    private OrthographicCamera camera;

    public GameScreen(SpaceEscape game) {
        this.game = game;

        this.player = new Player(Gdx.files.internal("box.jpg"), 50, 50);
        this.enemy = new Enemy(Gdx.files.internal("enemy.jpg"), 50, 50);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1800, 900);

        players = new LinkedList<>();
        players.add(player);
        enemies = new LinkedList<>();
        enemies.add(enemy);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        for (Player player1 : players) {
            player1.move();
            player1.boundsLeftAndRight(camera);
            player1.boundsUpAndDown(camera);
        }
        enemy.move();
        enemy.boundsLeftAndRight(camera);
        enemy.boundsUpAndDown(camera);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.draw(player.getCharacterImage(), player.getObject().getX(), player.getObject().getY());
        game.batch.draw(enemy.getCharacterImage(), enemy.getObject().getX(), enemy.getObject().getY());
        game.batch.end();
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
