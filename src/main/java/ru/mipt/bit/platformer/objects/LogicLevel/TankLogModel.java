package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class TankLogModel implements Obstacle {

    // Model position
    private final Position currPosition;
    private final Position nextPosition;

    private Command moveCommand;
    private Command shootCommand;

    private float playerMovementProgress = 1f;
    private int health = 100;

    public TankLogModel(int x, int y, Command moveCommand_, Command shootCommand_) {
        currPosition = new Position(x, y, Direction.RIGHT);
        nextPosition = new Position(x, y, Direction.RIGHT);
        moveCommand = moveCommand_;
        shootCommand = shootCommand_;
    }

    public void setNextDirection(Direction direction) {
        nextPosition.setDirection(direction);
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }
    public void setPlayerMovementProgress(float nextPMP) {
        playerMovementProgress = nextPMP;
    }

    public int getHealth() {
        return health;
    }

    public Position getCurrPosition() { return currPosition; }
    public Position getNextPosition() { return nextPosition; } // Do we need Rotation?

    public void move() {
        moveCommand.run(this);
    }
    public void shoot() {
        shootCommand.run(this);
    }
    // Remove this
    public boolean moveTank() {
/*
        boolean moved = false;
        // Вернуть сюда логику перемещения, но moved получать из publisher
        if (isEqual(playerMovementProgress, 1f)) {
            switch (nextPosition.getDirection()) {
                case UP:
                    if (canMoveUp(obstaclesCoordinates)) {
                        nextPosition.getCoordinates().y++;
                        moved = true;
                    }
                    break;
                case DOWN:
                    if (canMoveDown(obstaclesCoordinates)) {
                        nextPosition.getCoordinates().y--;
                        moved = true;
                    }
                    break;
                case LEFT:
                    if (canMoveLeft(obstaclesCoordinates)) {
                        nextPosition.getCoordinates().x--;
                        moved = true;
                    }
                    break;
                case RIGHT:
                    if (canMoveRight(obstaclesCoordinates)) {
                        nextPosition.getCoordinates().x++;
                        moved = true;
                    }
                    break;
            }
            if (moved) {
                playerMovementProgress = 0f;
            }
            currPosition.setDirection(nextPosition.getDirection());
        }
        return moved;

 */
        return false;
    }
    public void movementProgess(float deltaTime, float movement_speed) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movement_speed);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            currPosition.setCoordinates(nextPosition.getCoordinates());
        }
    }

    // Remove this
    public boolean canMoveUp(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedY(currPosition.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveDown(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedY(currPosition.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveLeft(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedX(currPosition.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveRight(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedX(currPosition.getCoordinates()));
        }
        return result;
    }

    public Set<GridPoint2> getBannedCoordinates() {
        HashSet<GridPoint2> Coordinates = new HashSet<>(Arrays.asList(currPosition.getCoordinates(), nextPosition.getCoordinates()));
        return Coordinates;
    }
}
