package ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.LogInterfaces.Movable;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class MoveTapHandler implements TapHandler {

    private LogicLevel level;

    public MoveTapHandler(LogicLevel level_) {
        level = level_;
    }
    public Command handle() {

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
            if (level.tryMoveTank(level.getPlayer(), nextDirection)) {
                return new MoveCommand((Movable) level.getPlayer(), nextDirection);
            }
        }
        return null;
    }
}
