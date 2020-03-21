package ee.taltech.iti0200.entities;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public enum EnemyType {

    ENEMY1("enemy");

    private String id;
    private ArrayList<Texture> moving;

    EnemyType(String id) {
        this.id = id;
        this.moving = (ArrayList<Texture>) makeTextureList(id);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Texture> getMoving() { return moving; }

    public List<Texture> makeTextureList(String id) {
        ArrayList<Texture> image = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                image.add(new Texture(id + "_moving_" + i + ".png"));
            }
        }
        return image;
    }

}
