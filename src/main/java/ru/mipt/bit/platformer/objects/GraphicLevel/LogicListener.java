package ru.mipt.bit.platformer.objects.GraphicLevel;

import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;

public interface LogicListener {
    void addModel(LogModel model);
    void removeModel(LogModel model);
}
