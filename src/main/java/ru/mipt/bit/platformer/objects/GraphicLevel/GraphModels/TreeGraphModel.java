package ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.GraphicLevel.Utils.ModelTexture;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TreeLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Position;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class TreeGraphModel implements GraphModel {

    private final TreeLogModel tree;

    // Graphics
    private final ModelTexture greenTree;
    private final Rectangle rectangle;

    public TreeGraphModel(String pathTreePng, TreeLogModel logModel, TiledMapTileLayer groundLayer) {
        greenTree = new ModelTexture(pathTreePng);
        rectangle = createBoundingRectangle(greenTree.getRegion());
        tree = logModel;
        moveRectangleAtTileCenter(groundLayer, rectangle, tree.getPosition().getCoordinates());
    }

    public void rectToCenter(TiledMapTileLayer groundLayer) {

    }

    public void movePic(TileMovement tileMovement) {}

    @Override
    public void draw(Batch batch) {
        // render tree obstacle
        drawTextureRegionUnscaled(batch, greenTree.getRegion(), rectangle, tree.getPosition().getDirection().getAngle());
    }
    @Override
    public void dispose() {
        greenTree.dispose();
    }
    @Override
    public Class<?> getLogical() {
        return tree.getClass();
    }
    @Override
    public Object getObject() {
        return tree;
    }
    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
