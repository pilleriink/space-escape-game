package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.server.packets.*;
import ee.taltech.iti0200.world.GameMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Player3 extends Entity {

    private static int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;
    private static final double C_TICK = 0.2;
    private static final double V_DELAY = 1;
    private static final double X_DELAY = 1;
    private static final double X_SKILL_HEIGHT_LIMIT = 40.0;

    private ArrayList<Entity> entities, cSkillToHeal;

    private Texture gunLeft, gunRight, cSkill1, cSkillField, cSkillField0, cSkillField1, cSkillField2,
            cSkillReady, cSkill2, xSkillTexture, vSkillTexture;
    private NinePatch health;
    public float totalHealth, shootingRange, lastX, lastXPos, lastC, deltaTime, cSkillX, cSkillY, lastV, lastZ, xSkillX, xSkillY, gunX, lastCTick;
    public boolean isRight, shoot, moving, keyPressed, cSkill, cSkillWasRight, vSkill, vSkillSpeedUp, zSkill, xSkill,
            bombGrounded, explosionTime, reachedLimit, xExplosion, xStuck, xRight, cSkillIsReady, cSkillIsDown;
    private int shootingTime, movingTime, jumpingPower, cSkillRange, fieldIndex;
    private PlayerType playerType;
    private Map<Float, Float> xSkillCurve;
    float xSkillCurveIndex;
    private Random random;
    private List<Texture> cSkillFieldList;
    final Client client;
    String id, texture, gunfire;

    public Player3(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, PlayerType playerType, Client client, String id) {
        super(x, y, EntityType.PLAYER, map, lives, id);
        this.client = client;
        this.id = id;
        this.texture = "character0/character0_running_left_0.png";
        this.gunfire = "no_gun.png";
        this.gunX = getX();
        this.lives = lives;

        this.entities = entities;
        this.shootingRange = shootingRange;
        this.totalHealth = getLives();
        this.gunLeft = new Texture("gunfireleft.png");
        this.gunRight = new Texture("gunfire.png");
        this.lastXPos = getX();
        this.playerType = playerType;
        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);

        this.cSkill1 = new Texture("PlayerAbilities/Player3/cSkill1.png");
        this.cSkill2 = new Texture("PlayerAbilities/Player3/cSkill2.png");
        this.cSkillField0 = new Texture("PlayerAbilities/Player3/cSkillField0.png");
        this.cSkillField1 = new Texture("PlayerAbilities/Player3/cSkillField1.png");
        this.cSkillField2 = new Texture("PlayerAbilities/Player3/cSkillField2.png");
        this.cSkillReady = new Texture("PlayerAbilities/Player3/cSkillReady.png");
        this.xSkillTexture = new Texture("PlayerAbilities/Player3/xSkillTexture.png");
        this.vSkillTexture = new Texture("PlayerAbilities/Player3/vSkillTexture.png");

        cSkillField = cSkillField0;
        fieldIndex = 0;


        cSkillToHeal = new ArrayList<>();
        xSkillCurve = new LinkedHashMap<>();
        xSkillCurve.put((float) 0.0,(float) 0.0);
        float indexX = (float) 0.0;
        float indexY = (float) 0.0;
        for (int i = 1; i <= 200; i++) {
            if (reachedLimit) {
                indexX += 1;
                indexY -= 2;
                xSkillCurve.put(indexX, indexY);
            } else if (xSkillCurve.get(indexX) < X_SKILL_HEIGHT_LIMIT) {
                indexX += 1;
                indexY += 2;
                xSkillCurve.put(indexX,indexY);
            } else {
                reachedLimit = true;
                xSkillCurve.put(indexX + 1, (float) (indexY + 0.5));
                xSkillCurve.put(indexX + 2, indexY);
                xSkillCurve.put(indexX + 3, indexY);
                xSkillCurve.put(indexX + 4, (float) (indexY - 0.5));
                indexX += 4;
            }
        }
        random = new Random();
    }

    public void clientWait() {
        synchronized (client) {
            try {
                client.wait(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void abilityPackage(float x, float y, String texture) {
        Ability ability = new Ability();
        ability.x = x;
        ability.y = y;
        ability.texture = texture;
        ability.id = id;
        client.sendTCP(ability);
        clientWait();
    }

    public void livesLostPackage(Entity entity) {
        LivesLost livesLost = new LivesLost();
        livesLost.id = entity.getId();
        livesLost.lives = entity.getLives();
        client.sendTCP(livesLost);
        clientWait();
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


    public void xSkill() {
        if (!xSkill) {
            xSkill = true;
            lastX = deltaTime;
            if (isRight)  {
                xRight = true;
                xSkillX = pos.x + getWidth();
            }
            else xSkillX = pos.x;
            xSkillY = pos.y + (getHeight() / 2);
            xSkillCurveIndex = 0;
        }
        if (xExplosion) {
            for (Entity entity : entities) {
                if (entity.type != EntityType.PLAYER && entity.getX() + entity.getWidth() >= xSkillX - 100 &&
                        entity.getX() <= xSkillX + xSkillTexture.getWidth() + 100 &&
                        entity.getY() + entity.getHeight() >= xSkillY - 100 &&
                        entity.getY() <= xSkillY + xSkillTexture.getHeight() + 100 ) {
                    if (entity.getLives() >= 15) {
                        entity.setLives(entity.getLives() - 15);
                        livesLostPackage(entity);
                    } else {
                        entity.setLives(0);
                        livesLostPackage(entity);
                    }
                } else if (entity.type == EntityType.PLAYER &&
                        entity.getX() + entity.getWidth() >= xSkillX - 100 &&
                        entity.getX() <= xSkillX + xSkillTexture.getWidth() + 100 &&
                        entity.getY() + entity.getHeight() >= xSkillY - 100 &&
                        entity.getY() <= xSkillY + xSkillTexture.getHeight() + 100 ) {
                    entity.setLives(Math.min(entity.getLives() + 300, entity.getTotalHealth()));
                    livesLostPackage(entity);
                }
            }
            xExplosion = false;
        }
    }


    public void cSkill() {
        if (!cSkill && grounded) {
            cSkill = true;
            lastC = deltaTime;
            cSkillX = pos.x - (cSkillField1.getWidth() / 2);
            cSkillY = pos.y;
        }
        if (cSkillIsReady) {
            for (Entity entity : entities) {
                if (entity.getX() + (entity.getWidth() / 2) >= cSkillX &&
                        entity.getX() <= cSkillX + cSkillField1.getWidth() - (entity.getWidth() / 2) &&
                        entity.getY() >= cSkillY && (entity.getY() + entity.getHeight()) <= cSkillY + 30) {
                    cSkillToHeal.add(entity);
                }
            }
            for (Entity entity : cSkillToHeal) {
                cSkillIsDown = false;
                cSkillIsReady = false;
                entity.setLives(Math.min(entity.getLives() + 200, entity.getTotalHealth()));
                livesLostPackage(entity);
            }
            cSkillToHeal.clear();
        }
    }

    public void vSkill() {
        if (!vSkill) {
            vSkill = true;
            lastV = deltaTime;
            SPEED += 100;
            vSkillSpeedUp = true;
            setLives(Math.min(getLives() + 300, totalHealth));
            livesLostPackage(this);
        }
    }


    @Override
    public void update(float deltaTime, float gravity) {
        if (lives < 1) {
            Death death = new Death();
            death.id = id;
            client.sendTCP(death);
            clientWait();
        }
        shootingTime += 1;
        jump(deltaTime, gravity);
        if (!keyPressed) {
            moveLeft(deltaTime);
            moveRight(deltaTime);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) shoot();
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) || xSkill) xSkill();
        if (Gdx.input.isKeyJustPressed(Input.Keys.C) || cSkill) cSkill();
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) vSkill();
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
        if (!grounded || moving) {
            Move move = new Move();
            move.id = id;
            move.x = getX();
            move.y = getY();
            move.texture = texture;
            client.sendTCP(move);
            clientWait();
        }
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
            clientWait();
        }

        if (xSkill) {
            if (xRight) {
                if (!map.doesRectCollideMap(xSkillX + xSkillCurveIndex, xSkillY + xSkillCurve.get(xSkillCurveIndex), xSkillTexture.getWidth(), xSkillTexture.getHeight())) {
                    if (xSkillCurveIndex < 204 && deltaTime <= lastX + 2) {
                        batch.draw(xSkillTexture, xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY);
                        abilityPackage(xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY, "PlayerAbilities/Player3/xSkillTexture.png");
                        xSkillCurveIndex += 1;
                    }
                } else if (deltaTime <= lastX + 2) {
                    batch.draw(xSkillTexture, xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY);
                    abilityPackage(xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY, "PlayerAbilities/Player3/xSkillTexture.png");
                }
            } else {
                if (!map.doesRectCollideMap(xSkillX - xSkillCurveIndex, xSkillY + xSkillCurve.get(xSkillCurveIndex), xSkillTexture.getWidth(), xSkillTexture.getHeight())) {
                    if (xSkillCurveIndex < 204 && deltaTime <= lastX + 2) {
                        batch.draw(xSkillTexture, -xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY);
                        abilityPackage(-xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY, "PlayerAbilities/Player3/xSkillTexture.png");
                        xSkillCurveIndex += 1;
                    }
                } else if (deltaTime <= lastX + 2) {
                    batch.draw(xSkillTexture, -xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY);
                    abilityPackage(-xSkillCurveIndex + xSkillX, xSkillCurve.get(xSkillCurveIndex) + xSkillY, "PlayerAbilities/Player3/xSkillTexture.png");
                }
            }
            if (deltaTime >= lastX + 2 && deltaTime < lastX + 4) {
                xExplosion = true;
            }
            if (deltaTime >= lastX + 4) {
                xSkill = false;
                xSkillCurveIndex = 0;
                xRight = false;
            }
        }


        if (cSkill) {
            if (deltaTime <= lastC + 0.5) cSkillIsDown = true;
            else if (deltaTime >= lastC + 4) cSkill = false;
        }
        if (cSkillIsDown) {
            if (deltaTime >= lastCTick + C_TICK) {
                lastCTick = deltaTime;
                if (cSkillField == cSkillField0) {
                cSkillField = cSkillField1;
                fieldIndex = 1;
                } else if (cSkillField == cSkillField1) {
                cSkillField = cSkillField2;
                fieldIndex = 2;
                } else {
                    cSkillField = cSkillField0;
                    fieldIndex = 0;
                }
            }
            batch.draw(cSkillField, cSkillX, cSkillY);
            abilityPackage(cSkillX, cSkillY, "PlayerAbilities/Player3/cSkillField" + fieldIndex + ".png");
            if (deltaTime <= lastC + 1) {
                batch.draw(cSkill2, cSkillX + 50, cSkillY + 40);
                abilityPackage(cSkillX + 50, cSkillY + 40, "PlayerAbilities/Player3/cSkill2.png");
            }
            else if (deltaTime > lastC + 1 && deltaTime <= lastC + 2){
                batch.draw(cSkill1, cSkillX + 50, cSkillY + 40);
                abilityPackage(cSkillX + 50, cSkillY + 40, "PlayerAbilities/Player3/cSkill1.png");
            }
            else if (deltaTime > lastC + 2) {
                cSkillIsReady = true;
                batch.draw(cSkillReady, cSkillX, cSkillY + 40);
                abilityPackage(cSkillX, cSkillY + 40, "PlayerAbilities/Player3/cSkillReady.png");
            }
        }
        if (vSkill) {
            if (vSkillSpeedUp && deltaTime < lastV + 0.3) {
                batch.draw(vSkillTexture, pos.x, pos.y);
            }
            if (vSkillSpeedUp && deltaTime >= lastV + V_DELAY) {
                SPEED -= 100;
                vSkillSpeedUp = false;
            }
            if (deltaTime >= lastV + 5) {
                vSkill = false;
            }
        }
    }

}
