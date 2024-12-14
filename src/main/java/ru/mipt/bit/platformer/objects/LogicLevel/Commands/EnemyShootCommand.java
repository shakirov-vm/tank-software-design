package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Position;
import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

import java.util.Random;

public class EnemyShootCommand implements Command {

    Publisher publisher;
    TankLogModel enemy;

    private static final int FREQUENCY = 5;

    public EnemyShootCommand(Publisher publisher_, TankLogModel enemy_) {
        publisher = publisher_;
        enemy = enemy_;
    }

    private int getRandomNumberUsingNextInt(int min, int max) {

        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public boolean execute() {

        if (getRandomNumberUsingNextInt(0, FREQUENCY) % FREQUENCY == 0) {
            GridPoint2 nextCoordinates = enemy.getNextPosition().getCoordinates();
            BulletLogModel bullet = new BulletLogModel(new Position(nextCoordinates.x, nextCoordinates.y, enemy.getCurrPosition().getDirection()),
                                    new BulletMoveCommand(publisher));
            publisher.tryAddBullet(bullet);
            return true;
        }
        return false;
    }
}
