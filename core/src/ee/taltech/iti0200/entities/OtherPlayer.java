package ee.taltech.iti0200.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.world.GameMap;

import java.util.ArrayList;

public class OtherPlayer extends Entity {

    float x, y, lives, lastXPos, totalHealth, gunPos;
    String id;
    GameMap map;
    PlayerType playerType;
    String texture, gunfire;
    boolean keyPressed, isRight;

    public OtherPlayer(float x, float y, GameMap map, float lives, String id, PlayerType playerType) {
        super(x, y, EntityType.PLAYER1, map, lives, id);
        this.map = map;
        this.lives = lives;
        this.id = id;
        this.playerType = playerType;
        this.lastXPos = getX();
        this.totalHealth = getLives();
        this.texture = playerType.getId() + "/" + playerType.getId() + "_running_right_0.png";
        this.gunfire = "no_gun.png";
        this.gunPos = getX();
    }

    public void setGunPos(float gunPos) {
        this.gunPos = gunPos;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void setGunfire(String gunfire) {
        this.gunfire = gunfire;
    }

    public String getId() {
        return id;
    }

    @Override
    public void render(SpriteBatch batch) {
        velocityY = 0;
        batch.draw(new Texture(texture), pos.x, pos.y, getWidth(), getHeight());
        batch.draw(new Texture(gunfire), gunPos, pos.y + getHeight() / 4, 5, 5);
        new NinePatch(new Texture("healthbar.png"), 0, 0, 0, 0).draw(batch, pos.x, pos.y + 40, (getLives() / this.totalHealth) * getWidth(), 3);
        gunfire = "no_gun.png";
    }
}
