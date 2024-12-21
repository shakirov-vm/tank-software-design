package ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.GraphicLevel.Drawer;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.GraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.GraphModelHealthDecorator;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.SwitchHealthCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.LogInterfaces.Movable;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class HealthBarTapHandler implements TapHandler {
    private Drawer drawer;

    public HealthBarTapHandler(Drawer drawer_) {
        drawer = drawer_;
    }
    public Command handle() {
        if (Gdx.input.isKeyPressed(L)) {
            return new SwitchHealthCommand(drawer);
        }
        return null;
    }
}
