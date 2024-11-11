package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

public class PlayerShotCommand implements Command {

    Publisher publisher;

    public PlayerShotCommand(Publisher publisher_) { publisher = publisher_; }

    public boolean run(TankLogModel player) {

        GridPoint2 nextCoordinates = player.getNextPosition().getCoordinates();
        BulletLogModel bullet = new BulletLogModel(nextCoordinates.x, nextCoordinates.y, player.getCurrPosition().getDirection());
        publisher.tryAddBullet(bullet);

        return true;
    }
}
