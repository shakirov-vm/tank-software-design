package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.objects.Config;
import ru.mipt.bit.platformer.objects.GraphicLevel.*;
import ru.mipt.bit.platformer.objects.GraphicLevel.Utils.Map;
import ru.mipt.bit.platformer.objects.LogicLevel.*;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.SwitchHealthCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Generators.*;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitPath;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitRandom;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.HealthBarTapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.MoveTapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.ShootTapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.TapHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class GameDesktopLauncher implements ApplicationListener {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 1024;

    public static final int TILES_WIDTH = 10;
    public static final int TILES_HEIGHT = 9;

    private static final String OBSTACLES_PATH_TO_TMX = "src/main/resources/obstacles.txt";

    private MapInitObjects initObjects;

    private LogicLevel level;
    private Drawer drawer;

    private CommandsHandler cmdHandler;

    public enum obstaclesCreateMode {
        RANDOM_OBSTACLES,
        OBSTACLES_FROM_FILE,
    }

    private obstaclesCreateMode obstaclesMode;

    public GameDesktopLauncher() {
        obstaclesMode = obstaclesCreateMode.RANDOM_OBSTACLES;
    }
    public GameDesktopLauncher(Path toObstaclesCoords) throws IOException {
        obstaclesMode = obstaclesCreateMode.OBSTACLES_FROM_FILE;
        initObjects = (MapInitObjects) new MapInitPath(toObstaclesCoords);
    }

    @Override
    public void create() {

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        level = context.getBean(LogicLevel.class);

        // Unfortunately, gdx.drawer crashes with an exception, so due to the dependency
        // on drawer, it is impossible to move the initialization of these classes to Spring
        drawer = new Drawer(new SpriteBatch());
        level.subscribe(drawer);
        level.initialize(context.getBean(MapInitObjects.class));

        Set<TapHandler> handlers = new HashSet<>();
        handlers.add(new MoveTapHandler(level));
        handlers.add(new ShootTapHandler(level));
        handlers.add(new HealthBarTapHandler(drawer));

        Set<CommandGenerator> generators = new HashSet<>();
        generators.add(new TapCommandGenerator(handlers));
        generators.add(new AICommandsGenerator(level));
        generators.add(new BulletCommandsGenerator(level));

        cmdHandler = new CommandsHandler(generators);
    }

    @Override
    public void render() {
        drawer.clearScreen();

        Set<Command> cmds = cmdHandler.generateCommands();
        cmdHandler.executeCommands(cmds);

        level.update(Gdx.graphics.getDeltaTime());
        drawer.modelsMoveDraw();
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
