package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.DefaultDirectionMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.TreeLogModel;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class Listener {

    private static final String TREE_PATH_TO_PNG = "images/greenTree.png";
    private static final String TANK_PATH_TO_PNG = "images/tank_blue.png";
    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    private static final float MOVEMENT_SPEED = 0.4f;

    private TileMovement tileMovement;
    private TankGraphModel player;
    private Set<TreeGraphModel> trees = new HashSet<>();
    private Set<TankGraphModel> enemies = new HashSet<>();
    private Set<BulletGraphModel> bullets = new HashSet<>();
    private Set<Drawable> drawables = new HashSet<>();

    public Listener(TileMovement tileMovement_) {
        tileMovement = tileMovement_;
    }
    public void addPlayer(TankLogModel player_) {
        player = new TankGraphModel(TANK_PATH_TO_PNG, player_);
        drawables.add(new DrawableHealthDecorator(player));
    }
    public void addTree(TreeLogModel tree_) {
        TreeGraphModel tree = new TreeGraphModel(TREE_PATH_TO_PNG, tree_);
        trees.add(tree);
        drawables.add(tree);
    }
    public void addEnemy(TankLogModel enemy_) {
        TankGraphModel enemy = new TankGraphModel(TANK_PATH_TO_PNG, enemy_);
        enemies.add(enemy);
        drawables.add(new DrawableHealthDecorator(enemy));
    }
    public void addBullet(BulletLogModel bullet_) {
        BulletGraphModel bullet = new BulletGraphModel(BULLET_PATH_TO_PNG, bullet_);
        bullets.add(bullet);
        drawables.add(bullet);
    }

    public TankGraphModel getPlayer() { return player; }
    public Set<TreeGraphModel> getTrees() { return trees; }
    public Set<TankGraphModel> getEnemies() { return enemies; }
    public Set<BulletGraphModel> getBullets() { return bullets; }

    public void moveGraphicPics(float deltaTime) {
        player.movePic(tileMovement);
        player.getTank().movementProgess(deltaTime, MOVEMENT_SPEED);

        for (TankGraphModel tank: enemies) {
            tank.movePic(tileMovement);
            tank.getTank().movementProgess(deltaTime, MOVEMENT_SPEED);
        }

        for (BulletGraphModel bullet: bullets) {
            bullet.movePic(tileMovement);
            bullet.getBullet().movementProgess(deltaTime, MOVEMENT_SPEED);
        }
    }
    public void drawModels(Batch batch) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch);
        }
    }
    public void clearScreen() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}
