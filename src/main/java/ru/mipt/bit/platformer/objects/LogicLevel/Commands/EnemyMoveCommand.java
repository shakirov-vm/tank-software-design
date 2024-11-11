package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

import static ru.mipt.bit.platformer.objects.LogicLevel.Direction.randomDirection;

public class EnemyMoveCommand implements Command {

    Publisher publisher;

    public EnemyMoveCommand(Publisher publisher_) {
        publisher = publisher_;
    }

    public boolean run(TankLogModel enemy) {

        enemy.setNextDirection(randomDirection());
        if (publisher.tryMoveTank(enemy)) {
            return enemy.moveTank();
        }
        return false;
    }
}
