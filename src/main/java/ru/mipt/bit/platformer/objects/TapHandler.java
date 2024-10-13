package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class TapHandler {

    private final Tank player;
    private final Set<GridPoint2> obstacles;

    public TapHandler(Tank player_, Set<GridPoint2> obstacles_) {

        player = player_;
        obstacles = obstacles_;
    }

    public void handle() {

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W))
            player.MoveTank(player.canMoveUp(obstacles), new Direction(Direction.To.UP));

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A))
            player.MoveTank(player.canMoveLeft(obstacles), new Direction(Direction.To.LEFT));

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S))
            player.MoveTank(player.canMoveDown(obstacles), new Direction(Direction.To.DOWN));

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D))
            player.MoveTank(player.canMoveRight(obstacles), new Direction(Direction.To.RIGHT));
    }
}
