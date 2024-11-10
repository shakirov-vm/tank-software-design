package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class BulletLogModel {

    // Model position
    private final Position currPosition;
    private final Position nextPosition;

    private float playerMovementProgress = 1f;

    public BulletLogModel(int x, int y, Direction initDirection) {
        currPosition = new Position(x, y, initDirection);
        nextPosition = new Position(x, y, initDirection);
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public Position getCurrPosition() { return currPosition; }
    public Position getNextPosition() { return nextPosition; }

    public boolean MoveTank(Set<GridPoint2> obstaclesCoordinates) {

        boolean moved = false;

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
    }
    public void movementProgess(float deltaTime, float movement_speed) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movement_speed);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            currPosition.setCoordinates(nextPosition.getCoordinates());
        }
    }

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
