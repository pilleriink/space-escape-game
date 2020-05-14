package ee.taltech.iti0200.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.server.packets.Death;
import ee.taltech.iti0200.server.packets.LivesLost;
import ee.taltech.iti0200.server.packets.MoveEnemy;
import ee.taltech.iti0200.world.GameMap;

import java.util.ArrayList;

public class FirstEnemy extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private static final double MOVING_SPEED = 0.75;
    private static final int JUMPING_SPEED = 20;
    private int time, movingTime;
    private float shootingRange, totalHealth;

    public ArrayList<Entity> entities;
    private boolean isRight, shoot;
    private Entity followed;
    private EnemyType enemyType = EnemyType.ENEMY0;
    private final Client client;

    public FirstEnemy(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities,
                      String id, Client client) {
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
        moveX((float) ((float) SPEED * deltaTime * MOVING_SPEED));
        isRight = true;
    }

    public void moveLeft(float deltaTIme) {
        moveX((float) (-SPEED * deltaTIme * MOVING_SPEED));
        isRight = false;
    }

    public void jump() {
        this.velocityY += JUMP_VELOCITY * getWeight() / JUMPING_SPEED;
    }

    public void shoot() {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.getLives() > 0 && entity.getType().equals(EntityType.PLAYER)) {
                shoot = true;
                if (isRight && entity.getX() > getX() && entity.getX() <= getX() + getWidth() + shootingRange
                        && getY() + 0.3 * getHeight() >= entity.getY()
                        && getY() + 0.3 * getHeight() <= entity.getY() + entity.getHeight()
                        || !isRight && entity.getX() < getX() && entity.getX() + entity.getWidth() >= getX()
                        - shootingRange
                        && getY() + 0.3 * getHeight() >= entity.getY()
                        && getY() + 0.3 * getHeight() <= entity.getY() + entity.getHeight()) {
                    time = 0;
                    entity.setLives(entity.getLives() - 1);
                    LivesLost livesLost = new LivesLost();
                    livesLost.lives = entity.getLives();
                    livesLost.id = entity.getId();
                    client.sendUDP(livesLost);

                    synchronized (client) {
                        try {
                            client.wait(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                }
            }
        }
    }

    public void follow(float deltaTime) {
        for (Entity player : entities) {
            if (player.getType().equals(EntityType.PLAYER)
                    && player.getY() >= getY() && player.getY() <= getY() + getHeight()
                    && player.getX() > getX() - 300 && player.getX() < getX() + 300) {
                followed = player;
                break;
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

            if (followed.getY() < getY() - getHeight() * 2 || followed.getY() > getY() + getHeight() * 2
                    || followed.getX() < getX() - 500 || followed.getX() > getX() + 500) {
                followed = null;
            }
            MoveEnemy moveEnemy = new MoveEnemy();
            moveEnemy.id = id;
            moveEnemy.x = getX();
            moveEnemy.y = getY();
            client.sendUDP(moveEnemy);

            synchronized (client) {
                try {
                    client.wait(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(float deltaTime, float gravity) {
        if (lives < 1) {
            Death death = new Death();
            death.id = id;
            client.sendTCP(death);
        }
        follow(deltaTime);
        movingTime += 1;
        if (movingTime > enemyType.getMovingString().size() - 1) {
            movingTime = 0;
        }
        time += 1;
        super.update(deltaTime, gravity);
        shoot();
        if (time > 2) {
            shoot = false;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(new Texture(enemyType.getMovingString().get(movingTime)), pos.x, pos.y, getWidth(), getHeight());
        new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0).draw(batch, pos.x,
                pos.y + getHeight() + 10, (getLives() / this.totalHealth) * getWidth(), 3);
        if (shoot) {
            if (isRight) {
                batch.draw(new Texture("gunfire.png"), pos.x + getWidth() + 2,
                        pos.y + getHeight() / 3, 5, 5);
            } else {
                batch.draw(new Texture("gunfireleft.png"), pos.x - 7, pos.y + getHeight() / 3,
                        5, 5);
            }
        }
    }

}
