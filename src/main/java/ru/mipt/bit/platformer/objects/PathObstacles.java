package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathObstacles implements MapInitObjects {

    Set<GridPoint2> obstacles = new HashSet<GridPoint2>();
    GridPoint2 player;

    public PathObstacles(Path pathToObstacles) throws IOException {

        List<String> lines = Files.readAllLines(pathToObstacles, StandardCharsets.UTF_8);

        int i = lines.size() - 1;
        for (String s: lines) {
            int j = 0;
            for (char c: s.toCharArray()) {
                if (c == 'T') {
                    obstacles.add(new GridPoint2(j, i));
                } else if (c == 'X') {
                    player = new GridPoint2(j, i);
                }
                j++;
            }
            i--;
        }
    }

    public Set<GridPoint2> getObstacles() {
        return obstacles;
    }
    public GridPoint2 getStartedCoordinates() {
        return player;
    }
}
