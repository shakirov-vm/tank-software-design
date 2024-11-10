package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.Obstacle;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

import java.util.HashSet;
import java.util.Set;

import static ru.mipt.bit.platformer.objects.LogicLevel.Direction.randomDirection;

public class EnemyMoveCommand implements Command {

    Set<Obstacle> obstacles;
    TankLogModel enemy;

    public EnemyMoveCommand(TankLogModel enemy_) {
        enemy = enemy_;
    }
    public void setObstacles(Set<Obstacle> obstacles_) {
        obstacles = new HashSet<>(obstacles_);
        boolean removed = obstacles.remove(enemy);
        assert (removed);
    }

    public boolean run() {
        HashSet<GridPoint2> bannedCoordinates = new HashSet<>();
        for (Obstacle obstacle : obstacles) {
            Set<GridPoint2> oneObstacleBannedCoordinates = obstacle.getBannedCoordinates();
            for (GridPoint2 coordinate : oneObstacleBannedCoordinates) {
                bannedCoordinates.add(coordinate);
            }
        }

        enemy.setNextDirection(randomDirection());
        return enemy.MoveTank(bannedCoordinates);
    }
}
