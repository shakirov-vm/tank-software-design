package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class TapHandler {

    private final Tank player;
    private final Tree singleTree;

    public TapHandler(Tank player_, Tree singleTree_) {

        player = player_;
        singleTree = singleTree_;
    }

    public void handle() {

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W))
            player.MoveTank(player.canMoveUp(singleTree.getCoords()), new Direction(Direction.To.UP));

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A))
            player.MoveTank(player.canMoveLeft(singleTree.getCoords()), new Direction(Direction.To.LEFT));

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S))
            player.MoveTank(player.canMoveDown(singleTree.getCoords()), new Direction(Direction.To.DOWN));

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D))
            player.MoveTank(player.canMoveRight(singleTree.getCoords()), new Direction(Direction.To.RIGHT));
    }
}
