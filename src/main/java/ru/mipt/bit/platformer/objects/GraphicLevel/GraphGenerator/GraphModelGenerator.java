package ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator;

import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.GraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;

public interface GraphModelGenerator {
    GraphModel createGraphModel(LogModel logModel);
}
