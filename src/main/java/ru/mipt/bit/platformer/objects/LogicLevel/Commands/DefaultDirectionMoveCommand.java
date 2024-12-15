package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.Movable;

public class DefaultDirectionMoveCommand {
    BulletLogModel obj;

    public DefaultDirectionMoveCommand(BulletLogModel obj_) {
        obj = obj_;
    }

    public void execute() {
        obj.move();
    }
}
