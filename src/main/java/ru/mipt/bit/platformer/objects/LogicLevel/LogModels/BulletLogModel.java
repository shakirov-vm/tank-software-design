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
        return new GridPoint2(
                currPosition.getCoordinates().x + direction.getVector().x,
                currPosition.getCoordinates().y + direction.getVector().y);
    }

    public void move() {
        playerMovementProgress = 0f;
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
