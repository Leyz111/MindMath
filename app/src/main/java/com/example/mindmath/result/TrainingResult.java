package com.example.mindmath.result;

import java.util.List;

public class TrainingResult extends Result {
    private int totalQuestions;
    private int correctAnswers;
    private List<Mistake> mistakes;

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<Mistake> getMistakes() {
        return mistakes;
    }

    public void setMistakes(List<Mistake> mistakes) {
        this.mistakes = mistakes;
    }

}
