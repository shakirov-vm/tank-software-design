package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class TapHandler {

    private final Tank player;

    public TapHandler(Tank player_) {
        player = player_;
    }

    public void handle() {

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W))
            player.setNextDirection(Direction.UP);

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A))
            player.setNextDirection(Direction.LEFT);

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S))
            player.setNextDirection(Direction.DOWN);

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D))
            player.setNextDirection(Direction.RIGHT);
    }
}
