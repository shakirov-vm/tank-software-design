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

    private final Texture blueTankTexture;
    private final TextureRegion playerGraphics;
    private final Rectangle playerRectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 playerCoordinates;
    // which tile the player want to go next
    private final GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;
    private float playerRotation;

    public Tank(String pathToPng, int x, int y) {

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture(pathToPng);
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        playerGraphics = new TextureRegion(blueTankTexture);
        playerRectangle = createBoundingRectangle(playerGraphics);
        // set player initial position
        playerDestinationCoordinates = new GridPoint2(1, 1);
        playerCoordinates = new GridPoint2(playerDestinationCoordinates);
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
            playerCoordinates.set(playerDestinationCoordinates);
        }
    }

    public void movePic(TileMovement tileMovement) {
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(playerRectangle, playerCoordinates, playerDestinationCoordinates, playerMovementProgress);
    }

    public boolean canMoveUp(GridPoint2 obstacle) {
        return !obstacle.equals(incrementedY(playerCoordinates));
    }
    public boolean canMoveDown(GridPoint2 obstacle) {
        return !obstacle.equals(decrementedY(playerCoordinates));
    }
    public boolean canMoveLeft(GridPoint2 obstacle) {
        return !obstacle.equals(decrementedX(playerCoordinates));
    }
    public boolean canMoveRight(GridPoint2 obstacle) {
        return !obstacle.equals(incrementedX(playerCoordinates));
    }

    public void draw(Batch batch) {
        // render player
        drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle, playerRotation);
    }

    public void dispose() {
        blueTankTexture.dispose();
    }
}
