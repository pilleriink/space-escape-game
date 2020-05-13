package ee.taltech.iti0200;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TiledGameMap;

public class SpaceEscape extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    GameMap gameMap;

    public SpaceEscape() {

    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameMap = new TiledGameMap();
        this.setScreen(new StartScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        gameMap.dispose();
    }
}
