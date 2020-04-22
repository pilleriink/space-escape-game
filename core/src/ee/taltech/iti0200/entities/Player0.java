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


public class Player0 extends Entity {

    private static int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private static final double C_DELAY = 0.02;
    private static final double V_DELAY = 0.75;
    private static final double X_DELAY = 1;

    private ArrayList<Entity> entities;

    private Texture gunLeft, gunRight, cSkill1, cSkill2, cSkill3, xSkill1, xSkill2;
    private NinePatch health;
    private float totalHealth, shootingRange, lastX, lastXPos, lastC, deltaTime, cSkillX, cSkillY, lastV, lastZ, xSkillX, xSkillY, gunX;
    private boolean isRight, shoot, moving, keyPressed, cSkill, cSkillWasRight, vSkill, vSkillSpeedUp, zSkill, xSkill,
            bombGrounded, explosionTime, cDoesDmg, cDidDmg;
    private int shootingTime, movingTime, jumpingPower, cSkillRange;
    private PlayerType playerType;
    Client client;
    String id, texture, gunfire;

    public Player0(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, PlayerType playerType, Client client, String id) {
        super(x, y, EntityType.PLAYER, map, lives, id);
        this.client = client;
        this.id = id;
        this.texture = "character0/character0_running_left_0.png";
        this.gunfire = "no_gun.png";

        this.entities = entities;
        this.shootingRange = shootingRange;
        this.totalHealth = getLives();
        this.gunLeft = new Texture("gunfireleft.png");
        this.gunRight = new Texture("gunfire.png");
        this.lastXPos = getX();
        this.playerType = playerType;
        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);

        this.cSkill1 = new Texture("PlayerAbilities/Player0/cSkill1.png");
        this.cSkill2 = new Texture("PlayerAbilities/Player0/cSkill2.png");
        this.cSkill3 = new Texture("PlayerAbilities/Player0/cSkill3.png");
        this.xSkill1 = new Texture("PlayerAbilities/Player0/xSkill1.png");
        this.xSkill2 = new Texture("PlayerAbilities/Player0/xSkill2.png");
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
            xSkillX = pos.x;
            xSkillY = pos.y;
        }
        if (explosionTime) {
            for (Entity entity : entities) {
                if (entity.type != EntityType.PLAYER &&
                        entity.getX() + entity.getWidth() >= xSkillX - 200 && entity.getX() <= xSkillX
                        + xSkill2.getWidth() + 200 && entity.getY() + entity.getHeight() >= xSkillY - 200
                        && entity.getY() <= xSkillY + xSkill2.getHeight() + 200 ) {
                    if (entity.getLives() >= 10) {
                        entity.setLives(entity.getLives() - 15);
                    } else {
                        entity.setLives(0);
                    }
                }
            }
            explosionTime = false;
        }
    }


    public void cSkill() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.C) && !cSkill) {
            cSkill = true;
            lastC = deltaTime;
            cSkillX = pos.x;
            cSkillY = pos.y;
            cSkillWasRight = isRight;
        }
        if (cDoesDmg && !cDidDmg) {
            cSkillX = pos.x;
            cSkillY = pos.y;
            for (Entity entity : entities) {
                if (isRight && entity.getX() > pos.x
                        && entity.getX() <= getX() + getWidth() + cSkillRange
                        && getY() >= entity.getY()
                        && getY() + getHeight() <= entity.getY() + entity.getHeight()) {
                    if (entity.getLives() >= 10) {
                        entity.setLives(entity.getLives() - 10);
                    } else {
                        entity.setLives(0);
                    }
                } else if (!isRight && entity.getX() < pos.x
                        && entity.getX() + entity.getWidth() >= getX() - cSkillRange
                        && getY() >= entity.getY()
                        && getY() + getHeight() <= entity.getY() + entity.getHeight()) {
                    if (entity.getLives() >= 10) {
                        entity.setLives(entity.getLives() - 10);
                    } else {
                        entity.setLives(0);
                    }
                }
            }
            cDidDmg = true;
        }
    }

    public void vSkill() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.V) && !vSkill) {
            vSkill = true;
            lastV = deltaTime;
            setLives(Math.min(getLives() + 400, totalHealth));
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
            if (deltaTime <= lastX + X_DELAY){
                if (!map.doesRectCollideMap(xSkillX, xSkillY - 2, xSkill1.getWidth(), xSkill1.getHeight())) {
                    batch.draw(xSkill1, xSkillX, xSkillY -= 2);
                } else {
                    batch.draw(xSkill1, xSkillX, xSkillY);
                }
            } else if (deltaTime > lastX + X_DELAY && deltaTime <= lastX + X_DELAY * 2) {
                if (!map.doesRectCollideMap(xSkillX, xSkillY - 2, xSkill2.getWidth(), xSkill2.getHeight())) {
                    batch.draw(xSkill2, xSkillX, xSkillY -= 2);
                } else {
                    batch.draw(xSkill2, xSkillX, xSkillY);
                }
            } else if (deltaTime > lastX + X_DELAY * 2 && deltaTime <= lastX + 4 ) {
                explosionTime = true;
            }
            else if (deltaTime > lastX + 4) {
                xSkill = false;
            }
        }


        if (cSkill) {
            if (cSkillWasRight) {
                if (deltaTime <= lastC + C_DELAY) {
                    batch.draw(cSkill1, pos.x + 20, pos.y, 200, 24);
                } else if (deltaTime > lastC + C_DELAY && deltaTime <= lastC + C_DELAY * 2) {
                    batch.draw(cSkill2, pos.x + 20, pos.y, 200, 24);
                    cDoesDmg = true;
                } else if (deltaTime > lastC + C_DELAY * 2 && deltaTime <= lastC + C_DELAY * 3) {
                    batch.draw(cSkill3, pos.x + 20, pos.y, 200, 24);
                } else if (deltaTime >= lastC + 3) {
                    cSkill = false;
                    cDoesDmg = false;
                    cDidDmg = false;
                }
            } else {
                if (deltaTime <= lastC + C_DELAY) {
                    batch.draw(cSkill1, pos.x - 210, pos.y, 200, 24);
                } else if (deltaTime > lastC + C_DELAY && deltaTime <= lastC + C_DELAY * 2) {
                    batch.draw(cSkill2, pos.x - 210, pos.y, 200, 24);
                    cDoesDmg = true;
                } else if (deltaTime > lastC + C_DELAY * 2 && deltaTime <= lastC + C_DELAY * 3) {
                    batch.draw(cSkill3, pos.x - 210, pos.y, 200, 24);
                } else if (deltaTime >= lastC + 3) {
                    cSkill = false;
                    cDoesDmg = false;
                    cDidDmg = false;
                }
            }
        }
        if (vSkill) {
            if (deltaTime >= lastV + 5) vSkill = false;
        }
    }

}
