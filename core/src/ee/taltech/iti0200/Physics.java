package ee.taltech.iti0200;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Physics {

    public void moveLeftAndRight(Player player) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.getObject().setX(player.getObject().getX() -
                200 * Gdx.graphics.getDeltaTime());
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.getObject().setX(player.getObject().getX() +
                200 * Gdx.graphics.getDeltaTime());
    }

    public void moveUpAndDown(Player player) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) player.getObject().setY(player.getObject().getY() +
                200 * Gdx.graphics.getDeltaTime());
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.getObject().setY(player.getObject().getY() -
                200 * Gdx.graphics.getDeltaTime());
    }

}
