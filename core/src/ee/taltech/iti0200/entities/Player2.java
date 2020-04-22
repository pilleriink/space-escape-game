package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.server.packets.Death;
import ee.taltech.iti0200.server.packets.Gun;
import ee.taltech.iti0200.server.packets.LivesLost;
import ee.taltech.iti0200.server.packets.Move;
import ee.taltech.iti0200.world.GameMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;


public class Player2 extends Entity {

    private static int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private static final double C_DELAY = 0.05;
    private static final double V_DELAY = 0.75;
    private static final double X_DELAY = 1;

    private ArrayList<Entity> entities;

    private Texture gunLeft, gunRight, cSkill1, cSkill2, cSkill3, xSkill1, xSkill2, droneTexture;
    private NinePatch health;
    private float totalHealth, shootingRange, lastX, lastXPos, lastC, deltaTime, xSkillX, xSkillY, lastV, lastZ,
            droneX, droneY, gunX, closestEnemyX, closestEnemyY;
    private boolean isRight, shoot, moving, keyPressed, cSkill, cSkillWasRight, vSkill, vSkillSpeedUp, zSkill, xSkill,
            bombGrounded, explosionTime, droneCanMove, droneIsComingBack, droneExplosion, enemyFound, xExplosion;
    private int shootingTime, movingTime, jumpingPower, cSkillRange;
    private PlayerType playerType;
    private Entity closestEnemy;
    Client client;
    String id, texture, gunfire;

    public Player2(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, PlayerType playerType, Client client, String id) {
        super(x, y, EntityType.PLAYER, map, lives, id);
        this.client = client;
        this.id = id;
        this.texture = "character0/character0_running_left_0.png";
        this.gunfire = "no_gun.png";
        this.gunX = getX();

        this.entities = entities;
        this.shootingRange = shootingRange;
        this.totalHealth = getLives();
        this.gunLeft = new Texture("gunfireleft.png");
        this.gunRight = new Texture("gunfire.png");
        this.lastXPos = getX();
        this.playerType = playerType;
        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);
        droneX = (int) x - 15;
        droneY = (int) y - 15;
        droneCanMove = true;
        droneIsComingBack = false;
        droneExplosion = false;
        closestEnemyX = 100000;
        closestEnemyY = 100000;

        droneTexture = new Texture("PlayerAbilities/Player2/droneTEST.png");
        cSkill1 = new Texture("PlayerAbilities/Player0/cSkill1.png");
        cSkill2 = new Texture("PlayerAbilities/Player0/cSkill2.png");
        cSkill3 = new Texture("PlayerAbilities/Player0/cSkill3.png");
        xSkill1 = new Texture("PlayerAbilities/Player0/xSkill1.png");
        xSkill2 = new Texture("PlayerAbilities/Player0/xSkill2.png");
        cSkillRange = cSkill1.getWidth();
    }

    public boolean isRight() {
        return isRight;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isShoot() {
        return shoot;
    }

    public String getId() {
        return id;
    }

    public float getLives() {
        return this.lives;
    }

    public float getTotalHealth() {
        return totalHealth;
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
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)
                && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX((float) (-SPEED * 1.5 * deltaTime));
            isRight = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)
                && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveX(-SPEED * deltaTime);
            isRight = false;
        }
    }

    public void moveRight(float deltaTime) {
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)
                && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX((float) (SPEED * 1.5 * deltaTime));
            isRight = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)
                && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moveX(SPEED * deltaTime);
            isRight = true;
        }
    }


    public void shoot() {
        for (Entity entity : entities) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                shoot = true;
                shootingTime = 0;
                if (isRight && entity.getX() > pos.x
                        && entity.getX() <= getX() + getWidth() + shootingRange
                        && getY() + 0.5 * getHeight() >= entity.getY()
                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
                        && entity.getLives() > 0) {
                    entity.setLives(entity.getLives() - 1);
                    LivesLost livesLost = new LivesLost();
                    livesLost.id = entity.getId();
                    livesLost.lives = entity.getLives();
                    client.sendTCP(livesLost);
                } else if (!isRight && entity.getX() < pos.x
                        && entity.getX() + entity.getWidth() >= getX() - shootingRange
                        && getY() + 0.5 * getHeight() >= entity.getY()
                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
                        && entity.getLives() > 0) {
                    entity.setLives(entity.getLives() - 1);
                    LivesLost livesLost = new LivesLost();
                    livesLost.id = entity.getId();
                    livesLost.lives = entity.getLives();
                    client.sendTCP(livesLost);
                }
            }
        }
    }


    public void xSkill() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) && !xSkill) {
            xSkill = true;
            lastX = deltaTime;
            closestEnemy = entities.get(entities.size() - 1);
            xSkillX = droneX;
            xSkillY = droneY;
            for (Entity entity : entities) {
                if (entity != this) {
                    if (entity.getX() < closestEnemyX && entity.getY() < closestEnemyY) {
                        closestEnemy = entity;
                        enemyFound = true;
                        closestEnemyX = entity.getX();
                        closestEnemyY = entity.getY();
                    }
                }
            }
        }
        if (enemyFound) {
            if (closestEnemy.getX() - 30 <= xSkillX && closestEnemy.getX() + closestEnemy.getWidth() + 30 >= xSkillX
                    && closestEnemy.getY() + closestEnemy.getHeight() + 30 >= xSkillY && closestEnemy.getY() - 30 <= xSkillY) {
                xExplosion = true;
                enemyFound = false;
                System.out.println("GNNA DO DMG");
            }
            if (xSkillX < closestEnemy.getX()) {
                if (xSkillX <= closestEnemy.getX() - 5) xSkillX += 5;
            } else {
                if (xSkillX >= closestEnemy.getX() + 5) xSkillX -= 5;
            }
            if (xSkillY < closestEnemy.getY()) {
                if (xSkillY <= closestEnemy.getY() - 5) xSkillY += 5;
            } else {
                if (xSkillY >= closestEnemyY + 5) xSkillY -= 5;
            }
        }
        if (xExplosion) {
            System.out.println("DAMAGING HIM");
            closestEnemy.setLives(Math.max(closestEnemy.getLives() - 200, 0));
            System.out.println(closestEnemy.getLives() + " LIVES");
            xExplosion = false;
            closestEnemyY = 100000;
            closestEnemyX = 100000;
        }
    }


    public void cSkill() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.C) && !cSkill) {
            cSkill = true;
            lastC = deltaTime;
        }
        if (droneExplosion) {
            for (Entity entity : entities) {
                if (entity != this &&
                        entity.getX() >= droneX - 200 && entity.getX() <= droneX + droneTexture.getWidth() + 200
                        && entity.getY() + entity.getHeight() >= droneY - 200
                        && entity.getY() <= droneY + droneTexture.getHeight() + 200 ) {
                    if (entity.getLives() >= 10) {
                        entity.setLives(entity.getLives() - 30);
                    } else {
                        entity.setLives(0);
                    }
                }
            }
            droneIsComingBack = true;
            droneExplosion = false;
        }
    }

    public void vSkill() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.V) && !vSkill) {
            lastV = deltaTime;
            vSkill = true;
            setLives(Math.min(getLives() + 400, totalHealth));
        }
    }

    public void dronePosition() {
        if (droneCanMove) {
            if (isRight) {
                if (droneX > pos.x - 20) {
                    droneX -= 2;
                } else {
                    droneX = pos.x - 20;
                }
            } else {
                if (droneX < pos.x + 20) {
                    droneX += 2;
                } else {
                    droneX = pos.x + 20;
                }
            }
        } else if (droneIsComingBack) {
            if (droneX < pos.x) {
                if (droneX < pos.x - 20) droneX += 5;
            } else {
                if (droneX > pos.x + 20) droneX -= 5;
            }
            if (droneY < pos.y + 20) {
                if (droneY < pos.y + 15) droneY += 5;
            } else {
                if (droneY > pos.y + 25) droneY -= 5;
            }
            if (pos.x - 20 <= droneX && pos.x + 20 >= droneX && pos.y + 15 <= droneY && pos.y + 25 >= droneY) {
                droneCanMove = true;
                droneIsComingBack = false;
            }
        }
    }


    @Override
    public void update(float deltaTime, float gravity) {
        if (lives == 0) {
            Death death = new Death();
            death.id = id;
            client.sendTCP(death);
        }
        shootingTime += 1;
        jump(deltaTime, gravity);
        if (!keyPressed) {
            moveLeft(deltaTime);
            moveRight(deltaTime);
        }
        shoot();
        xSkill();
        cSkill();
        vSkill();
        if (shootingTime > 5) { shoot = false; }
        if (getX() != lastXPos) {
            movingTime += 1;
            if (movingTime > playerType.getRunningRight().size() - 1) {
                movingTime = 0;
            }
            moving = true;
            lastXPos = getX();
        } else {
            moving = false;
            movingTime = 0;
        }
        Move move = new Move();
        move.id = id;
        move.x = getX();
        move.y = getY();
        move.texture = texture;
        client.sendTCP(move);
    }

    @Override
    public void render(SpriteBatch batch) {
        deltaTime += Gdx.graphics.getDeltaTime();
        // draw player
        if (keyPressed) {
            if (isRight) {
                texture = playerType.getId() + "/" + playerType.getId() + "_jumping_up_right.png";
                batch.draw(playerType.getRightJumpingUp(), pos.x, pos.y, getWidth(), getHeight());
            } else {
                texture = playerType.getId() + "/" + playerType.getId() + "_jumping_up_left.png";
                batch.draw(playerType.getLeftJumpingUp(), pos.x, pos.y, getWidth(), getHeight());
            }
        }
        else {
            if (!moving || !grounded) {
                if (isRight) {
                    texture = playerType.getId() + "/" + playerType.getId() + "_running_right_0.png";
                    batch.draw(playerType.getStandingRight(), pos.x, pos.y, getWidth(), getHeight());
                } else {
                    texture = playerType.getId() + "/" + playerType.getId() + "_running_left_0.png";
                    batch.draw(playerType.getStandingLeft(), pos.x, pos.y, getWidth(), getHeight());
                }
            } else {
                if (isRight) {
                    texture = playerType.getRight().get(movingTime);
                    batch.draw(playerType.getRunningRight().get(movingTime), pos.x, pos.y, getWidth(), getHeight());
                } else {
                    texture = playerType.getLeft().get(movingTime);
                    batch.draw(playerType.getRunningLeft().get(movingTime), pos.x, pos.y, getWidth(), getHeight());
                }
            }
        }
        // draw a drone
        if (droneCanMove) {
            droneY = pos.y + 20;
            dronePosition();
        } else if (droneIsComingBack) {
            dronePosition();
        }
        // drawn
        batch.draw(droneTexture, droneX, droneY, getWidth(), getHeight());
        health.draw(batch, pos.x, pos.y + 40, (getLives() / this.totalHealth) * getWidth(), 3);

        if (shoot) {
            if (isRight) {
                gunfire = "gunfire.png";
                gunX = pos.x + getWidth();
                batch.draw(gunRight, pos.x + getWidth(), pos.y + getHeight() / 4, 5, 5);
            } else {
                gunfire = "gunfireleft.png";
                gunX = pos.x - 5;
                batch.draw(gunLeft, pos.x - 5, pos.y + getHeight() / 4, 5, 5);
            }
            Gun gun = new Gun();
            gun.gun = gunfire;
            gun.x = gunX;
            gun.id = id;
            client.sendTCP(gun);
        }

        if (xSkill) {
            if (enemyFound) {
                batch.draw(droneTexture, xSkillX, xSkillY, droneTexture.getWidth(), droneTexture.getHeight());
            }
            if (deltaTime > lastX + 4) xSkill = false;
        }


        if (cSkill) {
        // Drone stops waits for 2 seconds, then goes back to owner
            if (deltaTime <= lastC + 2) droneCanMove = false;
            if (deltaTime > lastC + 2) droneExplosion = true;
            if (deltaTime > lastC + 6) cSkill = false;
        }
        if (vSkill) {
            if (deltaTime >= lastV + 5) vSkill = false;
        }
    }

}
