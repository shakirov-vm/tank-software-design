package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree {

    // Graphics
    private final TextureWrap greenTree;

    // Model position
    private final Position position;

    public Tree(String pathTreePng, int x, int y) {

        greenTree = new ModelTexture(pathTreePng);
        position = new ModelPosition(x, y, greenTree.getRegion(), Direction.RIGHT_DIRECTION);
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

    public void Dispose() {
        greenTree.dispose();
    }
}
