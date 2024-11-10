package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public interface Position {

    public void setCoordinates(GridPoint2 newCoordinates);
    public GridPoint2 getCoordinates();
    public Rectangle getRectangle();
    public float getRotation();
    public void setRotation(float Rotation);
}
