package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {

    // Graphics
    private final TextureWrap blueTank;

    // Model position
    private final Position position;

    // which tile the player want to go next
    private final GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;

    public Tank(String pathToPng, int x, int y) {

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        blueTank = new ModelTexture(pathToPng);
        position = new ModelPosition(x, y, blueTank.getRegion(), Direction.RIGHT_DIRECTION);

        // set player initial position
        playerDestinationCoordinates = new GridPoint2(x, y);
    }

    public void MoveTank(boolean isMove, Direction action) {
        if (isEqual(playerMovementProgress, 1f)) {
            if (isMove) {
                switch (action.direction) {
                    case UP:
                        playerDestinationCoordinates.y++;
                        break;
                    case DOWN:
                        playerDestinationCoordinates.y--;
                        break;
                    case LEFT:
                        playerDestinationCoordinates.x--;
                        break;
                    case RIGHT:
                        playerDestinationCoordinates.x++;
                        break;
                }

                playerMovementProgress = 0f;
            }
            position.setRotation(action.toRotation());
        }
    }
    public void movementProgess(float deltaTime, float movement_speed) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movement_speed);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            position.setCoordinates(playerDestinationCoordinates);
        }
    }

    public void movePic(TileMovement tileMovement) {
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(position.getRectangle(), position.getCoordinates(), playerDestinationCoordinates, playerMovementProgress);
    }

    public boolean canMoveUp(GridPoint2 obstacle) {
        return !obstacle.equals(incrementedY(position.getCoordinates()));
    }
    public boolean canMoveDown(GridPoint2 obstacle) { return !obstacle.equals(decrementedY(position.getCoordinates())); }
    public boolean canMoveLeft(GridPoint2 obstacle) { return !obstacle.equals(decrementedX(position.getCoordinates())); }
    public boolean canMoveRight(GridPoint2 obstacle) { return !obstacle.equals(incrementedX(position.getCoordinates())); }

    public void draw(Batch batch) {
        // render player
        drawTextureRegionUnscaled(batch, blueTank.getRegion(), position.getRectangle(), position.getRotation());
    }

    public void dispose() {
        blueTank.dispose();
    }
}
