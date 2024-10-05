package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.*;
import ru.mipt.bit.platformer.objects.Direction.To;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedY;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 1024;

    private static final String TREE_PATH_TO_PNG = "images/greenTree.png";
    private static final String TANK_PATH_TO_PNG = "images/tank_blue.png";

    private static final String MAP_PATH_TO_TMX = "level.tmx";

    private static final int TREE_START_X_COORD = 4;
    private static final int TREE_START_Y_COORD = 3;

    private static final int TANK_START_X_COORD = 5;
    private static final int TANK_START_Y_COORD = 2;

    private Batch batch;
    private TileMovement tileMovement;

    private Map map;
    private Tree singleTree;
    private Tank player;

    private TapHandler keys;

    @Override
    public void create() {

        batch = new SpriteBatch();

        map = new Map(batch, MAP_PATH_TO_TMX);
        singleTree = new Tree(TREE_PATH_TO_PNG, TREE_START_X_COORD, TREE_START_Y_COORD);
        player = new Tank(TANK_PATH_TO_PNG, TANK_START_X_COORD, TANK_START_Y_COORD);
        tileMovement = map.createTileMovement();

        keys = new TapHandler(player, singleTree);

        singleTree.rectToCenter(map.getGroundLayer());
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
        singleTree.draw(batch);

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
        singleTree.Dispose();
        map.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
