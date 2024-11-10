package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashSet;
import java.util.Set;

public class PlayerMoveCommand implements Command {

    Set<Obstacle> obstacles;
    TankLogModel player;

    public PlayerMoveCommand(TankLogModel player_) {
        player = player_;
    }
    public void setObstacles(Set<Obstacle> obstacles_) {
        obstacles = new HashSet<>(obstacles_);
        boolean removed = obstacles.remove(player);
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
        return player.MoveTank(bannedCoordinates);
    }
}
