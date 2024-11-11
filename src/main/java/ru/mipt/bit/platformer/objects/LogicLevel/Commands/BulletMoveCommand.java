package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;

// Must implement command?
public class BulletMoveCommand {

    Publisher publisher;

    public BulletMoveCommand(Publisher publisher_) {
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
