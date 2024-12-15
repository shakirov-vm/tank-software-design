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
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.DefaultDirectionMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.ShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Generators.AICommandsGenerator;
import ru.mipt.bit.platformer.objects.LogicLevel.Generators.TapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitPath;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitRandom;
import ru.mipt.bit.platformer.util.TileMovement;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.objects.LogicLevel.Direction.randomDirection;
import static ru.mipt.bit.platformer.objects.LogicLevel.Generators.StaticCommandsGenerator.generateStaticCommands;

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
    private Listener listener;

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

        listener = new Listener(map.createTileMovement());
        level = new LogicLevel(listener);
        for (GridPoint2 coord : initObjects.getObstacles()) {
            level.addTree(new TreeLogModel(coord.x, coord.y));
        }

        for (GridPoint2 coord : initObjects.getStartedEnemies()) {
            level.addEnemy(new TankLogModel(coord.x, coord.y));
        }
        level.addPlayer(new TankLogModel(initObjects.getStartedCoordinates().x, initObjects.getStartedCoordinates().y));
        cmdHandler = new CommandsHandler(level);

        for (TreeGraphModel tree: listener.getTrees()) {
            tree.rectToCenter(map.getGroundLayer());
        }
    }

    @Override
    public void render() {

        listener.clearScreen();

        Set<Command> cmds = cmdHandler.generateCommands();
        cmdHandler.executeCommands(cmds);

        // get time passed since the last render
        listener.moveGraphicPics(Gdx.graphics.getDeltaTime());
        map.render();

        batch.begin();
        listener.drawModels(batch);
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
        listener.getPlayer().dispose();
        for (TreeGraphModel tree : listener.getTrees()) {
            tree.dispose();
        }
        map.dispose();
        batch.dispose();
    }

    public static void main(String[] args) throws IOException {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
//        new Lwjgl3Application(new GameDesktopLauncher(Paths.get(OBSTACLES_PATH_TO_TMX)), config);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
