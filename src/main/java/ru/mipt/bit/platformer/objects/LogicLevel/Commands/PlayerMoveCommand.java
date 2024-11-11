package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

public class PlayerMoveCommand implements Command {

    Publisher publisher;

    public PlayerMoveCommand(Publisher publisher_) {
        publisher = publisher_;
    }

    public boolean run(TankLogModel player) {

        // NextDirection set TapHandler
        if (publisher.tryMoveTank(player)) {
            return player.moveTank();
        }
        return false;
    }
}
