package ee.taltech.iti0200.world;

import java.util.HashMap;
import java.util.HashSet;

public enum TileType {

    GRASS(1, true, "Grass"),
    DIRT(2, true, "Dirt"),
    SKY(3, false, "Sky"),
    LAVA(4, false, "Lava"),
    CLOUD(5, true, "Cloud"),
    STONE(6, false, "Stone");

    public static final int TILE_SIZE = 16;


    private int id;
    private boolean collision;
    private String name;
    private float damage;


    private TileType (int id, boolean collision, String name) {
        this(id, collision, name, 0);
    }

    private TileType(int id, boolean collision, String name, float damage) {
        this.id = id;
        this.collision = collision;
        this.name = name;
        this.damage = damage;
    }

    public int getId() {
        return id;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    private static HashMap<Integer, TileType> tileMap = new HashMap<>();

    static {
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById (int id) {
        return tileMap.get(id);
    }
}
