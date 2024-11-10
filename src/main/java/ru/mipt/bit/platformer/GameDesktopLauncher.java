package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.GraphicLevel.Map;
import ru.mipt.bit.platformer.objects.GraphicLevel.TankGraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.TapHandler;
import ru.mipt.bit.platformer.objects.GraphicLevel.TreeGraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.*;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitPath;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitRandom;
import ru.mipt.bit.platformer.util.TileMovement;

import java.io.IOException;
import java.nio.file.Path;
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
    private Set<TreeGraphModel> trees = new HashSet<TreeGraphModel>();
    private TankGraphModel player;
    private Set<TankGraphModel> enemies = new HashSet<TankGraphModel>();
    private Set<Obstacle> obstacles = new HashSet<Obstacle>();
    private MapInitObjects initObjects;

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

        batch = new SpriteBatch();

        map = new Map(batch, MAP_PATH_TO_TMX);
        for(GridPoint2 coord : initObjects.getObstacles()) {
            TreeLogModel treeModel = new TreeLogModel(coord.x, coord.y);
            TreeGraphModel tree = new TreeGraphModel(TREE_PATH_TO_PNG, treeModel);
            trees.add(tree);
            obstacles.add(treeModel);
        }

        for(GridPoint2 coord : initObjects.getStartedEnemies()) {
            TankLogModel enemyModel = new TankLogModel(coord.x, coord.y);
            TankGraphModel enemy = new TankGraphModel(TANK_PATH_TO_PNG, enemyModel);
            enemies.add(enemy);
            obstacles.add(enemyModel);
            enemyModel.setMoveCommand(new EnemyMoveCommand(enemyModel));
        }
        TankLogModel playerModel = new TankLogModel(initObjects.getStartedCoordinates().x, initObjects.getStartedCoordinates().y);
        player = new TankGraphModel(TANK_PATH_TO_PNG, playerModel);
        obstacles.add(playerModel);
        playerModel.setMoveCommand(new PlayerMoveCommand(playerModel));

        for (TankGraphModel enemy : enemies) {
            ((EnemyMoveCommand) enemy.getTank().getMoveCommand()).setObstacles(obstacles);
        }
        ((PlayerMoveCommand) player.getTank().getMoveCommand()).setObstacles(obstacles);

        tileMovement = map.createTileMovement();

        keys = new TapHandler(player, enemies);

        for (TreeGraphModel tree: trees) {
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

        // set actions to player
        keys.handle();

        player.getTank().move();
        player.movePic(tileMovement);
        player.getTank().movementProgess(deltaTime, MOVEMENT_SPEED);
        for (TankGraphModel tank: enemies) {
            tank.getTank().move();
            tank.movePic(tileMovement);
            tank.getTank().movementProgess(deltaTime, MOVEMENT_SPEED);
        }

        map.render();

        batch.begin();

        player.draw(batch);
        for (TreeGraphModel tree : trees) {
            tree.draw(batch);
        }
        for (TankGraphModel tank : enemies) {
            tank.draw(batch);
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
        for (TreeGraphModel tree : trees) {
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
