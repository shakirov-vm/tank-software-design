package ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator;

import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.BulletGraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.GraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;

public class BulletGraphGenerator implements GraphModelGenerator {

    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    public GraphModel createGraphModel(LogModel logModel) {
        return new BulletGraphModel(BULLET_PATH_TO_PNG, (BulletLogModel) logModel);
    }
}
