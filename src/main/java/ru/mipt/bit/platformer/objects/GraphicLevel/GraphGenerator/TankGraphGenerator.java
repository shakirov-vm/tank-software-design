package ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator;

import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.BulletGraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.GraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.TankGraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TankLogModel;

public class TankGraphGenerator implements GraphModelGenerator {

    private static final String TANK_PATH_TO_PNG = "images/tank_blue.png";

    public GraphModel createGraphModel(LogModel logModel) {
        return new TankGraphModel(TANK_PATH_TO_PNG, (TankLogModel) logModel);
    }
}
