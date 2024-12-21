package ru.mipt.bit.platformer.objects.LogicLevel.LogModels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Position;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class BulletLogModel implements LogModel {

    // Model position
    private final Position currPosition;
    private final Position nextPosition;

    private float playerMovementProgress = 1f;

    public BulletLogModel(Position newPosition) {
        currPosition = new Position(newPosition);
        nextPosition = new Position(newPosition);
    }

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
    public void setPlayerMovementProgress(float nextPMP) {
        playerMovementProgress = nextPMP;
    }

    public void move() {
        setPlayerMovementProgress(0f);
        nextPosition.setCoordinates(getNextCoordinates(getCurrPosition().getDirection()));
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public Position getCurrPosition() { return currPosition; }
    public Position getNextPosition() { return nextPosition; }

    public void movementProgess(float deltaTime, float movement_speed) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movement_speed);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            currPosition.setCoordinates(nextPosition.getCoordinates());
        }
    }
}
