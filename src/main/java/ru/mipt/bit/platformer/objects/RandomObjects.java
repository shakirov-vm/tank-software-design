package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedX;

public class RandomObjects implements MapInitObjects {

    public final int MIN_ENEMIES = 3;
    public final int MAX_ENEMIES = 5;

    Set<GridPoint2> obstacles = new HashSet<GridPoint2>();
    GridPoint2 player;
    Set<GridPoint2> enemies = new HashSet<GridPoint2>();

    private int getRandomNumberUsingNextInt(int min, int max) {

        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public RandomObjects(int tilesWidth, int tilesHeight) {

        int numObjects = getRandomNumberUsingNextInt(0, tilesWidth * tilesHeight - MAX_ENEMIES);

        System.out.println(numObjects);
        Random random = new Random();

        for (int i = 0; i < numObjects; i++) {

            int nextRandom = random.nextInt(tilesWidth * tilesHeight);
            obstacles.add(new GridPoint2(nextRandom % tilesWidth, nextRandom / tilesWidth));
        }

        int numEnemies = getRandomNumberUsingNextInt(MIN_ENEMIES, MAX_ENEMIES);
        for (int i = 0; i < numEnemies; ) {

            int nextRandom = random.nextInt(tilesWidth * tilesHeight);
            if (obstacles.contains(new GridPoint2(nextRandom % tilesWidth, nextRandom / tilesWidth))) {

                System.out.println("Not Enemy: " + (nextRandom % tilesWidth) + " - " + (nextRandom / tilesWidth));
                continue;
            }
            i++;
            System.out.println("Enemy: " + (nextRandom % tilesWidth) + " - " + (nextRandom / tilesWidth));
            enemies.add(new GridPoint2(nextRandom % tilesWidth, nextRandom / tilesWidth));
        }

        do {
            int nextRandom = random.nextInt(tilesWidth * tilesHeight);
            player = new GridPoint2(nextRandom % tilesWidth, nextRandom / tilesWidth);
        } while (obstacles.contains(player) || enemies.contains(player));
    }

    public Set<GridPoint2> getObstacles() {
        return obstacles;
    }
    public GridPoint2 getStartedCoordinates() {
        return player;
    }
    public Set<GridPoint2> getStartedEnemies() {
        return enemies;
    }
}
