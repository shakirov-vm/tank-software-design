package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.LogicLevel.TreeLogModel;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class TreeGraphModel {

    private final TreeLogModel tree;

    // Graphics
    private final ModelTexture greenTree;
    private final Rectangle rectangle;

    public TreeGraphModel(String pathTreePng, TreeLogModel logModel) {
        greenTree = new ModelTexture(pathTreePng);
        rectangle = createBoundingRectangle(greenTree.getRegion());

        tree = logModel;
    }

    public void draw(Batch batch) {
        // render tree obstacle
        drawTextureRegionUnscaled(batch, greenTree.getRegion(), rectangle, tree.getPosition().getDirection().getAngle());
    }

    public void rectToCenter(TiledMapTileLayer groundLayer) {

        moveRectangleAtTileCenter(groundLayer, rectangle, tree.getPosition().getCoordinates());
    }

    public void Dispose() {
        greenTree.dispose();
    }
}
