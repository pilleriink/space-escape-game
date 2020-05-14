package ee.taltech.iti0200.entities;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public enum PlayerType {

    PLAYER0("character0"),
    PLAYER1("character1"),
    PLAYER2("character2"),
    PLAYER3("character3");

    private String id;
    private ArrayList<Texture> runningRight, runningLeft;
    private ArrayList<String> right, left;
    private Texture standingRight, standingLeft, rightJumpingUp, leftJumpingUp;

    PlayerType(String id) {
        this.id = id;
        this.runningRight = (ArrayList<Texture>) makeTextureList(id, "running_right");
        this.right = (ArrayList<String>) makeStringList(id, "running_right");
        this.left = (ArrayList<String>) makeStringList(id, "running_left");
        this.runningLeft = (ArrayList<Texture>) makeTextureList(id, "running_left");
        this.standingRight = runningRight.get(0);
        this.standingLeft = runningLeft.get(0);
        this.rightJumpingUp = new Texture(id + "/" + id + "_jumping_up_right.png");
        this.leftJumpingUp = new Texture(id + "/" + id + "_jumping_up_left.png");
    }

    public ArrayList<String> getRight() {
        return right;
    }

    public ArrayList<String> getLeft() {
        return left;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Texture> getRunningRight() {
        return runningRight;
    }

    public ArrayList<Texture> getRunningLeft() {
        return runningLeft;
    }

    public Texture getStandingRight() {
        return standingRight;
    }

    public Texture getStandingLeft() {
        return standingLeft;
    }

    public Texture getRightJumpingUp() {
        return rightJumpingUp;
    }

    public Texture getLeftJumpingUp() {
        return leftJumpingUp;
    }

    public List<String> makeStringList(String id, String movement) {
        ArrayList<String> image = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                image.add(id + "/" + id + "_" + movement + "_" + i + ".png");
            }
        }
        return image;
    }

    public List<Texture> makeTextureList(String id, String movement) {
        ArrayList<Texture> image = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                image.add(new Texture(id + "/" + id + "_" + movement + "_" + i + ".png"));
            }
        }
        return image;
    }

}
