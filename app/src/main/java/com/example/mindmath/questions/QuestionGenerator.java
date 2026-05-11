package com.example.mindmath.questions;

import java.util.Random;

public class QuestionGenerator {
    private static final Random random = new Random();

    public static Question generate() {
        int max = 20;
        int a = random.nextInt(max) + 1;
        int b = random.nextInt(max) + 1;

        int operation = random.nextInt(3);

        String text;
        int result;

        switch (operation) {
            case 0:
                text = a + " + " + b;
                result = a + b;
                break;
            case 1:
                if (a < b) { int temp = a; a = b; b = temp; }
                text = a + " - " + b;
                result = a - b;
                break;
            default:
                a = random.nextInt(20) + 1;
                b = random.nextInt(10) + 1;
                text = a + " * " + b;
                result = a * b;
                break;
        }

        return new Question(text, String.valueOf(result));
    }
}
