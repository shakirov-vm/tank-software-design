package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.GraphicLevel.Drawer;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;

public class LogicLevel {

    Drawer drawer;

    private TankLogModel player;
    private Set<TreeLogModel> trees = new HashSet<>();
    private Set<TankLogModel> enemies = new HashSet<>();
    private Set<BulletLogModel> bullets = new HashSet<>();

    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    private void initializeWithObjects(MapInitObjects initObjects) {
        for (GridPoint2 coord : initObjects.getObstacles()) {
            addTree(new TreeLogModel(coord.x, coord.y));
        }

        for (GridPoint2 coord : initObjects.getStartedEnemies()) {
            addEnemy(new TankLogModel(coord.x, coord.y));
        }
        addPlayer(new TankLogModel(initObjects.getStartedCoordinates().x, initObjects.getStartedCoordinates().y));
    }

    public LogicLevel(Drawer drawer_, MapInitObjects initObjects) {
        drawer = drawer_;
        initializeWithObjects(initObjects);
    }

    public TankLogModel getPlayer() {
        return player;
    }
    public Set<TankLogModel> getEnemies() {
        return enemies;
    }
    public Set<BulletLogModel> getBullets() {
        return bullets;
    }

    private void addPlayer(TankLogModel player_) {
        player = player_;
        drawer.addPlayer(player);
    }
    private void addTree(TreeLogModel tree) {
        trees.add(tree);
        drawer.addTree(tree);
    }
    private void addEnemy(TankLogModel enemy) {
        enemies.add(enemy);
        drawer.addEnemy(enemy);
    }

    public void tryAddBullet(BulletLogModel bullet) {
        bullets.add(bullet);
        drawer.addBullet(bullet);
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
