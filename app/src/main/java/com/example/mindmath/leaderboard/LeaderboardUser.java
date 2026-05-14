package com.example.mindmath.leaderboard;

public class LeaderboardUser {
    private final int rank;
    private final String name;
    private final int score;

    public LeaderboardUser(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
}