package ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphInterfaces.Drawable;
import ru.mipt.bit.platformer.objects.LogicLevel.LogInterfaces.Liveable;
import ru.mipt.bit.platformer.util.GdxGameUtils;

import java.lang.reflect.Method;

public class DrawableHealthDecorator implements Drawable {

    private final Drawable drawable;
    private boolean drawHealth = true;

    public DrawableHealthDecorator(Drawable drawable_) {
        drawable = drawable_;
    }

    private Rectangle createRectangle() {
        var rectangle_ = new Rectangle(drawable.getRectangle());
        rectangle_.y += 90;
        return rectangle_;
    }
    private TextureRegion getHealthbarTexture(float relativeHealth) {
        var pixmap = new Pixmap(90, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, 90, 20);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, (int) (90 * relativeHealth), 20);
        var texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }
    private void renderHealthbar(Batch batch) {
        if (Liveable.class.isAssignableFrom(drawable.getLogical())) {
            Method method;
            try {
                method = drawable.getLogical().getMethod("getHealth");
                int health = (int) method.invoke(drawable.getObject());
                var healthbarTexture = getHealthbarTexture(health);
                var rectangle = createRectangle();
                GdxGameUtils.drawTextureRegionUnscaled(batch, healthbarTexture, rectangle, 0f);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void switchDrawHealth() {
        drawHealth = !drawHealth;
    }

    @Override
    public void draw(Batch batch) {
        drawable.draw(batch);
        if (drawHealth){
            renderHealthbar(batch);
        }
    }
    @Override
    public void dispose() {
        drawable.dispose();
    }
    @Override
    public Class<?> getLogical() {
        return drawable.getClass();
    }
    @Override
    public Object getObject() {
        return drawable.getObject();
    }
    @Override
    public Rectangle getRectangle() {
        return drawable.getRectangle();
    }
}
