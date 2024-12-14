package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.PlayerShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.Movable;

import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;
import static ru.mipt.bit.platformer.objects.LogicLevel.Direction.randomDirection;

public class TapHandler {

    private final TankGraphModel player;
    private final Set<TankGraphModel> enemies;
    private boolean isDrawHealthBar = false;
    private LogicLevel publisher;

    public TapHandler(TankGraphModel player_, Set<TankGraphModel> enemies_, LogicLevel publisher_) {
        player = player_;
        enemies = enemies_;
        publisher = publisher_;
    }

    public void handle() {

        // Из-за этого всегда двигается!!
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            player.getTank().getNextPosition().setDirection(Direction.UP);
            if (publisher.tryMoveTank(player.getTank())) {
                (new MoveCommand((Movable) player.getTank(), Direction.UP)).execute();
            }
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            player.getTank().getNextPosition().setDirection(Direction.LEFT);
            if (publisher.tryMoveTank(player.getTank())) {
                (new MoveCommand((Movable) player.getTank(), Direction.LEFT)).execute();
            }
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            player.getTank().getNextPosition().setDirection(Direction.DOWN);
            if (publisher.tryMoveTank(player.getTank())) {
                (new MoveCommand((Movable) player.getTank(), Direction.DOWN)).execute();
            }
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            player.getTank().getNextPosition().setDirection(Direction.RIGHT);
            if (publisher.tryMoveTank(player.getTank())) {
                (new MoveCommand((Movable) player.getTank(), Direction.RIGHT)).execute();
            }
        }

        if (Gdx.input.isKeyPressed(L)) {
            isDrawHealthBar = !isDrawHealthBar;
            player.setDrawHealth(isDrawHealthBar);
            for (TankGraphModel tank : enemies) {
                tank.setDrawHealth(isDrawHealthBar);
            }
        }
        if (Gdx.input.isKeyPressed(SPACE)) {
            (new PlayerShootCommand(publisher, player.getTank())).execute();
        }
    }
}
