package ee.taltech.iti0200.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ee.taltech.iti0200.world.GameMap;

public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;
    protected float velocityY = 0;
    protected GameMap map;
    protected boolean grounded = false;
    protected float lives, totalHealth;
    String id;
    boolean isRight, moving, shoot, isSlowed;

    public Entity(float x, float y, EntityType type, GameMap map, float lives, String id) {
        this.pos = new Vector2(x, y);
        this.type = type;
        this.map = map;
        this.lives = lives;
        totalHealth = lives;
        this.id = id;
        this.isRight = false;
        this.moving = false;
        this.shoot = false;
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

    public float getTotalHealth() {
        return totalHealth;
    }

    public String getId() {
        return id;
    }

    public void update(float deltaTime, float gravity) {
        float newY = pos.y;

        this.velocityY += gravity * deltaTime * getWeight();
        newY += this.velocityY * deltaTime;

        if (map.doesRectCollideMap(pos.x, newY, getWidth(), getHeight())) {
            if (velocityY < 0) {
                this.pos.y = (float) Math.floor(pos.y);
                grounded = true;
            }
            this.velocityY = 0;
        } else {
            this.pos.y = newY;
            grounded = false;
        }

    }

    public abstract void render(SpriteBatch batch);

    protected void moveX(float amount) {
        float newX = this.pos.x + amount;
        if (!map.doesRectCollideMap(newX, pos.y, getWidth(), getHeight())) {
            this.pos.x = newX;
        }
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPosX(float posX) {
        this.pos.x = posX;
    }

    public void setPosY(float posY) {
        this.pos.y = posY;
    }

    public float getLives() {return this.lives;}

    public void setLives(float lives) {
        this.lives = lives;
    }


    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public EntityType getType() {
        return type;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public int getWidth() {
        return type.getWidth();
    }
    public int getHeight() {
        return type.getHeight();
    }
    public float getWeight() {
        return type.getWeight();
    }
}
