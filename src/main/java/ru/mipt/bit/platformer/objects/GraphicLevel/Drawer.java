package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator.BulletGraphGenerator;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator.GeneratorMapper;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator.TankGraphGenerator;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphGenerator.TreeGraphGenerator;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.*;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.LogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TankLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TreeLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class Drawer implements LogicListener {

    private static final String MAP_PATH_TO_TMX = "level.tmx";

    private ru.mipt.bit.platformer.objects.GraphicLevel.Utils.Map map;
    private TileMovement tileMovement;
    private Batch batch;

    private Set<GraphModel> models = new HashSet<>();

    private Map<BulletLogModel, BulletGraphModel> bulletLogToGraph = new HashMap<>();
    private GeneratorMapper mapper;

    public Drawer(Batch batch_) {
        batch = batch_;
        map = new ru.mipt.bit.platformer.objects.GraphicLevel.Utils.Map(batch, MAP_PATH_TO_TMX);
        tileMovement = map.createTileMovement();

        mapper = new GeneratorMapper();
        mapper.addGenerator(TankLogModel.class, new TankGraphGenerator());
        mapper.addGenerator(TreeLogModel.class, new TreeGraphGenerator(map.getGroundLayer()));
        mapper.addGenerator(BulletLogModel.class, new BulletGraphGenerator());
    }

    public void addModel(LogModel logModel) {
        GraphModel model = mapper.createGraphModel(logModel);
        models.add(new GraphModelHealthDecorator(model));
    }
    public void removeModel(LogModel logModel) {
        for (GraphModel model : models) {
            if (model.getObject().equals(logModel)) {
                model.dispose();
                models.remove(model);
                break;
            }
        }
    }

    public void modelsMoveDraw() {
        handleHealthDrawing();
        map.render();

        batch.begin();
        for (GraphModel model : models) {
            model.movePic(tileMovement);
            model.draw(batch);
        }
        batch.end();
    }
    public void disposeModels() {
        for (GraphModel model : models) {
            model.dispose();
        }
    }
    public void clearScreen() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
    private void handleHealthDrawing() {
        if (Gdx.input.isKeyPressed(L)) {
            for (GraphModel model : models) {
                ((GraphModelHealthDecorator) model).switchDrawHealth();
            }
        }
    }
}
