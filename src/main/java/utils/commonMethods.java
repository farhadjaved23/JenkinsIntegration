package utils;

import java.util.Random;

public class commonMethods {

    public static int randomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
