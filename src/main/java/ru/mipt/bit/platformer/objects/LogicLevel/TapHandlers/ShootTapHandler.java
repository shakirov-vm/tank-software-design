package ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.ShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.LogInterfaces.Movable;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class ShootTapHandler implements TapHandler {

    private LogicLevel level;

    public ShootTapHandler(LogicLevel level_) {
        level = level_;
    }
    public Command handle() {
        if (Gdx.input.isKeyPressed(SPACE)) {
            return new ShootCommand(level, level.getPlayer());
        }
        return null;
    }
}
