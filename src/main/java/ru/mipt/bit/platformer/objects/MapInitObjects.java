package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Set;

public interface MapInitObjects {

    Set<GridPoint2> getObstacles();
    GridPoint2 getStartedCoordinates();
}
