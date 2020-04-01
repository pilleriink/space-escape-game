package ee.taltech.iti0200.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0200.SpaceEscape;
import ee.taltech.iti0200.entities.*;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameMap {

    protected ArrayList<Entity> entities;

    public GameMap() {
        entities = new ArrayList<>();
        entities.add(new Player(800, 600, this, 500, 150, entities, PlayerType.PLAYER2));
        entities.add(new Enemy0(2000, 600, this, 10, 100, entities));
        entities.add(new Enemy1(3000, 650, this, 10, 1, entities));
    }

    public  void render (OrthographicCamera camera, SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.render(batch);
        }
    }

    public  void update (float delta) {
        for (Entity entity : entities) {
            entity.update(delta, -9.8f);
        }
    }
    public abstract void dispose ();

    /**
     * Gets a tile by pixel position within the game world at a specified layer.
     * @param layer layer
     * @param x x coord
     * @param y y coord
     * @return TileType
     */
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }


    /**
     * Get a tile at its coordinate within a map at a specified layer
     * @param layer layer
     * @param col column
     * @param row row
     * @return TileType
     */
    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);


    public boolean doesRectCollideMap(float x, float y, int width, int height) {
        if (x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight()) {
            return true;
        }

        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
            for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
                for (int layer = 0; layer < getLayers(); layer++) {
                    TileType type = getTileTypeByCoordinate(layer, col, row);
                    if (type != null && type.isCollision()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }

    public Player getPlayer() {
        return (Player) entities.get(0);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
}
