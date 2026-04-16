package com.example.mindmath.tasks;

public class Question {
    private String equation, answer;

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    private int attempts = 3;

    public Question(String equation, String answer) {
        this.equation = equation;
        this.answer = answer;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
