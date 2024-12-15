package ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphInterfaces.Drawable;
import ru.mipt.bit.platformer.objects.GraphicLevel.Utils.ModelTexture;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphInterfaces.MovablePic;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Position;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class BulletGraphModel implements Drawable, MovablePic {

    private final BulletLogModel bullet;

    // Graphics
    private final ModelTexture fastBullet;
    private final Rectangle rectangle;

    private boolean drawHealth = true;

    public BulletGraphModel(String pathToPng, BulletLogModel logModel) {
        fastBullet = new ModelTexture(pathToPng);
        rectangle = createBoundingRectangle(fastBullet.getRegion());

        bullet = logModel;
    }

    public void movePic(TileMovement tileMovement) {

        Position curr = bullet.getCurrPosition();
        Position next = bullet.getNextPosition();

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(rectangle,
                curr.getCoordinates(), next.getCoordinates(), bullet.getPlayerMovementProgress());
    }

    public BulletLogModel getBullet() {
        return bullet;
    }

    @Override
    public void changeMovementProgess(float deltaTime, float MOVEMENT_SPEED) {
        bullet.movementProgess(deltaTime, MOVEMENT_SPEED);
    }
    @Override
    public void draw(Batch batch) {
        // render bullet
        drawTextureRegionUnscaled(batch, fastBullet.getRegion(), rectangle, bullet.getCurrPosition().getDirection().getAngle());
    }
    @Override
    public void dispose() {
        fastBullet.dispose();
    }
    @Override
    public Class<?> getLogical() {
        return bullet.getClass();
    }
    @Override
    public Object getObject() {
        return bullet;
    }
    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
