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
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.EnemyShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.PlayerShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitPath;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitRandom;
import ru.mipt.bit.platformer.util.TileMovement;

import java.io.IOException;
import java.nio.file.Path;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.objects.LogicLevel.Direction.randomDirection;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 1024;

    private static final int TILES_WIDTH = 10;
    private static final int TILES_HEIGHT = 9;

    private static final String MAP_PATH_TO_TMX = "level.tmx";
    private static final String OBSTACLES_PATH_TO_TMX = "src/main/resources/obstacles.txt";

    private Batch batch;
    private TileMovement tileMovement;

    private Map map;
    private MapInitObjects initObjects;

    private LogicLevel publisher;
    private Listener listener;

    private TapHandler keys;

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

        listener = new Listener();
        publisher = new LogicLevel(listener);

        batch = new SpriteBatch();

        for (GridPoint2 coord : initObjects.getObstacles()) {
            publisher.addTree(new TreeLogModel(coord.x, coord.y));
        }

        for (GridPoint2 coord : initObjects.getStartedEnemies()) {
            publisher.addEnemy(new TankLogModel(coord.x, coord.y));
        }

        publisher.addPlayer(new TankLogModel(initObjects.getStartedCoordinates().x, initObjects.getStartedCoordinates().y));

        map = new Map(batch, MAP_PATH_TO_TMX);

        tileMovement = map.createTileMovement();

        keys = new TapHandler(listener.getPlayer(), listener.getEnemies(), publisher);

        for (TreeGraphModel tree: listener.getTrees()) {
            tree.rectToCenter(map.getGroundLayer());
        }
    }

    @Override
    public void render() {

        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        // SET ACTIONS TO PLAYER (MOVE AND SHOOT)
        keys.handle();

        // Must be player movement there???
        listener.getPlayer().movePic(tileMovement);
        listener.getPlayer().getTank().movementProgess(deltaTime, MOVEMENT_SPEED);
        for (TankGraphModel tank: listener.getEnemies()) {
            Direction direction = randomDirection();
            if (publisher.tryMoveTank(tank.getTank(), direction)) {
                (new MoveCommand((Movable) tank.getTank(), direction)).execute();
            }
            (new EnemyShootCommand(publisher, tank.getTank())).execute();
            tank.movePic(tileMovement);
            tank.getTank().movementProgess(deltaTime, MOVEMENT_SPEED);
        }
        for (BulletGraphModel bullet: listener.getBullets()) {
            bullet.getBullet().move();
            bullet.movePic(tileMovement);
            bullet.getBullet().movementProgess(deltaTime, MOVEMENT_SPEED);
        }

        map.render();

        batch.begin();

        listener.getPlayer().draw(batch);
        for (TreeGraphModel tree : listener.getTrees()) {
            tree.draw(batch);
        }
        for (TankGraphModel tank : listener.getEnemies()) {
            tank.draw(batch);
        }
        for (BulletGraphModel bullet : listener.getBullets()) {
            bullet.draw(batch);
        }

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
            tree.Dispose();
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
