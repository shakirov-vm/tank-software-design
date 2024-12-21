package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.GraphicLevel.Drawer;
import ru.mipt.bit.platformer.objects.GraphicLevel.LogicListener;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TankLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TreeLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;

public class LogicLevel {

    private static final float MOVEMENT_SPEED = 0.4f;

    private TankLogModel player;
    private Set<TreeLogModel> trees = new HashSet<>();
    private Set<TankLogModel> enemies = new HashSet<>();
    private Set<BulletLogModel> bullets = new HashSet<>();

    private Set<LogicListener> subscribers = new HashSet<>();

    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    public void initialize(MapInitObjects initObjects) {
        for (GridPoint2 coord : initObjects.getObstacles()) {
            addTree(new TreeLogModel(coord.x, coord.y));
        }

        for (GridPoint2 coord : initObjects.getStartedEnemies()) {
            addEnemy(new TankLogModel(coord.x, coord.y));
        }
        addPlayer(new TankLogModel(initObjects.getStartedCoordinates().x, initObjects.getStartedCoordinates().y));
    }
    public void subscribe(LogicListener subscriber) {
        subscribers.add(subscriber);
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
        for (LogicListener subscriber : subscribers) {
            subscriber.addModel(player);
        }
    }
    private void addTree(TreeLogModel tree) {
        trees.add(tree);
        for (LogicListener subscriber : subscribers) {
            subscriber.addModel(tree);
        }
    }
    private void addEnemy(TankLogModel enemy) {
        enemies.add(enemy);
        for (LogicListener subscriber : subscribers) {
            subscriber.addModel(enemy);
        }
    }

    public void addBullet(BulletLogModel bullet) {
        bullets.add(bullet);
        for (LogicListener subscriber : subscribers) {
            subscriber.addModel(bullet);
        }
    }
    public void removeBullet(BulletLogModel bullet) {
        for (LogicListener subscriber : subscribers) {
            subscriber.removeModel(bullet);
        }
        bullets.remove(bullet);
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

        Set<GridPoint2> obstacles = getObstaclesForBullet();

        if (isEqual(bullet.getPlayerMovementProgress(), 1f)) {
            switch (bullet.getCurrPosition().getDirection()) {
                case UP:
                    if (canMoveUp(obstacles, bullet.getCurrPosition())) return true;
                    else {
                        removeBullet(bullet);
                        return false;
                    }
                case DOWN:
                    if (canMoveDown(obstacles, bullet.getCurrPosition())) return true;
                    else {
                        removeBullet(bullet);
                        return false;
                    }
                case LEFT:
                    if (canMoveLeft(obstacles, bullet.getCurrPosition())) return true;
                    else {
                        removeBullet(bullet);
                        return false;
                    }
                case RIGHT:
                    if (canMoveRight(obstacles, bullet.getCurrPosition())) return true;
                    else {
                        removeBullet(bullet);
                        return false;
                    }
            }
        }
        return false;
    }

    public Set<LogModel> getModels() {
        Set<LogModel> models = new HashSet<>();
        models.addAll(enemies);
        models.addAll(trees);
        models.addAll(bullets);
        models.add(player);
        return models;
    }

    public void update(float deltaTime) {
        for (LogModel model : getModels()) {
            model.movementProgess(deltaTime, MOVEMENT_SPEED);
        }
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
    private Set<GridPoint2> getObstaclesForBullet() {

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
}
