package com.example.mindmath.tasks;

import java.util.ArrayList;

public class QuestionQueue {
    private ArrayList<Question> mainQueue = new ArrayList<>();
    private ArrayList<Question> skippedQueue = new ArrayList<>();
    private Question currentQuestion;

    private int questionNumber = 0;

    public void nextQuestion() {
        if (questionNumber + 1 < mainQueue.size()) {
            questionNumber++;
            currentQuestion = mainQueue.get(questionNumber);
        } else {
            switchToSkippedQueue();
        }
    }

    public void skipCurrentQuestion() {
        skippedQueue.add(currentQuestion);
        nextQuestion();
    }

    public void switchToSkippedQueue() {
        if (!skippedQueue.isEmpty()) {
            mainQueue = new ArrayList<>(skippedQueue);
            skippedQueue.clear();
            questionNumber = 0;
            currentQuestion = mainQueue.get(0);
        } else {
            currentQuestion = null;
        }
    }

    public QuestionQueue(ArrayList<Question> questions) {
        this.mainQueue = questions;
        this.currentQuestion = mainQueue.get(0);
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public ArrayList<Question> getMainQueue() {
        return mainQueue;
    }

    public ArrayList<Question> getSkippedQueue() {
        return skippedQueue;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }
}
