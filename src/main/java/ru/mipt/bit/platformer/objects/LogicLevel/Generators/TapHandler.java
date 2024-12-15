package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.GraphicLevel.TankGraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.ShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.Movable;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class TapHandler {

    private final TankLogModel player;
    private boolean isDrawHealthBar = false;
    private LogicLevel level;

    public TapHandler(TankLogModel player_, LogicLevel level_) {
        player = player_;
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
            if (level.tryMoveTank(player, nextDirection)) {
                return new MoveCommand((Movable) player, nextDirection);
            }
        }
        return null;
    }
    private Command generatePlayerShootCommand() {
        if (Gdx.input.isKeyPressed(SPACE)) {
            return new ShootCommand(level, player);
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
            commands.add(new ShootCommand(level, player));
        }

        return commands;
    }
}
