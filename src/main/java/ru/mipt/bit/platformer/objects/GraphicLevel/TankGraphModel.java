package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.LogicLevel.Position;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TankGraphModel {

    private final TankLogModel tank;

    // Graphics
    private final ModelTexture blueTank;
    private final Rectangle rectangle;

    private boolean drawHealth = true;

    public TankGraphModel(String pathToPng, TankLogModel logModel) {
        blueTank = new ModelTexture(pathToPng);
        rectangle = createBoundingRectangle(blueTank.getRegion());

        tank = logModel;
    }

    public void movePic(TileMovement tileMovement) {

        Position curr = tank.getCurrPosition();
        Position next = tank.getNextPosition();

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(rectangle,
                curr.getCoordinates(), next.getCoordinates(), tank.getPlayerMovementProgress());
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

    private Rectangle createRectangle() {
        var rectangle_ = new Rectangle(rectangle);
        rectangle_.y += 90;
        return rectangle_;
    }

    private void renderHealthbar(Batch batch) {
        var health = tank.getHealth();
        var healthbarTexture = getHealthbarTexture(health);
        var rectangle = createRectangle();
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthbarTexture, rectangle, 0f);
    }

    public void setDrawHealth(boolean isDraw) {
        drawHealth = isDraw;
    }

    public TankLogModel getTank() {
        return tank;
    }

    public void draw(Batch batch) {
        // render player
        drawTextureRegionUnscaled(batch, blueTank.getRegion(), rectangle, tank.getCurrPosition().getDirection().getAngle());
        if (drawHealth)
            renderHealthbar(batch);
    }

    public void dispose() {
        blueTank.dispose();
    }
}
