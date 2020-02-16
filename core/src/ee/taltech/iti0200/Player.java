package ee.taltech.iti0200;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Texture characterImage;
    private Rectangle object;

    public Player(FileHandle characterImage) {
        this.object = new Rectangle();
        this.characterImage = new Texture(characterImage);
    }

    public Rectangle getObject() {
        return object;
    }

    public Texture getCharacterImage() {
        return characterImage;
    }

    public void setCharacterSize() {
        object.width = 20;
        object.height = 20;
    }

    public void setCharacterStartingPoint() {
        object.x = 20;
        object.y = 20;
    }

}
