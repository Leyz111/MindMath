package com.example.mindmath.person;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalPerson extends Person {
    private static LocalPerson instance;
    private LocalPerson() {}

    private static final String SP_PREF_NAME = "local_person_sp";
    private static final String SP_NAME = "name";
    private static final String SP_LOGIN = "login";
    private static final String SP_PASSWORD = "password";
    private static final String SP_ROLE = "role";
    private static final String SP_TOP_RESULT = "top_result";

    private static final String SP_ID = "id";

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
        this.setId(serverPerson.getId());
    }

    public void loadFromSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SP_PREF_NAME, Context.MODE_PRIVATE);

        setName(preferences.getString(SP_NAME, "Гость"));
        setLogin(preferences.getString(SP_LOGIN, "null"));
        setPassword(preferences.getString(SP_PASSWORD, "null"));
        setRole(preferences.getString(SP_ROLE, "null"));
        setTopResult(preferences.getString(SP_TOP_RESULT, "0"));
        setId(preferences.getLong(SP_ID, 0));
    }

    public void saveToShaSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SP_NAME, getName());
        editor.putString(SP_LOGIN, getLogin());
        editor.putString(SP_PASSWORD, getPassword());
        editor.putString(SP_ROLE, getRole());
        editor.putString(SP_TOP_RESULT, getTopResult());
        editor.putLong(SP_ID, getId());

        editor.apply();
    }
}