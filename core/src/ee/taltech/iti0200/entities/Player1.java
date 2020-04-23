package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.server.packets.*;
import ee.taltech.iti0200.world.GameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Player1 extends Entity {

    private static int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private static final double C_DELAY = 0.05;
    private static final double V_DELAY = 0.75;
    private static final double X_DELAY = 1;

    private ArrayList<Entity> entities;

    private Texture gunLeft, gunRight, cSkill1, cSkill2, cSkill3, xSkill50, xSkill45, xSkill40, xSkill35, xSkill30,
            xSkill25, xSkill20, xSkill15, xSkill10, xSkill05;
    private NinePatch health;
    private float totalHealth, shootingRange, lastX, lastXPos, lastC, deltaTime, cSkillX, cSkillY, lastV, lastZ,
            xSkillX, xSkillY, gunX, closestEnemyX, closestEnemyY;
    private boolean isRight, shoot, moving, keyPressed, cSkill, cSkillWasRight, vSkill, vSkillSpeedUp, zSkill, xSkill,
            bombGrounded, explosionTime, cPlanted, cExploding;
    private int shootingTime, movingTime, jumpingPower, cSkillRange;
    private PlayerType playerType;
    private Map<Float, Float> xSkillXY = new HashMap<>();
    private Entity closestEnemy;
    Client client;
    String id, texture, gunfire;

    public Player1(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, PlayerType playerType, Client client, String id) {
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
        closestEnemyX = 100000;
        closestEnemyY = 100000;

        this.cSkill1 = new Texture("PlayerAbilities/Player1/cSkill1.png");
        this.cSkill2 = new Texture("PlayerAbilities/Player1/cSkill2.png");
        this.cSkill3 = new Texture("PlayerAbilities/Player1/cSkill3.png");
        this.xSkill50 = new Texture("PlayerAbilities/Player1/xSkill50.png");
        this.xSkill45 = new Texture("PlayerAbilities/Player1/xSkill45.png");
        this.xSkill40 = new Texture("PlayerAbilities/Player1/xSkill40.png");
        this.xSkill35 = new Texture("PlayerAbilities/Player1/xSkill35.png");
        this.xSkill30 = new Texture("PlayerAbilities/Player1/xSkill30.png");
        this.xSkill25 = new Texture("PlayerAbilities/Player1/xSkill25.png");
        this.xSkill20 = new Texture("PlayerAbilities/Player1/xSkill20.png");
        this.xSkill15 = new Texture("PlayerAbilities/Player1/xSkill15.png");
        this.xSkill10 = new Texture("PlayerAbilities/Player1/xSkill10.png");
        this.xSkill05 = new Texture("PlayerAbilities/Player1/xSkill05.png");
        cSkillRange = cSkill1.getWidth();
    }

    public void livesLostPackage(Entity entity) {
        LivesLost livesLost = new LivesLost();
        livesLost.id = entity.getId();
        livesLost.lives = entity.getLives();
        client.sendTCP(livesLost);
    }

    public void abilityPackage(float x, float y, String texture) {
        Ability ability = new Ability();
        ability.x = x;
        ability.y = y;
        ability.texture = texture;
        ability.id = id;
        client.sendTCP(ability);
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
                    livesLostPackage(entity);
                } else if (!isRight && entity.getX() < pos.x
                        && entity.getX() + entity.getWidth() >= getX() - shootingRange
                        && getY() + 0.5 * getHeight() >= entity.getY()
                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
                        && entity.getLives() > 0) {
                    entity.setLives(entity.getLives() - 1);
                    livesLostPackage(entity);
                }
            }
        }
    }


    public void xSkill() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) && !xSkill) {
            xSkill = true;
            lastX = deltaTime;
        }
        if (xSkill) {
            for (Entity entity : entities) {
                if (entity != this) {
                    for (Map.Entry<Float, Float> skillXY : xSkillXY.entrySet()) {
                        if (entity.getX() + entity.getWidth() >= skillXY.getKey() - 10 &&
                                (entity.getX()) <= skillXY.getKey() + 10 &&
                                entity.getY() <= skillXY.getValue() + 10 &&
                                (entity.getY() + entity.getHeight()) >= skillXY.getValue() - 10) {
                            if (entity.getLives() - 0.1 > 0) {
                                entity.setLives((float) (entity.getLives() - 0.1));
                                livesLostPackage(entity);
                            } else {
                                entity.setLives(0);
                                setLives(Math.min(getLives() + 100, totalHealth));
                                livesLostPackage(entity);
                                livesLostPackage(this);
                            }
                        }
                    }
                }
            }
        }
    }


    public void cSkill() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.C) && !cSkill) {
            cSkill = true;
            lastC = deltaTime;
            closestEnemy = entities.get(entities.size() - 1);
            for (Entity entity : entities) {
                if (entity != this) {
                    if (entity.getX() < closestEnemyX && entity.getY() < closestEnemyY) {
                        closestEnemy = entity;
                        closestEnemyX = entity.getX();
                        closestEnemyY = entity.getY();
                    }
                }
            }
            cPlanted = true;
        }
        if (cExploding) {
            for (Entity entity : entities) {
                if (entity != this) {
                    if (closestEnemy.getX() - 50 <= entity.getX() + entity.getWidth() &&
                            (closestEnemy.getX() + closestEnemy.getWidth() + 50) >= entity.getX() &&
                            closestEnemy.getY() - 50 <= (entity.getY() + entity.getHeight()) && (closestEnemy.getY() + closestEnemy.getHeight() + 50) >= entity.getY()) {
                        if (entity.getLives() - 50 > 0) {
                            entity.setLives(entity.getLives() - 50);
                            livesLostPackage(entity);
                        } else {
                            entity.setLives(0);
                            setLives(Math.min(getLives() + 100, totalHealth));
                            livesLostPackage(entity);
                            livesLostPackage(this);
                        }
                    }
                }
            }
            cExploding = false;
            cPlanted = false;
        }
    }

    public void vSkill() {
        // PASSIVE
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
            if (deltaTime <= lastX + 0.2) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill50, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill50.png");
                }
            } else if (deltaTime > lastX + 0.2 && deltaTime <= lastX + 0.4) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill45, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill45.png");
                }
            } else if (deltaTime > lastX + 0.4 && deltaTime <= lastX + 0.6) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill40, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill40.png");
                }
            } else if (deltaTime > lastX + 0.6 && deltaTime <= lastX + 0.8) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill35, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill35.png");
                }
            } else if (deltaTime > lastX + 0.8 && deltaTime <= lastX + 1) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill30, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill30.png");
                }
            } else if (deltaTime > lastX + 1 && deltaTime <= lastX + 1.2) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill25, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill25.png");
                }
            } else if (deltaTime > lastX + 1.2 && deltaTime <= lastX + 1.4) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill20, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill20.png");
                }
            } else if (deltaTime > lastX + 1.4 && deltaTime <= lastX + 1.6) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill15, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill15.png");
                }
            } else if (deltaTime > lastX + 1.6 && deltaTime <= lastX + 1.8) {
                xSkillXY.put(pos.x, pos.y);
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill10, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill10.png");
                }
            } else if (deltaTime > lastX + 1.8 && deltaTime <= lastX + 4) {
                for (Map.Entry<Float, Float> puddlePos : xSkillXY.entrySet()) {
                    batch.draw(xSkill05, puddlePos.getKey(), puddlePos.getValue());
                    abilityPackage(puddlePos.getKey(), puddlePos.getValue(), "PlayerAbilities/Player1/xSkill05.png");
                }
            }
            if (deltaTime >= lastX + 4) {
                xSkillXY.clear();
                xSkill = false;
            }
        }


        if (cSkill) {
            if (cPlanted) {
                if (deltaTime <= lastC + 1) {
                    batch.draw(cSkill3, closestEnemy.getX() + (closestEnemy.getWidth() / 2), closestEnemy.getY() + (closestEnemy.getHeight() + 15));
                    abilityPackage(closestEnemy.getX() + (closestEnemy.getWidth() / 2), closestEnemy.getY() + (closestEnemy.getHeight() + 15), "PlayerAbilities/Player1/cSkill3.png");
                } else if (deltaTime <= lastC + 2 && deltaTime > lastC + 1) {
                    batch.draw(cSkill2, closestEnemy.getX() + (closestEnemy.getWidth() / 2), closestEnemy.getY() + (closestEnemy.getHeight() + 15));
                    abilityPackage(closestEnemy.getX() + (closestEnemy.getWidth() / 2), closestEnemy.getY() + (closestEnemy.getHeight() + 15), "PlayerAbilities/Player1/cSkill2.png");
                } else if (deltaTime <= lastC + 3 && deltaTime > lastC + 2) {
                    batch.draw(cSkill1, closestEnemy.getX() + (closestEnemy.getWidth() / 2), closestEnemy.getY() + (closestEnemy.getHeight() + 15));
                    abilityPackage(closestEnemy.getX() + (closestEnemy.getWidth() / 2), closestEnemy.getY() + (closestEnemy.getHeight() + 15), "PlayerAbilities/Player1/cSkill1.png");
                } else if (deltaTime >= lastC + 3) {
                    cExploding = true;
                }
            }
            if (deltaTime >= lastC + 8) cSkill = false;
        }
    }
}
