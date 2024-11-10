package ru.mipt.bit.platformer.objects.LogicLevel;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Set;

public interface Obstacle {
    Set<GridPoint2> getBannedCoordinates();
}
