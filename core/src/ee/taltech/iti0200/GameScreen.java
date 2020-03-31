package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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


    // stage
    public Stage stage;
    public float deltaTime;
    public String timeStr = "0";
    public BitmapFont font;
    public Label.LabelStyle labelStyle;
    public Label label;
    public Image xSkill;
    public Image cSkill;
    public Image vSkill;


    // cooldowns
    public float currentTime;
    public float xCooldownTime = 2;
    public float xCoolDownPoint = 0;
    public int cCooldownTime = 3;
    public float cCoolDownPoint;
    public int vCooldownTime = 5;
    public float vCoolDownPoint;


    public OrthographicCamera camera;

    GameMap gameMap;

    public GameScreen(SpaceEscape game) {


        gameMap = new TiledGameMap();


        this.game = game;


        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label.LabelStyle labelStyleTwo = new Label.LabelStyle(font, Color.BLACK);

        label = new Label(timeStr, labelStyle);
        label.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 50);
        stage.addActor(label);

        // --------------   DRAWING  ------------

        // Screen size
        int screenCenterX = Gdx.graphics.getWidth() / 2;
        int screenCenterY = Gdx.graphics.getHeight() / 2;

        // abilities
        Texture xSkillTexture = new Texture("PlayerAbilities/xSkill.png");
        Texture cSkillTexture = new Texture("PlayerAbilities/cSkill.png");
        Texture vSkillTexture = new Texture("PlayerAbilities/vSkill.png");
        xSkill = new Image(xSkillTexture);
        xSkill.setSize(50, 50);
        xSkill.setPosition(screenCenterX - 75, screenCenterY - 200);

        cSkill = new Image(cSkillTexture);
        cSkill.setSize(50, 50);
        cSkill.setPosition(screenCenterX - 20, screenCenterY - 200);

        vSkill = new Image(vSkillTexture);
        vSkill.setSize(50, 50);
        vSkill.setPosition(screenCenterX + 35, screenCenterY - 200);

        stage.addActor(xSkill);
        stage.addActor(cSkill);
        stage.addActor(vSkill);


        // ------------------- FINISHED DRAWING -------------


        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(35 / 255f, 34 / 255f, 47 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // current time
        deltaTime += Gdx.graphics.getDeltaTime();
        currentTime = System.currentTimeMillis();


        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, game.batch);


        // stage
        stage.act(Gdx.graphics.getDeltaTime());


        timeStr = Double.toString(Math.round(deltaTime * 100.0) / 100.0);
        label.setText(timeStr);

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            if (deltaTime > xCoolDownPoint + xCooldownTime) {
                xSkill.addAction(Actions.alpha(0));
                xSkill.addAction(Actions.fadeIn(2f));
                xCoolDownPoint = deltaTime;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            if (deltaTime > cCoolDownPoint + cCooldownTime) {
                cSkill.addAction(Actions.alpha(0));
                cSkill.addAction(Actions.fadeIn(3f));
                cCoolDownPoint = deltaTime;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            if (deltaTime > vCoolDownPoint + vCooldownTime) {
                vSkill.addAction(Actions.alpha(0));
                vSkill.addAction(Actions.fadeIn(5f));
                vCoolDownPoint = deltaTime;
            }
        }

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
