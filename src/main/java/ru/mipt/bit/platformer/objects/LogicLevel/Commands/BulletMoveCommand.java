package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;

// Must implement command?
public class BulletMoveCommand {

    LogicLevel publisher;

    public BulletMoveCommand(LogicLevel publisher_) {
        publisher = publisher_;
    }

    public boolean run(BulletLogModel bullet) {

        // NextDirection set TapHandler // Must be movable
        if (publisher.tryMoveBullet(bullet)) {
//            return player.moveTank();
        }
        return false;
    }
}
