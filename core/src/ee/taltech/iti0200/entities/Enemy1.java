package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.world.GameMap;

import java.util.ArrayList;

public class Enemy1 extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private int time, movingTime;
    private float movementTime, shootingRange, totalHealth;
    private NinePatch health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);
    private ArrayList<Entity> entities;
    private boolean isRight;
    private EnemyType enemyType = EnemyType.ENEMY1;
    private Entity followed;

    public Enemy1(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities) {
        super(x, y, EntityType.ENEMY1, map, lives);
        this.shootingRange = shootingRange;
        this.entities = entities;
        this.totalHealth = getLives();
    }

    public void moveRight(float deltaTime) {
        moveX((float) ((float) SPEED * deltaTime));
        isRight = true;
    }

    public void moveLeft(float deltaTIme) {
        moveX((float) (-SPEED * deltaTIme));
        isRight = false;
    }

    public void jump() {
        this.velocityY += JUMP_VELOCITY * getWeight() / 20;
    }

    public void shoot() {
        for (Entity entity : entities) {
            if (entity.getLives() > 0 && entity.getType().equals(EntityType.PLAYER)) {
                if (isRight
                        && entity.getX() <= getX() + getWidth() + shootingRange
                        && getY() + 0.3 * getHeight() >= entity.getY()
                        && getY() + 0.3 * getHeight() <= entity.getY() + entity.getHeight()) {
                    time = 0;
                    entity.setLives(entity.getLives() - 1);
                } else if (!isRight
                        && entity.getX() + entity.getWidth() >= getX() - shootingRange
                        && getY() + 0.3 * getHeight() >= entity.getY()
                        && getY() + 0.3 * getHeight() <= entity.getY() + entity.getHeight()) {
                    entity.setLives(entity.getLives() - 1);
                }
            }
        }
    }

    public void follow(float deltaTime) {
        for (Entity player : entities) {
            if (player.getType().equals(EntityType.PLAYER)
                    && player.getY() >= getY() && player.getY() <= getY() + getHeight()
                    && player.getX() > getX() && player.getX() < getX() + 100 || player.getX() < getX() && player.getX() > getX() - 100) {
                followed = player;
            }
        }

        if (followed != null) {
            if (isRight && map.doesRectCollideMap(getX() + 5, getY(), getWidth(), getHeight())
                    || !isRight && map.doesRectCollideMap(getX() - 5, getY(), getWidth(), getHeight())) {
                jump();
            }

            if (followed.getX() > getX()) {
                moveRight(deltaTime);
            } else if (followed.getX() < getX()) {
                moveLeft(deltaTime);
            }

        }
    }

    @Override
    public void update(float deltaTime, float gravity) {
        follow(deltaTime);
        movementTime += Gdx.graphics.getDeltaTime();
        movingTime += 1;
        if (movingTime > enemyType.getMoving().size() - 1) { movingTime = 0; }
        time += 1;
        super.update(deltaTime, gravity); // applies the gravity
        //move(deltaTime);
        shoot();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(enemyType.getMoving().get(movingTime), pos.x, pos.y, getWidth(), getHeight());
        health.draw(batch, (float) (pos.x + 0.25 * getWidth()), pos.y + getHeight() + 10, (getLives() / this.totalHealth) * getWidth() / 2, 3);

    }
}
