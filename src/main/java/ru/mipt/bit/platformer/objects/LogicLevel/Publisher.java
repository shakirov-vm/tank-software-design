package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Null;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;

public class Publisher {

    private TankLogModel player;
    private Set<TreeLogModel> trees = new HashSet<>();
    private Set<TankLogModel> enemies = new HashSet<>();
    private Set<BulletLogModel> bullets = new HashSet<>();

    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    public Publisher() {}

    public void addPlayer(TankLogModel player_) {
        player = player_;
    }
    public void addTree(TreeLogModel tree) {
        trees.add(tree);
    }
    public void addEnemy(TankLogModel enemy) {
        enemies.add(enemy);
    }

    public void tryAddBullet(BulletLogModel bullet) {

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

    public boolean canMoveUp(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedY(currPosition.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveDown(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedY(currPosition.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveLeft(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedX(currPosition.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveRight(Set<GridPoint2> obstacles, Position currPosition) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedX(currPosition.getCoordinates()));
        }
        return result;
    }
    // Перед вызовом tryMoveTank устанавливается nextDirection, в который надо двигаться
    // Перенести всё сюда из moveTank и tapHandler
    public boolean tryMoveTank(TankLogModel tank) {

        Set<GridPoint2> obstacles = getObstaclesForTank(tank);

        boolean moved = false;

        // Вся эта логика должна остаться в moveTank, чтобы не раскрывать лишнего, publisher должен лишь отвечать на вопрос
        // двигать или не двигать!
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            switch (tank.getNextPosition().getDirection()) {
                case UP:
                    if (canMoveUp(obstacles, tank.getCurrPosition())) {
                        tank.getNextPosition().getCoordinates().y++;
                        moved = true;
                    }
                    break;
                case DOWN:
                    if (canMoveDown(obstacles, tank.getCurrPosition())) {
                        tank.getNextPosition().getCoordinates().y--;
                        moved = true;
                    }
                    break;
                case LEFT:
                    if (canMoveLeft(obstacles, tank.getCurrPosition())) {
                        tank.getNextPosition().getCoordinates().x--;
                        moved = true;
                    }
                    break;
                case RIGHT:
                    if (canMoveRight(obstacles, tank.getCurrPosition())) {
                        tank.getNextPosition().getCoordinates().x++;
                        moved = true;
                    }
                    break;
            }
            if (moved) {
                tank.setPlayerMovementProgress(0f);
            }
            tank.getCurrPosition().setDirection(tank.getNextPosition().getDirection());
        }
        return moved;
    }
}
