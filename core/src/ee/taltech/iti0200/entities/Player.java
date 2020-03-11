package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.world.GameMap;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class Player extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;

    private ArrayList<Entity> entities;

    private Texture image, gunLeft, gunRight;
    private NinePatch health;
    private float totalHealth, shootingRange;
    private boolean isRight, shoot;
    private int time;

    public Player(float x, float y, GameMap map, Texture image, float lives, float shootingRange, ArrayList<Entity> entities) {
        super(x, y, EntityType.PLAYER, map, lives);
        this.image = image;
        this.entities = entities;
        this.isRight = true;
        this.shoot = false;
        this.shootingRange = shootingRange;
        this.totalHealth = getLives();
        this.gunLeft = new Texture("gunfireleft.png");
        this.gunRight = new Texture("gunfire.png");
        this.time = 0;
        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);
    }

    public float getLives() {
        return this.lives;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        time += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
            this.velocityY += JUMP_VELOCITY * getWeight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0) {
            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
        }
        super.update(deltaTime, gravity); // applies the gravity

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                moveX(-SPEED * 2 * deltaTime);
            } else {
                moveX(-SPEED * deltaTime);
            }
            isRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                moveX(SPEED * 2 * deltaTime);
            } else {
                moveX(SPEED * deltaTime);
            }
            isRight = true;
        }
        for (Entity entity : entities) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                shoot = true;
                time = 0;
                if (isRight && entity.getX() <= getX() + shootingRange && entity.getX() > getX()
                        && getY() >= entity.getY() && getY() <= entity.getY() + entity.getHeight()
                        && entity.getLives() > 0) {
                    entity.setLives(entity.getLives() - 1);
                } else if (!isRight && entity.getX() >= getX() + shootingRange && entity.getX() < getX()
                        && getY() >= entity.getY() && getY() <= entity.getY() + entity.getHeight()
                        && entity.getLives() > 0) {
                    entity.setLives(entity.getLives() - 1);
                }
            }
        }
        if (time > 5) {
            shoot = false;
        }
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
