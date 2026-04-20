package utils;

import java.util.Random;

public class rand {

    private static Random random = new Random();

    public static int num(int bounds) {
        return random.nextInt(bounds);
    }

}
