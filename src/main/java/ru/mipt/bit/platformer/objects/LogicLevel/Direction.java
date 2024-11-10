package ru.mipt.bit.platformer.objects.LogicLevel;
import java.util.Random;

public enum Direction {

    UP (90f),
    DOWN (-90f),
    LEFT (-180f),
    RIGHT (0f);

    private float angle;
    Direction(final float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    private static final Random rdm = new Random();
    private static final Direction[] directions = values();

    public static Direction randomDirection()  {
        Direction[] directions = values();
        return directions[rdm.nextInt(directions.length)];
    }
}
