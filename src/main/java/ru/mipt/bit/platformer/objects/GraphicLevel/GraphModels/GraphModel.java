package ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

public interface GraphModel {
    public void draw(Batch batch);
    public void dispose();
    public Class<?> getLogical();
    public Object getObject();
    public Rectangle getRectangle();
    void movePic(TileMovement tileMovement);
}
