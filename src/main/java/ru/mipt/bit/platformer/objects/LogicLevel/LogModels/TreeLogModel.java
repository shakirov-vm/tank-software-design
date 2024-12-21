package ru.mipt.bit.platformer.objects.LogicLevel.LogModels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.LogInterfaces.Obstacle;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Position;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class TreeLogModel implements Obstacle, LogModel {

    // Model position
    private final Position position;

    public TreeLogModel(int x, int y) {

        position = new Position(x, y, Direction.RIGHT);
    }

    public Position getPosition() { return position; }

    public Set<GridPoint2> getBannedCoordinates() {
        HashSet<GridPoint2> Coordinates = new HashSet<>(Arrays.asList(position.getCoordinates()));
        return Coordinates;
    }
    public void movementProgess(float deltaTime, float movement_speed) {}

}
