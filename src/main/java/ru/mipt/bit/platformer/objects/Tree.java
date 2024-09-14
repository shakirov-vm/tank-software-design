package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree {

    private final Texture greenTreeTexture;
    private final TextureRegion treeObstacleGraphics;
    private final GridPoint2 treeObstacleCoordinates;
    private final Rectangle treeObstacleRectangle;

    public Tree(String pathTreePng, int x, int y) {

        greenTreeTexture = new Texture(pathTreePng);
        treeObstacleGraphics = new TextureRegion(greenTreeTexture);
        treeObstacleCoordinates = new GridPoint2(x, y);
        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
    }
    public GridPoint2 getCoords() {
        return treeObstacleCoordinates;
    }

    public void draw(Batch batch) {
        // render tree obstacle
        drawTextureRegionUnscaled(batch, treeObstacleGraphics, treeObstacleRectangle, 0f);
    }

    public void rectToCenter(TiledMapTileLayer groundLayer) {

        moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, treeObstacleCoordinates);
    }

    public void Dispose() {
        greenTreeTexture.dispose();
    }
}
