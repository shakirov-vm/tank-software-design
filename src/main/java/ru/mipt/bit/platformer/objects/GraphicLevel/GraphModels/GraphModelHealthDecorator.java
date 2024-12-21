package ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.LogicLevel.LogInterfaces.Liveable;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import java.lang.reflect.Method;

public class GraphModelHealthDecorator implements GraphModel {

    private final GraphModel graphModel;
    private boolean drawHealth = true;

    public GraphModelHealthDecorator(GraphModel graphModel_) {
        graphModel = graphModel_;
    }

    private Rectangle createRectangle() {
        var rectangle_ = new Rectangle(graphModel.getRectangle());
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
        if (Liveable.class.isAssignableFrom(graphModel.getLogical())) {
            Method method;
            try {
                method = graphModel.getLogical().getMethod("getHealth");
                int health = (int) method.invoke(graphModel.getObject());
                var healthbarTexture = getHealthbarTexture(health);
                var rectangle = createRectangle();
                GdxGameUtils.drawTextureRegionUnscaled(batch, healthbarTexture, rectangle, 0f);
            } catch (NoSuchMethodException e) {
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void switchDrawHealth() {
        drawHealth = !drawHealth;
    }
    public void movePic(TileMovement tileMovement) {
        graphModel.movePic(tileMovement);
    }

    @Override
    public void draw(Batch batch) {
        graphModel.draw(batch);
        if (drawHealth) {
            renderHealthbar(batch);
        }
    }
    @Override
    public void dispose() {
        graphModel.dispose();
    }
    @Override
    public Class<?> getLogical() {
        return graphModel.getClass();
    }
    @Override
    public Object getObject() {
        return graphModel.getObject();
    }
    @Override
    public Rectangle getRectangle() {
        return graphModel.getRectangle();
    }
}
