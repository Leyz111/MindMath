package com.example.mindmath;

public class LocalPerson extends Person {
    private static LocalPerson instance;
    private LocalPerson() {}

    public static LocalPerson getInstance() {
        if (instance == null) {
            instance = new LocalPerson();
        }
        return instance;
    }

    public void sync(Person serverPerson) {
        this.setName(serverPerson.getName());
        this.setLogin(serverPerson.getLogin());
        this.setPassword(serverPerson.getPassword());
        this.setRole(serverPerson.getRole());
        this.setTopResult(serverPerson.getTopResult());
    }
}