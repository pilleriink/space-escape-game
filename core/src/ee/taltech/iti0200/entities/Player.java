package ee.taltech.iti0200.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.world.GameMap;


public class Player extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;

    Texture image;

    public Player(float x, float y, GameMap map) {
        super(x, y, EntityType.PLAYER, map);
        image = new Texture("player.png");
    }

    @Override
    public void update(float deltaTime, float gravity) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
            this.velocityY += JUMP_VELOCITY * getWeight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0) {
            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
        }
        super.update(deltaTime, gravity); // applies the gravity

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX(-SPEED * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX(SPEED * deltaTime);
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }

}
