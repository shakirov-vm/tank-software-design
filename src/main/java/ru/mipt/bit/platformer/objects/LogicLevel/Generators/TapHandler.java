package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.GraphicLevel.TankGraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.ShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.Movable;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class TapHandler {

    private final TankGraphModel player;
    private final Set<TankGraphModel> enemies;
    private boolean isDrawHealthBar = false;
    private LogicLevel level;

    public TapHandler(TankGraphModel player_, Set<TankGraphModel> enemies_, LogicLevel level_) {
        player = player_;
        enemies = enemies_;
        level = level_;
    }

    private Command generatePlayerMoveCommand() {
        boolean movePressed = false;
        Direction nextDirection = Direction.LEFT;

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            movePressed = true;
            nextDirection = Direction.UP;
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            movePressed = true;
            nextDirection = Direction.LEFT;
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            movePressed = true;
            nextDirection = Direction.DOWN;
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            movePressed = true;
            nextDirection = Direction.RIGHT;
        }
        if (movePressed) {
            if (level.tryMoveTank(player.getTank(), nextDirection)) {
                return new MoveCommand((Movable) player.getTank(), nextDirection);
            }
        }
        return null;
    }
    private Command generatePlayerShootCommand() {
        if (Gdx.input.isKeyPressed(SPACE)) {
            return new ShootCommand(level, player.getTank());
        }
        return null;
    }

    public Set<Command> generateKeysCommands() {

        Set<Command> commands = new HashSet<>();

        Command nextCommand = generatePlayerMoveCommand();
        if (nextCommand != null) {
            commands.add(nextCommand);
        }
        if (Gdx.input.isKeyPressed(SPACE)) {
            (new ShootCommand(level, player.getTank())).execute();
        }

        if (Gdx.input.isKeyPressed(L)) {
            isDrawHealthBar = !isDrawHealthBar;
            player.setDrawHealth(isDrawHealthBar);
            for (TankGraphModel tank : enemies) {
                tank.setDrawHealth(isDrawHealthBar);
            }
        }
        return commands;
    }
}
