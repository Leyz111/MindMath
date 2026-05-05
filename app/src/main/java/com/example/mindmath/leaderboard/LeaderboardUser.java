package com.example.mindmath.leaderboard;

public class LeaderboardUser {
    private final int rank;
    private final String name;

    public LeaderboardUser(int rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
}