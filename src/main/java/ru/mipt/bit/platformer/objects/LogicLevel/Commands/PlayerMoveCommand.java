package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

public class PlayerMoveCommand implements Command {

    Publisher publisher;
    TankLogModel player;

    public PlayerMoveCommand(Publisher publisher_, TankLogModel player_) {
        publisher = publisher_;
        player = player_;
    }

    public boolean execute() {

        // NextDirection set TapHandler
        if (publisher.tryMoveTank(player)) {
            return player.moveTank();
        }
        return false;
    }
}
