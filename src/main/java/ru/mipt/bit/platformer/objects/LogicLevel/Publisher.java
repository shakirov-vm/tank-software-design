package ru.mipt.bit.platformer.objects.LogicLevel;

import ru.mipt.bit.platformer.objects.GraphicLevel.TankGraphModel;
import ru.mipt.bit.platformer.objects.GraphicLevel.TreeGraphModel;

import java.util.HashSet;
import java.util.Set;

public class Publisher {

    private TankLogModel player;
    private Set<TreeLogModel> trees;
    private Set<TankLogModel> enemies;
    private Set<BulletLogModel> bullets = new HashSet<>();

    private static final String BULLET_PATH_TO_PNG = "images/bullet.png";

    public Publisher (TankLogModel player_, Set<TreeLogModel> trees_, Set<TankLogModel> enemies_) {
        player = player_;
        trees = trees_;
        enemies = enemies_;
    }

}
