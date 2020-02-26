package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    private Texture characterImage;
    private Actor object;
    private Boolean isJumping;
    private float time;

    final static int OBJECT_SPEED = 200;
    final static int GRAVITY_SPEED = 300;

    public Player(FileHandle characterImage, long height, long width) {
        this.object = new Actor();
        this.characterImage = new Texture(characterImage);
        this.object.setHeight(height);
        this.object.setWidth(width);
        this.object.setX(0);
        this.object.setY(0);
        time = 1;
        isJumping = false;
    }

    public Actor getObject() {
        return object;
    }

    public Texture getCharacterImage() {
        return characterImage;
    }

    public void boundsLeftAndRight(OrthographicCamera camera) {
        if (getObject().getX() < 0) getObject().setX(0);
        if (getObject().getX() >= (int) camera.viewportWidth - (int) getObject().getWidth()) getObject().setX((int)
                camera.viewportWidth - (int) getObject().getWidth());
    }

    public void boundsUpAndDown(OrthographicCamera camera) {
        if(getObject().getY() < 0) getObject().setY(0);
        if(getObject().getY() >= (int) camera.viewportHeight - (int) getObject().getHeight()) getObject().setY((int)
                camera.viewportHeight - (int) getObject().getHeight());
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public void move() {
        setTime(getTime() + Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            getObject().setX(getObject().getX() - OBJECT_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            getObject().setX(getObject().getX() + OBJECT_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && getTime() > 1) {
            setTime(0);
            isJumping = true;
        }
        if (!isJumping) {
            getObject().setY(getObject().getY() - GRAVITY_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (getTime() <= 0.5 && isJumping) {
            getObject().setY(getObject().getY() + GRAVITY_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (getTime() > 0.5) {
            isJumping = false;
        }
    }

}
