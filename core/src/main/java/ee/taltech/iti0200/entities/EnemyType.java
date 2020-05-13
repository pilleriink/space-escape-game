package ee.taltech.iti0200.entities;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public enum EnemyType {

    ENEMY0("enemy0"),
    ENEMY1("enemy1"),
    ENEMY2("enemy2");

    private String id;
    private ArrayList<String> movingString;

    EnemyType(String id) {
        this.id = id;
        this.movingString = (ArrayList<String>) makeStringList(id);
    }

    public ArrayList<String> getMovingString() {
        return movingString;
    }

    public String getId() {
        return id;
    }


    public List<String> makeStringList(String id) {
        ArrayList<String> image = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                image.add(id + "/" + id + "_moving_" + i + ".png");
            }
        }
        return image;
    }
}
