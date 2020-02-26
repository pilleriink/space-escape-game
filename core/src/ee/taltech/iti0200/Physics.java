package ee.taltech.iti0200;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Physics extends ApplicationAdapter {

    private Boolean isJumping;
    private float time;

    public Physics() {
        time = 1;
        isJumping = false;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }


    public void move(Player player) {
        setTime(getTime() + Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getObject().setX(player.getObject().getX() - 200 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getObject().setX(player.getObject().getX() + 200 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && getTime() > 1) {
            setTime(0);
            isJumping = true;
        }
        if (!isJumping) {
            player.getObject().setY(player.getObject().getY() - 300 * Gdx.graphics.getDeltaTime());
        }
        if (getTime() <= 0.5 && isJumping) {
            player.getObject().setY(player.getObject().getY() + 300 * Gdx.graphics.getDeltaTime());
        }
        if (getTime() > 0.5) {
            isJumping = false;
        }
    }
}
