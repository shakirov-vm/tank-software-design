package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TankLogModel;

public class ShootCommand implements Command {

    LogicLevel level;
    TankLogModel player;

    public ShootCommand(LogicLevel level_, TankLogModel player_) {
        level = level_;
        player = player_;
    }

    public void execute() {

        player.shoot(level);
    }
}
