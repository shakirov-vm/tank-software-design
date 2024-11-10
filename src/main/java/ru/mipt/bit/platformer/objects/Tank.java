package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank implements Obstacle {

    // Graphics
    private final TextureWrap blueTank;

    // Model position
    private final Position position;

    private Command moveCommand;

    // which tile the player want to go next
    private final GridPoint2 DestinationCoordinates;
    private Direction nextDirection;
    private float playerMovementProgress = 1f;

    private boolean drawHealth = true;

    void setNextDirection(Direction direction) {
        nextDirection = direction;
    }

    public Tank(String pathToPng, int x, int y) {

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        blueTank = new ModelTexture(pathToPng);
        position = new ModelPosition(x, y, blueTank.getRegion(), Direction.RIGHT.getAngle());
        setNextDirection(Direction.RIGHT);

        // set player initial position
        DestinationCoordinates = new GridPoint2(x, y);

    }

    public void setMoveCommand(Command moveCommand_) {
        moveCommand = moveCommand_;
    }
    public Command getMoveCommand() {
        return moveCommand;
    }

    public void move() {
        moveCommand.run();
    }

    public boolean MoveTank(Set<GridPoint2> obstaclesCoordinates) {

        boolean moved = false;

        if (isEqual(playerMovementProgress, 1f)) {
            switch (nextDirection) {
                case UP:
                    if (canMoveUp(obstaclesCoordinates)) {
                        DestinationCoordinates.y++;
                        moved = true;
                    }
                    break;
                case DOWN:
                    if (canMoveDown(obstaclesCoordinates)) {
                        DestinationCoordinates.y--;
                        moved = true;
                    }
                    break;
                case LEFT:
                    if (canMoveLeft(obstaclesCoordinates)) {
                        DestinationCoordinates.x--;
                        moved = true;
                    }
                    break;
                case RIGHT:
                    if (canMoveRight(obstaclesCoordinates)) {
                        DestinationCoordinates.x++;
                        moved = true;
                    }
                    break;
            }
            if (moved) {
                playerMovementProgress = 0f;
            }
            position.setRotation(nextDirection.getAngle());
        }
        return moved;
    }
    public void movementProgess(float deltaTime, float movement_speed) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movement_speed);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            position.setCoordinates(DestinationCoordinates);
        }
    }

    public void movePic(TileMovement tileMovement) {
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(position.getRectangle(), position.getCoordinates(), DestinationCoordinates, playerMovementProgress);
    }

    public boolean canMoveUp(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedY(position.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveDown(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedY(position.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveLeft(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(decrementedX(position.getCoordinates()));
        }
        return result;
    }
    public boolean canMoveRight(Set<GridPoint2> obstacles) {
        boolean result = true;
        for (GridPoint2 coords : obstacles) {
            result = result && !coords.equals(incrementedX(position.getCoordinates()));
        }
        return result;
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
        var rectangle = new Rectangle(position.getRectangle());
        rectangle.y += 90;
        return rectangle;
    }

    private void renderHealthbar(Batch batch) {
        var health = 100;
        var healthbarTexture = getHealthbarTexture(health);
        var rectangle = createRectangle();
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthbarTexture, rectangle, 0f);
    }

    public void setDrawHealth(boolean isDraw) {
        drawHealth = isDraw;
    }

    public void draw(Batch batch) {
        // render player
        drawTextureRegionUnscaled(batch, blueTank.getRegion(), position.getRectangle(), position.getRotation());
        if (drawHealth)
            renderHealthbar(batch);
    }

    public Set<GridPoint2> getProhobitedCoordinates() {
        HashSet<GridPoint2> Coordinates = new HashSet<>(Arrays.asList(position.getCoordinates(), DestinationCoordinates));
        return Coordinates;
    }

    public void dispose() {
        blueTank.dispose();
    }
}
