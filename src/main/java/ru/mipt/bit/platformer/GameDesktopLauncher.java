package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.*;
import ru.mipt.bit.platformer.util.TileMovement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 1024;

    private static final int TILES_WIDTH = 10;
    private static final int TILES_HEIGHT = 9;

    private static final String TREE_PATH_TO_PNG = "images/greenTree.png";
    private static final String TANK_PATH_TO_PNG = "images/tank_blue.png";

    private static final String MAP_PATH_TO_TMX = "level.tmx";
    private static final String OBSTACLES_PATH_TO_TMX = "src/main/resources/obstacles.txt";

    private Batch batch;
    private TileMovement tileMovement;

    private Map map;
    private Set<Tree> obstacles = new HashSet<Tree>();
    private Tank player;
    private MapInitObjects initObjects;

    private TapHandler keys;

    private enum obstaclesCreateMode {
        RANDOM_OBSTACLES,
        OBSTACLES_FROM_FILE,
    }

    private obstaclesCreateMode obstaclesMode;

    public GameDesktopLauncher() {
        obstaclesMode = obstaclesCreateMode.RANDOM_OBSTACLES;
        initObjects = new RandomObjects(TILES_WIDTH, TILES_HEIGHT);

    }
    public GameDesktopLauncher(Path toObstaclesCoords) throws IOException {
        obstaclesMode = obstaclesCreateMode.OBSTACLES_FROM_FILE;
        initObjects = new PathObstacles(toObstaclesCoords);
    }

    @Override
    public void create() {

        batch = new SpriteBatch();

        map = new Map(batch, MAP_PATH_TO_TMX);
        for(GridPoint2 coord : initObjects.getObstacles()) {
            obstacles.add(new Tree(TREE_PATH_TO_PNG, coord.x, coord.y));
        }
        player = new Tank(TANK_PATH_TO_PNG, initObjects.getStartedCoordinates().x, initObjects.getStartedCoordinates().y);
        tileMovement = map.createTileMovement();

        keys = new TapHandler(player, initObjects.getObstacles());

        for(Tree tree: obstacles) {
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

        keys.handle();

        player.movePic(tileMovement);
        player.movementProgess(deltaTime, MOVEMENT_SPEED);
        map.render();

        batch.begin();

        player.draw(batch);
        for (Tree tree : obstacles) {
            tree.draw(batch);
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
        player.dispose();
        for (Tree tree : obstacles) {
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
