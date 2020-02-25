package ee.taltech.iti0200;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Physics extends ApplicationAdapter {

    public void move(Player player) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.getObject().setX(player.getObject().getX() -
                200 * Gdx.graphics.getDeltaTime());
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.getObject().setX(player.getObject().getX() +
                200 * Gdx.graphics.getDeltaTime());
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) player.getObject().setY(player.getObject().getY() +
                200 * Gdx.graphics.getDeltaTime());
        if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && player.getObject().getY() > 0)
            player.getObject().setY(player.getObject().getY() - 150 * Gdx.graphics.getDeltaTime());
    }
}
