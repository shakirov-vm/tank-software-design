package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.LogicLevel.Liveable;

public interface Drawable {
    public void draw(Batch batch);
    public void dispose();
    public Class<?> getLogical();
    public Object getObject();
    public Rectangle getRectangle();
}
