package ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.GraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.TankGraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.TreeGraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TankLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TreeLogModel;

public class TreeGraphGenerator implements GraphModelGenerator {

    private static final String TREE_PATH_TO_PNG = "images/greenTree.png";
    private TiledMapTileLayer groundLayer;

    public TreeGraphGenerator(TiledMapTileLayer groundLayer_) {
        groundLayer = groundLayer_;
    }

    public GraphModel createGraphModel(LogModel logModel) {
        return new TreeGraphModel(TREE_PATH_TO_PNG, (TreeLogModel) logModel, groundLayer);
    }
}
