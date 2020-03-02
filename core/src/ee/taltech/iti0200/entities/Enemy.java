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

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Enemy extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private float time;

    Texture image;
    float totalHealth, shootingRange;
    NinePatch health;
    ArrayList<Entity> entities;
    boolean isRight;

    public Enemy(float x, float y, GameMap map, Texture image, float lives, float shootingRange, ArrayList<Entity> entities) {
        super(x, y, EntityType.PLAYER, map, lives);
        this.image = image;
        this.time = 0;
        this.shootingRange = shootingRange;
        this.entities = entities;
        this.totalHealth = getLives();
        this.isRight = true;
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
            this.isRight = true;
        }else {
            moveX(-SPEED * deltaTime);
            this.isRight = false;
        }

        for (Entity entity : entities) {
            if (entity.getLives() > 0) {
                if (isRight && entity.getX() <= getX() + shootingRange && entity.getX() > getX()) {
                    entity.setLives(entity.getLives() - 1);
                } else if (!isRight && entity.getX() >= getX() + shootingRange && entity.getX() < getX()) {
                    entity.setLives(entity.getLives() - 1);
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
        health.draw(batch, pos.x, pos.y + 40, (getLives() / this.totalHealth) * getWidth(), 3);
    }

}
