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

public class TankGraphModel implements Drawable {

    private final TankLogModel tank;

    // Graphics
    private final ModelTexture blueTank;
    private final Rectangle rectangle;

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
    public TankLogModel getTank() {
        return tank;
    }

    @Override
    public void draw(Batch batch) {
        // render player
        drawTextureRegionUnscaled(batch, blueTank.getRegion(), rectangle, tank.getCurrPosition().getDirection().getAngle());
    }
    @Override
    public void dispose() {
        blueTank.dispose();
    }
    @Override
    public Class<?> getLogical() {
        return tank.getClass();
    }
    @Override
    public Object getObject() {
        return tank;
    }
    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
