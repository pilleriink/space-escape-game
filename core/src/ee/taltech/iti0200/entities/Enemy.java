package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.SpaceEscape;
import ee.taltech.iti0200.world.GameMap;


public class Enemy extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private float time;

    Texture image;
    float lives, totalHealth;
    NinePatch health;

    public Enemy(float x, float y, GameMap map, Texture image, float lives) {
        super(x, y, EntityType.PLAYER, map);
        this.image = image;
        this.time = 0;
        this.totalHealth = lives;
        this.lives = lives;
        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        setTime(getTime() + Gdx.graphics.getDeltaTime());
        super.update(deltaTime, gravity); // applies the gravity
        if ((int) getTime() % 2 == 0) {
            moveX(SPEED * deltaTime);
        }else {
            moveX(-SPEED * deltaTime);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) && this.lives > 0) {
            this.lives -= 1;
            System.out.println(this.lives);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
        health.draw(batch, pos.x, pos.y + 40, (this.lives / this.totalHealth) * getWidth(), 3);
    }

}
