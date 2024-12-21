package ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator;

import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.GraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;

import java.util.HashMap;
import java.util.Map;

public class GeneratorMapper {

    private Map<Class<? extends LogModel>, GraphModelGenerator> logClassGeneratorMap = new HashMap<>();

    public void addGenerator(Class<? extends LogModel> logModelClass, GraphModelGenerator graphGenerator) {
        logClassGeneratorMap.put(logModelClass, graphGenerator);
    }

    public GraphModel createGraphModel(LogModel logModel) {
        GraphModelGenerator generator = logClassGeneratorMap.get(logModel.getClass());
        if (generator != null) {
            return generator.createGraphModel(logModel);
        }
        throw new RuntimeException("No class to create new graph model");
    }
}
