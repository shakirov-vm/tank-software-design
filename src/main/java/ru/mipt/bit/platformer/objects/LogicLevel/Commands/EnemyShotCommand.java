package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

import java.util.Random;

public class EnemyShotCommand {

    Publisher publisher;

    private static final int FREQUENCY = 5;

    public EnemyShotCommand(Publisher publisher_) { publisher = publisher_; }

    private int getRandomNumberUsingNextInt(int min, int max) {

        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public boolean run(TankLogModel enemy) {

        if (getRandomNumberUsingNextInt(0, FREQUENCY) % FREQUENCY == 0) {
            GridPoint2 nextCoordinates = enemy.getNextPosition().getCoordinates();
            BulletLogModel bullet = new BulletLogModel(nextCoordinates.x, nextCoordinates.y, enemy.getCurrPosition().getDirection());
            publisher.tryAddBullet(bullet);
            return true;
        }
        return false;
    }
}
