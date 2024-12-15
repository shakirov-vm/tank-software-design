package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.LogInterfaces.Movable;

public class MoveCommand implements Command {

    Movable obj;
    Direction direction;

    public MoveCommand(Movable obj_, Direction direction_) {
        obj = obj_;
        direction = direction_;
    }

    public void execute() {
        obj.move(direction);
    }
}
