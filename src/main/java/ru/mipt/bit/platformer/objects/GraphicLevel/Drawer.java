package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphInterfaces.Drawable;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphInterfaces.MovablePic;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.BulletGraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.DrawableHealthDecorator;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.TankGraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.GraphModels.TreeGraphModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TankLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TreeLogModel;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class Drawer {

    private static final String TREE_PATH_TO_PNG = "images/greenTree.png";
    private static final String TANK_PATH_TO_PNG = "images/tank_blue.png";
    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    private static final float MOVEMENT_SPEED = 0.4f;

    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;

    private Set<Drawable> drawables = new HashSet<>();
    private Set<MovablePic> movablePics = new HashSet<>();
    private Set<DrawableHealthDecorator> healthies = new HashSet<>();

    private Map<BulletLogModel, BulletGraphModel> bulletLogToGraph = new HashMap<>();

    public Drawer(TileMovement tileMovement_, TiledMapTileLayer groundLayer_) {
        tileMovement = tileMovement_;
        groundLayer = groundLayer_;
    }
    public void addPlayer(TankLogModel player_) {
        TankGraphModel player = new TankGraphModel(TANK_PATH_TO_PNG, player_);
        DrawableHealthDecorator healthy = new DrawableHealthDecorator(player);

        movablePics.add(player);
        drawables.add(healthy);
        healthies.add(healthy);
    }
    public void addTree(TreeLogModel tree_) {
        TreeGraphModel tree = new TreeGraphModel(TREE_PATH_TO_PNG, tree_, groundLayer);

        drawables.add(tree);
    }
    public void addEnemy(TankLogModel enemy_) {
        TankGraphModel enemy = new TankGraphModel(TANK_PATH_TO_PNG, enemy_);
        DrawableHealthDecorator healthy = new DrawableHealthDecorator(enemy);

        movablePics.add(enemy);
        drawables.add(healthy);
        healthies.add(healthy);
    }
    public void addBullet(BulletLogModel bullet_) {
        BulletGraphModel bullet = new BulletGraphModel(BULLET_PATH_TO_PNG, bullet_);
        bulletLogToGraph.put(bullet_, bullet);

        movablePics.add(bullet);
        drawables.add(bullet);
    }
    public void removeBullet(BulletLogModel bullet_) {
        BulletGraphModel bullet = bulletLogToGraph.get(bullet_);
        bullet.dispose();
        movablePics.remove(bullet);
        drawables.remove(bullet);

        bulletLogToGraph.remove(bullet_);
    }

    public void moveGraphicPics(float deltaTime) {
        for (MovablePic movablePic : movablePics) {
            movablePic.movePic(tileMovement);
            movablePic.changeMovementProgess(deltaTime, MOVEMENT_SPEED);
        }
    }
    public void drawModels(Batch batch) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch);
        }
    }
    public void disposeModels() {
        for (Drawable drawable : drawables) {
            drawable.dispose();
        }
    }
    public void clearScreen() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
    public void handleHealthDrawing() {
        if (Gdx.input.isKeyPressed(L)) {
            for (DrawableHealthDecorator healthy : healthies) {
                healthy.switchDrawHealth();
            }
        }
    }
}
