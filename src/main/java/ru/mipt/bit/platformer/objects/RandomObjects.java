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

    Set<GridPoint2> obstacles = new HashSet<GridPoint2>();
    GridPoint2 player;

    private int getRandomNumberUsingNextInt(int min, int max) {

        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public RandomObjects(int tilesWidth, int tilesHeight) {

        int numObjects = getRandomNumberUsingNextInt(0, tilesWidth * tilesHeight);

        System.out.println(numObjects);
        Random random = new Random();

        for (int i = 0; i < numObjects; i++) {

            int nextRandom = random.nextInt(tilesWidth * tilesHeight);
            obstacles.add(new GridPoint2(nextRandom % tilesWidth, nextRandom / tilesWidth));
        }

        do {
            int nextRandom = random.nextInt(tilesWidth * tilesHeight);
            player = new GridPoint2(nextRandom % tilesWidth, nextRandom / tilesWidth);
        } while (obstacles.contains(player));
    }

    public Set<GridPoint2> getObstacles() {
        return obstacles;
    }
    public GridPoint2 getStartedCoordinates() {
        return player;
    }
}
