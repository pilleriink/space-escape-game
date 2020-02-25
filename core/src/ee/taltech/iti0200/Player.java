package ee.taltech.iti0200;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    private Texture characterImage;
    private Actor object;

    public Player(FileHandle characterImage, long height, long width) {
        this.object = new Actor();
        this.characterImage = new Texture(characterImage);
        this.object.setHeight(height);
        this.object.setWidth(width);
        this.object.setX(0);
        this.object.setY(0);
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

}
