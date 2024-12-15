package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.GraphicLevel.Listener;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;

public class LogicLevel {

    Listener listener;

    private TankLogModel player;
    private Set<TreeLogModel> trees = new HashSet<>();
    private Set<TankLogModel> enemies = new HashSet<>();
    private Set<BulletLogModel> bullets = new HashSet<>();

    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    public LogicLevel(Listener listener_) { listener = listener_; }

    public Set<TankLogModel> getEnemies() {
        return enemies;
    }

    public void addPlayer(TankLogModel player_) {
        player = player_;
        listener.addPlayer(player);
    }
    public void addTree(TreeLogModel tree) {
        trees.add(tree);
        listener.addTree(tree);
    }
    public void addEnemy(TankLogModel enemy) {
        enemies.add(enemy);
        listener.addEnemy(enemy);
    }

    public void tryAddBullet(BulletLogModel bullet) {
        bullets.add(bullet);
        listener.addBullet(bullet);
    }

    private Set<GridPoint2> getObstaclesForTank(TankLogModel tank) {

        boolean isEnemies = enemies.remove(tank);

        HashSet<GridPoint2> bannedCoordinates = new HashSet<>();
        for (TreeLogModel obstacle : trees) {
            Set<GridPoint2> oneObstacleBannedCoordinates = obstacle.getBannedCoordinates();
            for (GridPoint2 coordinate : oneObstacleBannedCoordinates) {
                bannedCoordinates.add(coordinate);
            }
        }
        // if given tank is enemy, it removed; else given tank is player and need all tanks
        for (TankLogModel obstacle : enemies) {
            Set<GridPoint2> oneObstacleBannedCoordinates = obstacle.getBannedCoordinates();
            for (GridPoint2 coordinate : oneObstacleBannedCoordinates) {
                bannedCoordinates.add(coordinate);
            }
        }
        if (isEnemies) { // given tank is enemy
            Set<GridPoint2> oneObstacleBannedCoordinates = player.getBannedCoordinates();
            for (GridPoint2 coordinate : oneObstacleBannedCoordinates) {
                bannedCoordinates.add(coordinate);
            }
        }
        if (isEnemies)
            enemies.add(tank);

        return bannedCoordinates;
    }

    private boolean canMoveUp(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedY(currPosition.getCoordinates()));
        }
        return result;
    }
    private boolean canMoveDown(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedY(currPosition.getCoordinates()));
        }
        return result;
    }
    private boolean canMoveLeft(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedX(currPosition.getCoordinates()));
        }
        return result;
    }
    private boolean canMoveRight(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedX(currPosition.getCoordinates()));
        }
        return result;
    }

    public boolean tryMoveTank(TankLogModel tank, Direction direction) {

        Set<GridPoint2> obstacles = getObstaclesForTank(tank);

        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            switch (direction) {
                case UP:
                    if (canMoveUp(obstacles, tank.getCurrPosition())) return true;
                    else return false;
                case DOWN:
                    if (canMoveDown(obstacles, tank.getCurrPosition())) return true;
                    else return false;
                case LEFT:
                    if (canMoveLeft(obstacles, tank.getCurrPosition())) return true;
                    else return false;
                case RIGHT:
                    if (canMoveRight(obstacles, tank.getCurrPosition())) return true;
                    else return false;
            }
        }
        return false;
    }
    public boolean tryMoveBullet(BulletLogModel bullet) {
        if (isEqual(bullet.getPlayerMovementProgress(), 1f)) {
            return true;
        }
        return false;
    }
}
