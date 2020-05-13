package ee.taltech.iti0200.entities;

public enum EntityType {

    PLAYER("player", 14, 24, 40),
    ENEMY0("enemy0", 14, 32, 40),
    ENEMY1("enemy1", 60, 54, 40),
    ENEMY2("enemy2", 5, 5, 5);


    private String id;
    private int width, height;
    private float weight;

    EntityType(String id, int width, int height, float weight) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }
}
