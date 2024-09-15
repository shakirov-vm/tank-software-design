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
    private final ModelTexture greenTree;

    // Model position
    private final ModelPosition position;

    public Tree(String pathTreePng, int x, int y) {

        greenTree = new ModelTexture(pathTreePng);
        position = new ModelPosition(x, y, greenTree.TextureRegion_);
    }
    public GridPoint2 getCoords() {
        return position.Coordinates_;
    }

    public void draw(Batch batch) {
        // render tree obstacle
        drawTextureRegionUnscaled(batch, greenTree.TextureRegion_, position.Rectangle_, 0f);
    }

    public void rectToCenter(TiledMapTileLayer groundLayer) {

        moveRectangleAtTileCenter(groundLayer, position.Rectangle_, position.Coordinates_);
    }

    public void Dispose() {
        greenTree.Texture_.dispose();
    }
}
