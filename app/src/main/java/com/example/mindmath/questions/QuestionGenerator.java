package com.example.mindmath.questions;

import java.util.Random;

public class QuestionGenerator {
    private static final Random random = new Random();

    public static Question generate() {
        int max = 20;
        int a = random.nextInt(max) + 1;
        int b = random.nextInt(max) + 1;

        int operation = random.nextInt(6);

        String text;
        int result;

        switch (operation) {
            case 0:
                text = a + " + " + b + " = ?";
                result = a + b;
                break;
            case 1:
                if (a < b) { int temp = a; a = b; b = temp; }
                text = a + " - " + b + " = ?";
                result = a - b;
                break;
            case 2:
                a = random.nextInt(20) + 1;
                b = random.nextInt(10) + 1;
                text = a + " * " + b + " = ?";
                result = a * b;
                break;
            case 3:
                int num = random.nextInt(10);
                text = num + "² = ?";
                result = num * num;
                break;
            case 4:
                int x = random.nextInt(15) + 1;
                int bValue = random.nextInt(15) + 1;
                int typeEquation = random.nextInt(2);

                if (typeEquation == 0) {
                    int cValue = x + bValue;
                    text = "x + " + bValue + " = " + cValue;
                } else {
                    int cValue = x - bValue;
                    text = "x - " + bValue + " = " + cValue;
                }

                result = x;
                break;
            default:
                int quotient = random.nextInt(10) + 2;
                int divisor = random.nextInt(9) + 2;
                int dividend = quotient * divisor;
                text = dividend + " / " + divisor + " = ?";
                result = quotient;
                break;
        }

        return new Question(text, String.valueOf(result));
    }
}
