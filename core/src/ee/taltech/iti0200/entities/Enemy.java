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
    private int time;
    private float movementTime;
    private Texture image, gunLeft, gunRight;
    private float totalHealth, shootingRange;
    private NinePatch health;
    private ArrayList<Entity> entities;
    private boolean isRight, shoot;

    public Enemy(float x, float y, GameMap map, Texture image, float lives, float shootingRange, ArrayList<Entity> entities) {
        super(x, y, EntityType.ENEMY, map, lives);
        this.image = image;
        this.time = 0;
        this.movementTime = 0;
        this.shootingRange = shootingRange;
        this.entities = entities;
        this.totalHealth = getLives();
        this.isRight = true;
        this.shoot = false;
        this.gunLeft = new Texture("gunfireleft.png");
        this.gunRight = new Texture("gunfire.png");
        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);
    }

    public void move(float deltaTime) {
        if ((int) movementTime % 2 == 0) {
            moveX(SPEED * deltaTime);
            this.isRight = true;
        } else {
            moveX(-SPEED * deltaTime);
            this.isRight = false;
        }
    }

    public void shoot() {
        for (Entity entity : entities) {
            if (entity.getLives() > 0 && entity.getClass() == Player.class) {
                shoot = true;
                if (isRight
                        && entity.getX() <= getX() + getWidth() + shootingRange
                        && getY() + 0.5 * getHeight() >= entity.getY()
                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()) {
                    time = 0;
                    entity.setLives(entity.getLives() - 1);
                } else if (!isRight
                        && entity.getX() + entity.getWidth() >= getX() - shootingRange
                        && getY() + 0.5 * getHeight() >= entity.getY()
                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()) {
                    time = 0;
                    entity.setLives(entity.getLives() - 1);
                }
            }
        }
    }

    @Override
    public void update(float deltaTime, float gravity) {
        movementTime += Gdx.graphics.getDeltaTime();
        time += 1;
        super.update(deltaTime, gravity); // applies the gravity
        move(deltaTime);
        shoot();
        if (time > 2) { shoot = false; }

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
        health.draw(batch, pos.x, pos.y + 40, (getLives() / this.totalHealth) * getWidth(), 3);
        if (shoot) {
            if (isRight) {
                batch.draw(gunRight, pos.x + getWidth() + 2, pos.y + getHeight() / 3, 10, 10);
            } else {
                batch.draw(gunLeft, pos.x - 12, pos.y + getHeight() / 3, 10, 10);
            }
        }
    }

}
