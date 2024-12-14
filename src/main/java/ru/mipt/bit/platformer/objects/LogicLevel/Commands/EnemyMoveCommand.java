package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

import static ru.mipt.bit.platformer.objects.LogicLevel.Direction.randomDirection;

public class EnemyMoveCommand implements Command {

    Publisher publisher;
    TankLogModel enemy;

    public EnemyMoveCommand(Publisher publisher_, TankLogModel enemy_) {
        publisher = publisher_;
        enemy = enemy_;
    }

    public boolean execute() {

        enemy.setNextDirection(randomDirection());
        if (publisher.tryMoveTank(enemy)) {
            return enemy.moveTank();
        }
        return false;
    }
}
