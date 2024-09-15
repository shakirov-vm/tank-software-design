package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {

    // Graphics
    private final ModelTexture blueTank;

    // Model position
    private final ModelPosition position;
    private float playerRotation;

    // which tile the player want to go next
    private final GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;

    public Tank(String pathToPng, int x, int y) {

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        blueTank = new ModelTexture(pathToPng);
        position = new ModelPosition(x, y, blueTank.TextureRegion_);

        // set player initial position
        playerDestinationCoordinates = new GridPoint2(x, y);
        playerRotation = 0f;
    }

    public void MoveTank(boolean isMove, float Rotation, Movements action) {
        if (isEqual(playerMovementProgress, 1f)) {
            if (isMove) {
                switch (action) {
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
            playerRotation = Rotation;
        }
    }
    public void movementProgess(float deltaTime, float movement_speed) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movement_speed);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            position.Coordinates_.set(playerDestinationCoordinates);
        }
    }

    public void movePic(TileMovement tileMovement) {
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(position.Rectangle_, position.Coordinates_, playerDestinationCoordinates, playerMovementProgress);
    }

    public boolean canMoveUp(GridPoint2 obstacle) {
        return !obstacle.equals(incrementedY(position.Coordinates_));
    }
    public boolean canMoveDown(GridPoint2 obstacle) {
        return !obstacle.equals(decrementedY(position.Coordinates_));
    }
    public boolean canMoveLeft(GridPoint2 obstacle) {
        return !obstacle.equals(decrementedX(position.Coordinates_));
    }
    public boolean canMoveRight(GridPoint2 obstacle) {
        return !obstacle.equals(incrementedX(position.Coordinates_));
    }

    public void draw(Batch batch) {
        // render player
        drawTextureRegionUnscaled(batch, blueTank.TextureRegion_, position.Rectangle_, playerRotation);
    }

    public void dispose() {
        blueTank.Texture_.dispose();
    }
}
