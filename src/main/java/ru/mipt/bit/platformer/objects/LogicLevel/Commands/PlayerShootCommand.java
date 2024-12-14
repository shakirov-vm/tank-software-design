package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Position;
import ru.mipt.bit.platformer.objects.LogicLevel.Publisher;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

public class PlayerShootCommand implements Command {

    Publisher publisher;
    TankLogModel player;

    public PlayerShootCommand(Publisher publisher_, TankLogModel player_) {
        publisher = publisher_;
        player = player_;
    }

    public boolean execute() {

        GridPoint2 nextCoordinates = player.getNextPosition().getCoordinates();
        BulletLogModel bullet = new BulletLogModel(new Position(nextCoordinates.x, nextCoordinates.y, player.getCurrPosition().getDirection()),
                                                    new BulletMoveCommand(publisher));
        publisher.tryAddBullet(bullet);

        return true;
    }
}
