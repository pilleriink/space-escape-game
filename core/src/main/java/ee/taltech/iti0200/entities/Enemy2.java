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

public class Enemy2 extends Entity {

    private static int SPEED = 50;
    private static final int JUMP_VELOCITY = 5;
    private int time, movingTime;
    private float shootingRange, totalHealth;
    private boolean isTooClose = false;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> oldTargets = new ArrayList<>();
    private ArrayList<Entity> target = new ArrayList<>();
    private boolean isRight, shoot;
    private Entity followed;
    private EnemyType enemyType = EnemyType.ENEMY2;
    private final Client client;
    private Boolean sprint;


    public Enemy2(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities,
                  String id, Client client) {
        super(x, y, EntityType.ENEMY2, map, lives, id);
        this.id = id;
        this.client = client;

        this.shootingRange = shootingRange;
        this.entities = entities;
        this.totalHealth = getLives();
        this.sprint = true;
    }

    public boolean isTooClose() {
        return isTooClose;
    }

    public Boolean getSprint() {
        return sprint;
    }

    public void setOldTargets(ArrayList<Entity> oldTargets) {
        this.oldTargets = oldTargets;
    }

    public void setSprint(Boolean sprint) {
        this.sprint = sprint;
    }

    public void setTarget(ArrayList<Entity> target) {
        this.target = target;
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
                if (entity.type == EntityType.PLAYER &&
                        entity.getX() + (entity.getWidth() / 2f) >= getX() - entity.getWidth() &&
                        entity.getX() + (entity.getWidth() / 2f) <= getX() + (entity.getWidth()) &&
                        entity.getY() + (entity.getHeight() / 2f) >= getY() - (entity.getHeight()) &&
                        entity.getY() + (entity.getHeight() / 2f) <= getY() + entity.getHeight()) {
                    time = 0;
                    entity.setLives(entity.getLives() - 10);
                    oldTargets.add(entity);
                    target.clear();
                    LivesLost livesLost = new LivesLost();
                    livesLost.lives = entity.getLives();
                    livesLost.id = entity.getId();
                    client.sendUDP(livesLost);
                    if (oldTargets.size() == 2) {
                        oldTargets.clear();
                    }
                    break;
                }
            }
        }
    }

    public void follow(float deltaTime) {
        for (Entity player : entities) {
            if (player.getType().equals(EntityType.PLAYER) && oldTargets.isEmpty()
                    && target.isEmpty()) {
                followed = player;
                target.add(player);
                oldTargets.add(player);
            } else if (player.getType().equals(EntityType.PLAYER) && !oldTargets.contains(player)
                    && target.isEmpty()) {
                followed = player;
                target.add(player);
                oldTargets.add(player);
            }

        }

        if (followed != null) {
            sprint = followed.getType().equals(EntityType.PLAYER)
                    && followed.getY() >= getY() - 150 && followed.getY() <= getY() + 150
                    && followed.getX() >= getX() - 150 && followed.getX() <= getX() + 150;
            isTooClose =
                    followed.getX() + (followed.getWidth() / 2f) >= getX() - followed.getWidth() &&
                            followed.getX() + (followed.getWidth() / 2f) <= getX() + (followed.getWidth()) &&
                            followed.getY() + (followed.getHeight() / 2f) >= getY() - (followed.getHeight()) &&
                            followed.getY() + (followed.getHeight() / 2f) <= getY() + followed.getHeight();
            if (isTooClose) {
                sprint = false;
            }
            if (sprint) {
                if (followed.getX() + (followed.getWidth() / 2f) > getX() + (getWidth() / 2f)) {
                    setPosX(getX() + 1.5f);
                } else if (followed.getX() + followed.getWidth() / 2f < getX() + (getWidth() / 2f)) {
                    setPosX(getX() - 1.5f);
                }
                if (followed.getY() + followed.getHeight() / 2f > getY() + (getHeight() / 2f)) {
                    setPosY(getY() + 1.5f);
                } else if (followed.getY() + followed.getHeight() / 2f < getY() + (getHeight() / 2f)) {
                    setPosY(getY() - 1.5f);
                }
            } else if (!isTooClose) {
                if (followed.getX() + followed.getWidth() / 2f > getX()) {
                    setPosX(getX() + 0.5f);
                } else if (followed.getX() + followed.getWidth() / 2f < getX()) {
                    setPosX(getX() - 0.5f);
                }
                if (followed.getY() + followed.getHeight() / 2f > getY()) {
                    setPosY(getY() + 0.5f);
                } else if (followed.getY() + followed.getHeight() / 2f < getY()) {
                    setPosY(getY() - 0.5f);
                }
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
        if (sprint = true) {
            follow(deltaTime * 2);
        } else {
            follow(deltaTime);
        }
        movingTime += 1;
        if (movingTime > enemyType.getMovingString().size() - 1) {
            movingTime = 0;
        }
        time += 1;
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
