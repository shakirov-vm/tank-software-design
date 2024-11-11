package ru.mipt.bit.platformer.objects.GraphicLevel;

import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.TreeLogModel;

import java.util.HashSet;
import java.util.Set;

public class Listener {

    private static final String TREE_PATH_TO_PNG = "images/greenTree.png";
    private static final String TANK_PATH_TO_PNG = "images/tank_blue.png";

    private TankGraphModel player;
    private Set<TreeGraphModel> trees = new HashSet<>();
    private Set<TankGraphModel> enemies = new HashSet<>();

    public Listener() {}
    public void addPlayer(TankLogModel player_) {
        player = new TankGraphModel(TANK_PATH_TO_PNG, player_);
    }
    public void addTree(TreeLogModel tree) {
        trees.add(new TreeGraphModel(TREE_PATH_TO_PNG, tree));
    }
    public void addEnemy(TankLogModel enemy) {
        enemies.add(new TankGraphModel(TANK_PATH_TO_PNG, enemy));
    }

    public TankGraphModel getPlayer() { return player; }
    public Set<TreeGraphModel> getTrees() { return trees; }
    public Set<TankGraphModel> getEnemies() { return enemies; }

}
