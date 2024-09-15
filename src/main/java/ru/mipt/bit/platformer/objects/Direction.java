package ru.mipt.bit.platformer.objects;

public class Direction {

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

    float toRotation() {
        switch (direction) {
            case RIGHT:
                return 0f;
            case UP:
                return 90f;
            case LEFT:
                return -180f;
            case DOWN:
                return -90f;
        }
        assert false;
        return 0f; // Unreachable
    }
}
