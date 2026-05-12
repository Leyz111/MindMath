package com.example.mindmath;
public class ConnectionStatus {
    private static ConnectionStatus instance;
    private String status = "OK";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ConnectionStatus getInstance() {
        if (instance == null) {
            instance = new ConnectionStatus();
        }
        return instance;
    }
}
