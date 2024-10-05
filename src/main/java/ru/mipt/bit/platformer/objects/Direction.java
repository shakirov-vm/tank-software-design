package ru.mipt.bit.platformer.objects;

public class Direction {

    public static final float RIGHT_DIRECTION = 0f;
    public static final float UP_DIRECTION = 90f;
    public static final float LEFT_DIRECTION = -180f;
    public static final float DOWN_DIRECTION = -90f;
    public static final float INVALID_DIRECTION = -360f;

    public enum To {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    To direction;

    public Direction(To direction_) {
        direction = direction_;
    }

    public float toRotation() {
        switch (direction) {
            case RIGHT:
                return RIGHT_DIRECTION;
            case UP:
                return UP_DIRECTION;
            case LEFT:
                return LEFT_DIRECTION;
            case DOWN:
                return DOWN_DIRECTION;
        }
        assert false;
        return INVALID_DIRECTION; // Unreachable
    }
}
