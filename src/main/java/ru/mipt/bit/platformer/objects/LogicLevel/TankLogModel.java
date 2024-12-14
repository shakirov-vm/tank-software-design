package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class TankLogModel implements Obstacle, Movable {

    // Model position
    private final Position currPosition;
    private final Position nextPosition;

    private float playerMovementProgress = 1f;
    private int health = 100;

    public TankLogModel(int x, int y) {
        currPosition = new Position(x, y, Direction.RIGHT);
        nextPosition = new Position(x, y, Direction.RIGHT);
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

     public GridPoint2 getNextCoordinates(Direction direction) {
        switch (direction) {
            case UP:
                return incrementedY(currPosition.getCoordinates());
            case DOWN:
                return decrementedY(currPosition.getCoordinates());
            case LEFT:
                return decrementedX(currPosition.getCoordinates());
            case RIGHT:
                return incrementedX(currPosition.getCoordinates());
            default:
                return new GridPoint2(0, 0);
        }
    }
    public void move(Direction direction) {
        setPlayerMovementProgress(0f);
        nextPosition.setCoordinates(getNextCoordinates(direction));
        currPosition.setDirection(direction);
    }
    public void shoot() {
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
