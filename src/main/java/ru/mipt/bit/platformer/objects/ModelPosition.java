package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class ModelPosition {
    public final Rectangle Rectangle_;
    public final GridPoint2 Coordinates_;

    public ModelPosition(int x, int y, TextureRegion Region_) {

        Rectangle_ = createBoundingRectangle(Region_);
        Coordinates_ = new GridPoint2(x, y);
    }
}
