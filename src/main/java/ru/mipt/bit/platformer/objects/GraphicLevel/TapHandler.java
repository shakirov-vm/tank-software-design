package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.EnemyMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.PlayerMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.PlayerShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;

import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class TapHandler {

    private final TankGraphModel player;
    private final Set<TankGraphModel> enemies;
    private boolean isDrawHealthBar = false;
    private Publisher publisher;

    public TapHandler(TankGraphModel player_, Set<TankGraphModel> enemies_, Publisher publisher_) {
        player = player_;
        enemies = enemies_;
        publisher = publisher_;
    }

    public void handle() {

        // Из-за этого всегда двигается!!
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            player.getTank().getNextPosition().setDirection(Direction.UP);
            (new PlayerMoveCommand(publisher, player.getTank())).execute();
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            player.getTank().getNextPosition().setDirection(Direction.LEFT);
            (new PlayerMoveCommand(publisher, player.getTank())).execute();
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            player.getTank().getNextPosition().setDirection(Direction.DOWN);
            (new PlayerMoveCommand(publisher, player.getTank())).execute();
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            player.getTank().getNextPosition().setDirection(Direction.RIGHT);
            (new PlayerMoveCommand(publisher, player.getTank())).execute();
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
