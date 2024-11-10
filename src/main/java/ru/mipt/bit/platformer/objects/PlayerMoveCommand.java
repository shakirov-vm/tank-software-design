package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashSet;
import java.util.Set;

public class PlayerMoveCommand implements Command {

    Set<Obstacle> obstacles;
    Tank player;

    public PlayerMoveCommand(Tank player_) {
        player = player_;
    }
    public void setObstacles(Set<Obstacle> obstacles_) {
        obstacles = new HashSet<>(obstacles_);
        boolean removed = obstacles.remove(player);
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
        return player.MoveTank(prohibitedCoordinates);
    }
}
