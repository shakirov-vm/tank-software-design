package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.LogicLevel.Direction;

import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class TapHandler {

    private final TankGraphModel player;
    private final Set<TankGraphModel> enemies;
    private boolean isDrawHealthBar = false;

    public TapHandler(TankGraphModel player_, Set<TankGraphModel> enemies_) {
        player = player_;
        enemies = enemies_;
    }

    public void handle() {

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W))
            player.getTank().getNextPosition().setDirection(Direction.UP);

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A))
            player.getTank().getNextPosition().setDirection(Direction.LEFT);

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S))
            player.getTank().getNextPosition().setDirection(Direction.DOWN);

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D))
            player.getTank().getNextPosition().setDirection(Direction.RIGHT);

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(L)) {
            isDrawHealthBar = !isDrawHealthBar;
            player.setDrawHealth(isDrawHealthBar);
            for (TankGraphModel tank : enemies) {
                tank.setDrawHealth(isDrawHealthBar);
            }
        }
    }
}
