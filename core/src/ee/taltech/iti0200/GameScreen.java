package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TiledGameMap;
import org.w3c.dom.Text;

import java.math.BigInteger;

public class GameScreen implements Screen {
    private SpaceEscape game;

    public Stage stage;
    public float deltaTime;
    public String timeStr = "0";
    public BitmapFont font;
    public Label.LabelStyle labelStyle;
    public Label label;


    public OrthographicCamera camera;

    GameMap gameMap;

    public GameScreen(SpaceEscape game) {


        gameMap = new TiledGameMap();


        this.game = game;


        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        labelStyle = new Label.LabelStyle(font, Color.WHITE);

        label = new Label(timeStr, labelStyle);
        label.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 50);
        stage.addActor(label);



        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deltaTime += Gdx.graphics.getDeltaTime();
        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, game.batch);
        stage.act(Gdx.graphics.getDeltaTime());
        timeStr = Double.toString(Math.round(deltaTime * 100.0) / 100.0);
        label.setText(timeStr);
        stage.draw();
        camera.position.x = Math.round(gameMap.getPlayer().getX());
        camera.position.y = Math.round(gameMap.getPlayer().getY());
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
        stage.dispose();
    }
}
