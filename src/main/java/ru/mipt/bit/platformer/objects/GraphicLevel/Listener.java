package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.DefaultDirectionMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.TreeLogModel;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashSet;
import java.util.Set;

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

    public Listener(TileMovement tileMovement_) {
        tileMovement = tileMovement_;
    }
    public void addPlayer(TankLogModel player_) {
        player = new TankGraphModel(TANK_PATH_TO_PNG, player_);
    }
    public void addTree(TreeLogModel tree) {
        trees.add(new TreeGraphModel(TREE_PATH_TO_PNG, tree));
    }
    public void addEnemy(TankLogModel enemy) {
        enemies.add(new TankGraphModel(TANK_PATH_TO_PNG, enemy));
    }
    public void addBullet(BulletLogModel bullet) {
        bullets.add(new BulletGraphModel(BULLET_PATH_TO_PNG, bullet));
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
        // use drawable
        player.draw(batch);
        for (TreeGraphModel tree : trees) {
            tree.draw(batch);
        }
        for (TankGraphModel tank : enemies) {
            tank.draw(batch);
        }
        for (BulletGraphModel bullet : bullets) {
            bullet.draw(batch);
        }
    }
}
