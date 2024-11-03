package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree implements Obstacle {

    // Graphics
    private final TextureWrap greenTree;

    // Model position
    private final Position position;

    public Tree(String pathTreePng, int x, int y) {

        greenTree = new ModelTexture(pathTreePng);
        position = new ModelPosition(x, y, greenTree.getRegion(), Direction.RIGHT.getAngle());
    }
    public GridPoint2 getCoords() {
        return position.getCoordinates();
    }

    public void draw(Batch batch) {
        // render tree obstacle
        drawTextureRegionUnscaled(batch, greenTree.getRegion(), position.getRectangle(), position.getRotation());
    }

    public void rectToCenter(TiledMapTileLayer groundLayer) {

        moveRectangleAtTileCenter(groundLayer, position.getRectangle(), position.getCoordinates());
    }

    public Set<GridPoint2> getProhobitedCoordinates() {
        HashSet<GridPoint2> Coordinates = new HashSet<>(Arrays.asList(position.getCoordinates()));
        return Coordinates;
    }

    public void Dispose() {
        greenTree.dispose();
    }
}
