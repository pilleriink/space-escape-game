package ee.taltech.iti0200.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0200.entities.*;
import ee.taltech.iti0200.entities.Player0;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameMap {

    public ArrayList<Entity> entities;

    public GameMap() {
        entities = new ArrayList<>();

        //entities.add(new Enemy0(1000, 600, this, 25, 100, entities));
        //entities.add(new Enemy1(3000, 650, this, 25, 1, entities));


    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addPlayer(PlayerType playerType, Client client, String id) {
        if (playerType == PlayerType.PLAYER0) {
            entities.add(new Player0(800, 600, this, 500, 150, entities, playerType, client, id));
        } else if (playerType == PlayerType.PLAYER1) {
            entities.add(new Player1(800, 600, this, 500, 150, entities, playerType, client, id));
        } else if (playerType == PlayerType.PLAYER2) {
            entities.add(new Player2(800, 600, this, 500, 150, entities, playerType, client, id));
        } else if (playerType == PlayerType.PLAYER3) {
            entities.add(new Player3(800, 600, this, 500, 150, entities, playerType, client, id));
        }
    }

    public  void render (OrthographicCamera camera, SpriteBatch batch) {
        for (int i = 0; i < entities.size(); i ++) {
            entities.get(i).render(batch);
        }
    }

    public  void update (float delta) {
        for (int i = 0; i < entities.size(); i ++) {
            entities.get(i).update(delta, -9.8f);
        }
        //for (Entity entity : entities) {
        //  entity.update(delta, -9.8f);
        //}
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

    public Entity getPlayer() {
        return entities.get(0);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }


    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
}
