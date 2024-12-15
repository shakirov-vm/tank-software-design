package ru.mipt.bit.platformer.objects.LogicLevel;

import java.util.Random;

public class UtilsRandom {
    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
