package ru.mipt.bit.platformer.objects.LogicLevel.Utils;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Random;

public enum Direction {

    UP (90f, new GridPoint2(0, 1)),
    DOWN (-90f, new GridPoint2(0, -1)),
    LEFT (-180f, new GridPoint2(-1, 0)),
    RIGHT (0f, new GridPoint2(1, 0));

    private float angle;
    private final GridPoint2 vector;

    Direction(final float angle, GridPoint2 vector) {
        this.angle = angle;
        this.vector = vector;
    }

    public float getAngle() {
        return angle;
    }
    public GridPoint2 getVector(){
        return vector;
    }

    private static final Random rdm = new Random();
    private static final Direction[] directions = values();

    public static Direction randomDirection()  {
        Direction[] directions = values();
        return directions[rdm.nextInt(directions.length)];
    }
}
