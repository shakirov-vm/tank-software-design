package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashSet;
import java.util.Set;

import static ru.mipt.bit.platformer.objects.Direction.randomDirection;

public class EnemyMoveCommand implements Command {

    Set<Obstacle> obstacles;
    Tank enemy;

    public EnemyMoveCommand(Tank enemy_) {
        enemy = enemy_;
    }
    public void setObstacles(Set<Obstacle> obstacles_) {
        obstacles = new HashSet<>(obstacles_);
        boolean removed = obstacles.remove(enemy);
        assert (removed);
    }

    public boolean run() {
        HashSet<GridPoint2> prohibitedCoordinates = new HashSet<>();
        for (Obstacle obstacle : obstacles) {
            Set<GridPoint2> oneObstacleProhibitedCoordinates = obstacle.getProhobitedCoordinates();
            for (GridPoint2 coordinate : oneObstacleProhibitedCoordinates) {
                prohibitedCoordinates.add(coordinate);
            }
        }

        enemy.setNextDirection(randomDirection());
        return enemy.MoveTank(prohibitedCoordinates);
    }
}
