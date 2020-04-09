//package ee.taltech.iti0200.entities;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.NinePatch;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import ee.taltech.iti0200.world.GameMap;
//
//import java.util.ArrayList;
//
//
//public class Player extends Entity {
//
//    private static int SPEED = 80;
//    private static final int JUMP_VELOCITY = 5;
//    private static final double C_DELAY = 0.05;
//    private static final double V_DELAY = 0.75;
//    private static final double Z_DELAY = 1;
//
//    private ArrayList<Entity> entities;
//
//    private Texture gunLeft, gunRight, cSkill1, cSkill2, cSkill3, zSkill1, zSkill2;
//    private NinePatch health;
//    private float totalHealth, shootingRange, lastX, lastC, deltaTime, cSkillX, cSkillY, lastV, lastZ, zSkillX, zSkillY;
//    private boolean isRight, shoot, moving, keyPressed, cSkill, cSkillWasRight, vSkill, vSkillSpeedUp, zSkill,
//            bombGrounded, explosionTime;
//    private int shootingTime, movingTime, jumpingPower, cSkillRange;
//    private PlayerType playerType;
//
//    public Player(float x, float y, GameMap map, float lives, float shootingRange, ArrayList<Entity> entities, PlayerType playerType) {
//        super(x, y, EntityType.PLAYER, map, lives);
//        this.entities = entities;
//        this.shootingRange = shootingRange;
//        this.totalHealth = getLives();
//        this.gunLeft = new Texture("gunfireleft.png");
//        this.gunRight = new Texture("gunfire.png");
//        this.lastX = getX();
//        this.playerType = playerType;
//        health = new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0);
//
//        this.cSkill1 = new Texture("PlayerAbilities/cSkill1.png");
//        this.cSkill2 = new Texture("PlayerAbilities/cSkill2.png");
//        this.cSkill3 = new Texture("PlayerAbilities/cSkill3.png");
//        this.zSkill1 = new Texture("PlayerAbilities/zSkill1.png");
//        this.zSkill2 = new Texture("PlayerAbilities/zSkill2.png");
//        cSkillRange = cSkill1.getWidth();
//    }
//
//    public float getLives() {
//        return this.lives;
//    }
//
//    public void jump(float deltaTime, float gravity) {
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
//            keyPressed = true;
//            jumpingPower += 1;
//        }
//        if (jumpingPower > 10 || !Gdx.input.isKeyPressed(Input.Keys.SPACE) && keyPressed && grounded) {
//            keyPressed = false;
//            this.velocityY += JUMP_VELOCITY * getWeight() * jumpingPower / 10;
//            jumpingPower = 0;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0) {
//            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
//        }
//        super.update(deltaTime, gravity); // applies the gravity
//    }
//
//    public void moveLeft(float deltaTime) {
//        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)
//                && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            moveX((float) (-SPEED * 1.5 * deltaTime));
//            isRight = false;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)
//                && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//            moveX(-SPEED * deltaTime);
//            isRight = false;
//        }
//    }
//
//    public void moveRight(float deltaTime) {
//        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)
//                && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            moveX((float) (SPEED * 1.5 * deltaTime));
//            isRight = true;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)
//                && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//            moveX(SPEED * deltaTime);
//            isRight = true;
//        }
//    }
//
//    public void zSkill() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && !zSkill) {
//            zSkill = true;
//            lastZ = deltaTime;
//            zSkillX = pos.x;
//            zSkillY = pos.y;
//        }
//        if (explosionTime) {
//            for (Entity entity : entities) {
//                if (entity.getType() == EntityType.ENEMY0 && entity.getX() >= zSkillX - 250
//                        && entity.getX() <= zSkillX + 250 && entity.getY() >= zSkillY - 250
//                        && entity.getY() <= zSkillY + 250 || entity.getType() == EntityType.ENEMY1
//                        && entity.getX() >= zSkillX - 250 && entity.getX() <= zSkillX + 250
//                        && entity.getY() >= zSkillY - 250 && entity.getY() <= zSkillY + 250 ) {
//                    if (entity.getLives() >= 10) {
//                        entity.setLives(entity.getLives() - 15);
//                    } else {
//                        entity.setLives(0);
//                    }
//                }
//            }
//            explosionTime = false;
//        }
//    }
//
//    public void shoot() {
//        for (Entity entity : entities) {
//            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
//                shoot = true;
//                shootingTime = 0;
//                if (isRight && entity.getX() > pos.x
//                        && entity.getX() <= getX() + getWidth() + shootingRange
//                        && getY() + 0.5 * getHeight() >= entity.getY()
//                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
//                        && entity.getLives() > 0) {
//                    entity.setLives(entity.getLives() - 1);
//                } else if (!isRight && entity.getX() < pos.x
//                        && entity.getX() + entity.getWidth() >= getX() - shootingRange
//                        && getY() + 0.5 * getHeight() >= entity.getY()
//                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
//                        && entity.getLives() > 0) {
//                    entity.setLives(entity.getLives() - 1);
//                }
//            }
//        }
//    }
//
//    public void cSkill() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.C) && !cSkill) {
//            cSkill = true;
//            lastC = deltaTime;
//            cSkillX = pos.x;
//            cSkillY = pos.y;
//            cSkillWasRight = isRight;
//            for (Entity entity : entities) {
//                if (isRight && entity.getX() > pos.x
//                        && entity.getX() <= getX() + getWidth() + cSkillRange + 20
//                        && getY() + 0.5 * getHeight() >= entity.getY()
//                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
//                        && entity.getLives() > 0) {
//                    if (entity.getLives() >= 10) {
//                    entity.setLives(entity.getLives() - 10);
//                    } else {
//                    entity.setLives(0);
//                    }
//                } else if (!isRight && entity.getX() < pos.x
//                        && entity.getX() + entity.getWidth() >= getX() - cSkillRange + 20
//                        && getY() + 0.5 * getHeight() >= entity.getY()
//                        && getY() + 0.5 * getHeight() <= entity.getY() + entity.getHeight()
//                        && entity.getLives() > 0) {
//                    if (entity.getLives() >= 10) {
//                        entity.setLives(entity.getLives() - 10);
//                    } else {
//                        entity.setLives(0);
//                    }
//                }
//            }
//        }
//    }
//
//    public void vSkill() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.V) && !vSkill) {
//            vSkill = true;
//            lastV = deltaTime;
//            SPEED += 150;
//            vSkillSpeedUp = true;
//        }
//    }
//
//
//    @Override
//    public void update(float deltaTime, float gravity) {
//        shootingTime += 1;
//        jump(deltaTime, gravity);
//        if (!keyPressed) {
//            moveLeft(deltaTime);
//            moveRight(deltaTime);
//        }
//        zSkill();
//        shoot();
//        cSkill();
//        vSkill();
//        if (shootingTime > 5) { shoot = false; }
//        if (getX() != lastX) {
//            movingTime += 1;
//            if (movingTime > playerType.getRunningRight().size() - 1) {
//                movingTime = 0;
//            }
//            moving = true;
//            lastX = getX();
//        } else {
//            moving = false;
//            movingTime = 0;
//        }
//    }
//
//    @Override
//    public void render(SpriteBatch batch) {
//        deltaTime += Gdx.graphics.getDeltaTime();
//        if (keyPressed) {
//            if (isRight) {
//                batch.draw(playerType.getRightJumpingUp(), pos.x, pos.y, getWidth(), getHeight());
//            } else batch.draw(playerType.getLeftJumpingUp(), pos.x, pos.y, getWidth(), getHeight());
//        }
//        else {
//            if (!moving || !grounded) {
//                if (isRight) {
//                    batch.draw(playerType.getStandingRight(), pos.x, pos.y, getWidth(), getHeight());
//                } else batch.draw(playerType.getStandingLeft(), pos.x, pos.y, getWidth(), getHeight());
//            } else {
//                if (isRight) {
//                    batch.draw(playerType.getRunningRight().get(movingTime), pos.x, pos.y, getWidth(), getHeight());
//                } else batch.draw(playerType.getRunningLeft().get(movingTime), pos.x, pos.y, getWidth(), getHeight());
//            }
//        }
//        health.draw(batch, pos.x, pos.y + 40, (getLives() / this.totalHealth) * getWidth(), 3);
//
//        if (zSkill) {
//            if (deltaTime <= lastZ + Z_DELAY){
//                if (!map.doesRectCollideMap(zSkillX, zSkillY - 2, zSkill1.getWidth(), zSkill1.getHeight())) {
//                    batch.draw(zSkill1, zSkillX, zSkillY -= 2);
//                } else {
//                    batch.draw(zSkill1, zSkillX, zSkillY);
//                }
//            } else if (deltaTime > lastZ + Z_DELAY && deltaTime <= lastZ + Z_DELAY * 2) {
//                if (!map.doesRectCollideMap(zSkillX, zSkillY - 2, zSkill2.getWidth(), zSkill2.getHeight())) {
//                    batch.draw(zSkill2, zSkillX, zSkillY -= 2);
//                } else {
//                    batch.draw(zSkill2, zSkillX, zSkillY);
//                }
//            } else if (deltaTime > lastZ + 4) {
//                explosionTime = true;
//                zSkill = false;
//            }
//        }
//
//        if (shoot) {
//            if (isRight) {
//                batch.draw(gunRight, pos.x + getWidth(), pos.y + getHeight() / 4, 5, 5);
//            } else {
//                batch.draw(gunLeft, pos.x - 5, pos.y + getHeight() / 4, 5, 5);
//            }
//        }
//        if (cSkill) {
//            if (cSkillWasRight) {
//                if (deltaTime <= lastC + C_DELAY) {
//                    batch.draw(cSkill1, pos.x + 20, pos.y, 200, 24);
//                } else if (deltaTime > lastC + C_DELAY && deltaTime <= lastC + C_DELAY * 2) {
//                    batch.draw(cSkill2, pos.x + 20, pos.y, 200, 24);
//                } else if (deltaTime > lastC + C_DELAY * 2 && deltaTime <= lastC + C_DELAY * 3) {
//                    batch.draw(cSkill3, pos.x + 20, pos.y, 200, 24);
//                } else if (deltaTime >= lastC + 3) {
//                    cSkill = false;
//                }
//            } else {
//                if (deltaTime <= lastC + C_DELAY) {
//                    batch.draw(cSkill1, pos.x - 210, pos.y, 200, 24);
//                } else if (deltaTime > lastC + C_DELAY && deltaTime <= lastC + C_DELAY * 2) {
//                    batch.draw(cSkill2, pos.x - 210, pos.y, 200, 24);
//                } else if (deltaTime > lastC + C_DELAY * 2 && deltaTime <= lastC + C_DELAY * 3) {
//                    batch.draw(cSkill3, pos.x - 210, pos.y, 200, 24);
//                } else if (deltaTime >= lastC + 3) {
//                    cSkill = false;
//                }
//            }
//        }
//        if (vSkill) {
//            if (vSkillSpeedUp && deltaTime >= lastV + V_DELAY) {
//                SPEED -= 150;
//                vSkillSpeedUp = false;
//            }
//            if (deltaTime >= lastV + 5) vSkill = false;
//        }
//    }
//
//}
