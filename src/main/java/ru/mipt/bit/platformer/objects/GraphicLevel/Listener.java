package ru.mipt.bit.platformer.objects.GraphicLevel;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Set;

public class Listener {

    private TankGraphModel player;
    private Set<TreeGraphModel> trees;
    private Set<TankGraphModel> enemies;

    public Listener(TankGraphModel player_, Set<TreeGraphModel> trees_, Set<TankGraphModel> enemies_) {

        player = player_;
        trees = trees_;
        enemies = enemies_;
    }
    public TankGraphModel getPlayer() { return player; }
    public Set<TreeGraphModel> getTrees() { return trees; }
    public Set<TankGraphModel> getEnemies() { return enemies; }

}
