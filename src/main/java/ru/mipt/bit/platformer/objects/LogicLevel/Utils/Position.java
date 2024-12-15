package ru.mipt.bit.platformer.objects.LogicLevel.Utils;

import com.badlogic.gdx.math.GridPoint2;

public class Position {
    private GridPoint2 coordinates;
    private Direction direction;

    public Position(int x, int y, Direction initDirection) {
        coordinates = new GridPoint2(x, y);
        direction = initDirection;
    }
    public Position(Position copy) {
        this(copy.coordinates.x, copy.coordinates.y, copy.direction);
    }

    public void setCoordinates(GridPoint2 newCoordinates) {
        coordinates = newCoordinates;
    }
    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
    public Direction getDirection() {
        return direction;
    }
}
