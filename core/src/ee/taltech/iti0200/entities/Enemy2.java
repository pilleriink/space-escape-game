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

    private static int SPEED = 40;
    private static final int JUMP_VELOCITY = 5;
    private int time, movingTime;
    private float shootingRange, totalHealth;;

    private ArrayList<Entity> entities;
    private boolean isRight, shoot;
    private Entity followed;
    private EnemyType enemyType = EnemyType.ENEMY2;
    final Client client;
    Boolean sprint;


    public Enemy2(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, String id, Client client) {
        super(x, y, EntityType.ENEMY2, map, lives, id);
        this.id = id;
        this.client = client;

        this.shootingRange = shootingRange;
        this.entities = entities;
        this.totalHealth = getLives();
        this.sprint = true;
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
                if (entity.getX() - 2 <= getX() && entity.getX() + 2 >= getX()
                        && getY() <= entity.getY() + 2 && entity.getY() - 2 <= getY()) {
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
                    && player.getX() > getX()  - 100 && player.getX() < getX() + 100) {
                sprint = true;
            }
            else if (player.getType().equals(EntityType.PLAYER)
                    && player.getY() >= getY() && player.getX() > getX() - 500 && player.getX() < getX() + 500) {
                followed = player;
                sprint = false;
            }

        }

        if (followed != null) {
//            if (map.doesRectCollideMap(getX() + 2, getY() + 2, getWidth(), getHeight())) {
//                setPosY(getY() + 1);
//                setPosX(getX() + 1);
//            }
            if (!map.doesRectCollideMap(getX(), getY() + 2, getWidth(), getHeight())) {
                moveY(1);
            }
            if (!map.doesRectCollideMap(getX() + 2, getY(), getWidth(), getHeight())) {
                moveX(1);
            }
            if (!map.doesRectCollideMap(getX() - 2, getY(), getWidth(), getHeight())) {
                moveX(-1);
            }
            if (!map.doesRectCollideMap(getX(), getY() - 2, getWidth(), getHeight())) {
                moveY(-1);
            }
            if (map.doesRectCollideMap(getX(), getY() + 2, getWidth(), getHeight()) && followed.getY() > getY()) {
                if (!map.doesRectCollideMap(getX() + 30, getY() + 2, getWidth(), getHeight()) && followed.getY() > getY()
                && !map.doesRectCollideMap(getX() - 30, getY() + 2, getWidth(), getHeight())) {

                    moveX(1);
                } else if (!map.doesRectCollideMap(getX() - 30, getY() + 2, getWidth(), getHeight()) && followed.getY() > getY()) {
                    moveX(-1);
                } else if (!map.doesRectCollideMap(getX() + 30, getY() + 2, getWidth(), getHeight()) && followed.getY() > getY()) {
                    moveX(1);
                }
            }
            if (map.doesRectCollideMap(getX(), getY() - 2, getWidth(), getHeight()) && followed.getY() < getY()) {
                if (!map.doesRectCollideMap(getX() + 30, getY() - 2, getWidth(), getHeight()) && followed.getY() > getY()) {

                    moveX(1);
                }
                if (!map.doesRectCollideMap(getX() - 30, getY() - 2, getWidth(), getHeight()) && followed.getY() > getY()) {
                    moveX(-1);
                }
            }
//            if (map.doesRectCollideMap(getX() - 5, getY() + 5, getWidth(), getHeight())
//                    || map.doesRectCollideMap(getX() + 5, getY() - 5, getWidth(), getHeight())) {
//                setPosY(getY() - 1);
//            }
//            if (map.doesRectCollideMap(getX(), getY() + 10, getWidth(), getHeight())
//                    || map.doesRectCollideMap(getX(), getY() -10, getWidth(), getHeight())) {
//                setPosY(getY() - 1);
//
//            }

            if (followed.getX() > getX()) {
                moveRight(deltaTime);
            } else if (followed.getX() < getX()) {
                moveLeft(deltaTime);
            }
            if (followed.getY() > getY()) {
                setPosY(getY() + 1);
            } else if (followed.getY() < getY()) {
                setPosY(getY() - 1);
            }



            if (map.doesRectCollideMap(followed.getX(), followed.getY(), followed.getWidth(), followed.getHeight())
                    || followed.getX() < getX()  - 500 || followed.getX() > getX() + 500 || followed.getY() < getY() - 200
            || followed.getY() > getY() + 200) {
                followed = null;
            }
//            if (map.doesRectCollideMap(followed.getX(), followed.getY() - 2, followed.getWidth(), followed.getHeight())
//                    || followed.getY() < getY() - getHeight() * 2
//                    || followed.getX() < getX()  - 150 || followed.getX() > getX() + 150) {
//                sprint = false;
//            }

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
        if (sprint = true) {
//            gravity = 0;
            follow(deltaTime * 2);
            movingTime += 1;
            if (movingTime > enemyType.getMovingString().size() - 1) { movingTime = 0; }
            time += 1;
//            super.update(deltaTime * 2, gravity); // applies the gravity
            //move(deltaTime);
            shoot();
            if (time > 2) { shoot = false; }
        }
//        gravity = 0;
        follow(deltaTime);
        movingTime += 1;
        if (movingTime > enemyType.getMovingString().size() - 1) { movingTime = 0; }
        time += 1;
//        super.update(deltaTime, gravity); // applies the gravity
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
