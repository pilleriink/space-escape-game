package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.world.GameMap;

import java.util.ArrayList;


public class Player extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;

    private ArrayList<Entity> entities;

    private Texture gunLeft, gunRight;
    private NinePatch health;
    private float totalHealth, shootingRange, lastX;
    private boolean isRight, shoot, moving, keyPressed;
    private int shootingTime, movingTime, jumpingPower;
    private PlayerType playerType;

    public Player(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, PlayerType playerType) {
        super(x, y, EntityType.PLAYER, map, lives);
        this.entities = entities;
        this.shootingRange = shootingRange;
        this.totalHealth = getLives();
        this.gunLeft = new Texture("gunfireleft.png");
        this.gunRight = new Texture("gunfire.png");
        this.lastX = getX();
        this.playerType = playerType;
        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);
    }

    public float getLives() {
        return this.lives;
    }

    public void jump(float deltaTime, float gravity) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
            keyPressed = true;
            jumpingPower += 1;
        }
        if (jumpingPower > 10 || !Gdx.input.isKeyPressed(Input.Keys.SPACE) && keyPressed && grounded) {
            keyPressed = false;
            this.velocityY += JUMP_VELOCITY * getWeight() * jumpingPower / 10;
            jumpingPower = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0) {
            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
        }
        super.update(deltaTime, gravity); // applies the gravity
    }

    public void moveLeft(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                moveX(-SPEED * 2 * deltaTime);
            } else {
                moveX(-SPEED * deltaTime);
            }
            isRight = false;
        }
    }

    public void moveRight(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                moveX(SPEED * 2 * deltaTime);
            } else {
                moveX(SPEED * deltaTime);
            }
            isRight = true;
        }
    }

    public void shoot() {
        for (Entity entity : entities) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                shoot = true;
                shootingTime = 0;
                if (isRight
                        && entity.getX() <= getX() + getWidth() + shootingRange
                        && getY() + 0.5 * getHeight() >= entity.getY()
                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
                        && entity.getLives() > 0) {
                    entity.setLives(entity.getLives() - 1);
                } else if (!isRight
                        && entity.getX() + entity.getWidth() >= getX() - shootingRange
                        && getY() + 0.5 * getHeight() >= entity.getY()
                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
                        && entity.getLives() > 0) {
                    entity.setLives(entity.getLives() - 1);
                }
            }
        }
    }

    @Override
    public void update(float deltaTime, float gravity) {
        shootingTime += 1;
        jump(deltaTime, gravity);
        if (!keyPressed) {
            moveLeft(deltaTime);
            moveRight(deltaTime);
        }
        shoot();
        if (shootingTime > 5) { shoot = false; }
        if (getX() != lastX) {
            movingTime += 1;
            if (movingTime > playerType.getRunningRight().size() - 1) {
                movingTime = 0;
            }
            moving = true;
            lastX = getX();
        } else {
            moving = false;
            movingTime = 0;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (keyPressed) {
            if (isRight) {
                batch.draw(playerType.getRightJumpingUp(), pos.x, pos.y, getWidth(), getHeight());
            } else batch.draw(playerType.getLeftJumpingUp(), pos.x, pos.y, getWidth(), getHeight());
        }
        else {
            if (!moving || !grounded) {
                if (isRight) {
                    batch.draw(playerType.getStandingRight(), pos.x, pos.y, getWidth(), getHeight());
                } else batch.draw(playerType.getStandingLeft(), pos.x, pos.y, getWidth(), getHeight());
            } else {
                if (isRight) {
                    batch.draw(playerType.getRunningRight().get(movingTime), pos.x, pos.y, getWidth(), getHeight());
                } else batch.draw(playerType.getRunningLeft().get(movingTime), pos.x, pos.y, getWidth(), getHeight());
            }
        }
        health.draw(batch, pos.x, pos.y + 40, (getLives() / this.totalHealth) * getWidth(), 3);
        if (shoot) {
            if (isRight) {
                batch.draw(gunRight, pos.x + getWidth(), pos.y + getHeight() / 4, 5, 5);
            } else {
                batch.draw(gunLeft, pos.x - 5, pos.y + getHeight() / 4, 5, 5);
            }
        }
    }

}
