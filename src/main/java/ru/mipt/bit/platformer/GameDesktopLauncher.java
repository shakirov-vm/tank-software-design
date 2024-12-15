package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.GraphicLevel.*;
import ru.mipt.bit.platformer.objects.LogicLevel.*;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitPath;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitRandom;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class GameDesktopLauncher implements ApplicationListener {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 1024;

    private static final int TILES_WIDTH = 10;
    private static final int TILES_HEIGHT = 9;

    private static final String MAP_PATH_TO_TMX = "level.tmx";
    private static final String OBSTACLES_PATH_TO_TMX = "src/main/resources/obstacles.txt";

    private Batch batch;

    private Map map;
    private MapInitObjects initObjects;

    private LogicLevel level;
    private Drawer drawer;

    private CommandsHandler cmdHandler;

    private enum obstaclesCreateMode {
        RANDOM_OBSTACLES,
        OBSTACLES_FROM_FILE,
    }

    private obstaclesCreateMode obstaclesMode;

    public GameDesktopLauncher() {
        obstaclesMode = obstaclesCreateMode.RANDOM_OBSTACLES;
        initObjects = (MapInitObjects) new MapInitRandom(TILES_WIDTH, TILES_HEIGHT);
    }
    public GameDesktopLauncher(Path toObstaclesCoords) throws IOException {
        obstaclesMode = obstaclesCreateMode.OBSTACLES_FROM_FILE;
        initObjects = (MapInitObjects) new MapInitPath(toObstaclesCoords);
    }

    @Override
    public void create() {

        batch = new SpriteBatch();
        map = new Map(batch, MAP_PATH_TO_TMX);

        drawer = new Drawer(map.createTileMovement(), map.getGroundLayer());
        level = new LogicLevel(drawer, initObjects);

        cmdHandler = new CommandsHandler(level);
    }

    @Override
    public void render() {
        drawer.clearScreen();

        Set<Command> cmds = cmdHandler.generateCommands();
        cmdHandler.executeCommands(cmds);

        drawer.handleHealthDrawing();
        // get time passed since the last render
        drawer.moveGraphicPics(Gdx.graphics.getDeltaTime());
        map.render();

        batch.begin();
        drawer.drawModels(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        drawer.disposeModels();
    }

    public static void main(String[] args) throws IOException {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
//        new Lwjgl3Application(new GameDesktopLauncher(Paths.get(OBSTACLES_PATH_TO_TMX)), config);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
