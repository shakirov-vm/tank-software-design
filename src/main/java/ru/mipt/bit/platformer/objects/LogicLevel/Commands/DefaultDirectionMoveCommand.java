package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;

public class DefaultDirectionMoveCommand implements Command {
    BulletLogModel obj;

    public DefaultDirectionMoveCommand(BulletLogModel obj_) {
        obj = obj_;
    }

    public void execute() {
        obj.move();
    }
}
