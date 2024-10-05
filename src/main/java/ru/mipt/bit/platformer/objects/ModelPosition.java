package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class ModelPosition implements Position {
    public final Rectangle Rectangle_;
    public final GridPoint2 Coordinates_;
    private float Rotation_;

    public ModelPosition(int x, int y, TextureRegion Region, float initRotation) {

        Rectangle_ = createBoundingRectangle(Region);
        Coordinates_ = new GridPoint2(x, y);
        Rotation_ = initRotation;
    }

    public void setCoordinates(GridPoint2 newCoordinates) {
        Coordinates_.set(newCoordinates);
    }
    public GridPoint2 getCoordinates() {
        return Coordinates_;
    }
    public Rectangle getRectangle() {
        return Rectangle_;
    }
    public float getRotation() {
        return Rotation_;
    }
    public void setRotation(float newRotation) {
        Rotation_ = newRotation;
    }
}
