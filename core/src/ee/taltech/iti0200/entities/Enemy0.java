package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.SpaceEscape;
import ee.taltech.iti0200.server.packets.Death;
import ee.taltech.iti0200.server.packets.LivesLost;
import ee.taltech.iti0200.server.packets.MoveEnemy;
import ee.taltech.iti0200.world.GameMap;
import org.w3c.dom.Text;

import java.awt.geom.FlatteningPathIterator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Enemy0 extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private int time, movingTime;
    private float movementTime, shootingRange, totalHealth;;

    private ArrayList<Entity> entities;
    private boolean isRight, shoot;
    private Entity followed;
    private EnemyType enemyType = EnemyType.ENEMY0;
    private EntityType entityType = EntityType.ENEMY0;
    final Client client;
    String id, texture, gunfire;

    public Enemy0(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, String id, Client client) {
        super(x, y, EntityType.ENEMY0, map, lives, id);
        this.id = id;
        this.client = client;

        this.shootingRange = shootingRange;
        this.entities = entities;
        this.totalHealth = getLives();
    }

    public String getId() {
        return id;
    }

    public void moveRight(float deltaTime) {
        moveX((float) ((float) SPEED * deltaTime * 0.75));
        isRight = true;
    }

    public void moveLeft(float deltaTIme) {
        moveX((float) (-SPEED * deltaTIme * 0.75));
        isRight = false;
    }

    public void jump() {
        this.velocityY += JUMP_VELOCITY * getWeight() / 20;
    }

    public void shoot() {
        for (Entity entity : entities) {
            if (entity.getLives() > 0 && entity.getType().equals(EntityType.PLAYER)) {
                shoot = true;
                if (entity.getX() <= getX() + getWidth() + shootingRange
                        && getY() + 0.3 * getHeight() >= entity.getY()
                        && getY() + 0.3 * getHeight() <= entity.getY() + entity.getHeight()
                        || entity.getX() + entity.getWidth() >= getX() - shootingRange
                        && getY() + 0.3 * getHeight() >= entity.getY()
                        && getY() + 0.3 * getHeight() <= entity.getY() + entity.getHeight()) {
                    time = 0;
                    entity.setLives(entity.getLives() - 1);
                    LivesLost livesLost = new LivesLost();
                    livesLost.lives = entity.getLives();
                    livesLost.id = entity.getId();
                    client.sendTCP(livesLost);
                    break;
                }
            }
        }
    }

    public void follow(float deltaTime) {

        for (Entity player : entities) {
            if (player.getType().equals(EntityType.PLAYER)
                    && player.getY() >= getY() && player.getY() <= getY() + getHeight()
                    && player.getX() > getX()  - 300 && player.getX() < getX() + 300) {
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

            if (map.doesRectCollideMap(followed.getX(), followed.getY() - 2, followed.getWidth(), followed.getHeight())
                    || followed.getY() < getY() - getHeight() * 2
                    || followed.getX() < getX()  - 500 || followed.getX() > getX() + 500) {
                followed = null;
            }

            MoveEnemy moveEnemy = new MoveEnemy();
            moveEnemy.id = id;
            moveEnemy.x = getX();
            moveEnemy.y = getY();
            client.sendTCP(moveEnemy);
        }
    }

    @Override
    public void update(float deltaTime, float gravity) {
        if (lives == 0) {
            Death death = new Death();
            death.id = id;
            client.sendTCP(death);
        }
        follow(deltaTime);
        movementTime += Gdx.graphics.getDeltaTime();
        movingTime += 1;
        if (movingTime > enemyType.getMovingString().size() - 1) { movingTime = 0; }
        time += 1;
        super.update(deltaTime, gravity); // applies the gravity
        //move(deltaTime);
        shoot();
        if (time > 2) { shoot = false; }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(new Texture(enemyType.getMovingString().get(movingTime)), pos.x, pos.y, getWidth(), getHeight());
        new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0).draw(batch, pos.x, pos.y + getHeight() + 10, (getLives() / this.totalHealth) * getWidth(), 3);
        if (shoot) {
            if (isRight) {
                batch.draw(new Texture("gunfire.png"), pos.x + getWidth() + 2, pos.y + getHeight() / 3, 5, 5);
            } else {
                batch.draw(new Texture("gunfireleft.png"), pos.x - 7, pos.y + getHeight() / 3, 5, 5);
            }
        }
    }



}
