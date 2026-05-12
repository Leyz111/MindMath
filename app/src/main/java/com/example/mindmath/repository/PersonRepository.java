package com.example.mindmath.repository;

import com.example.mindmath.person.Person;
import com.example.mindmath.api.ApiService;
import com.example.mindmath.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonRepository {
    private ApiService apiService;

    public PersonRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public void createPerson(Person person, RepositoryCallback<Person> callback) {
        apiService.createPerson(person).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable throwable) {
                callback.onFail(throwable.getMessage());
            }
        });
    }

    public void updatePerson(Person person, RepositoryCallback<Person> callback) {
        apiService.updatePerson(person.getId(), person).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable throwable) {
                callback.onFail(throwable.getMessage());
            }
        });
    }

    public void getAllPersons(RepositoryCallback<List<Person>> callback) {
        apiService.getAllPerson().enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable throwable) {
                callback.onFail(throwable.getMessage());
            }
        });
    }

    public void login(String login, String password, RepositoryCallback<Person> callback) {
        Person loginData = new Person();
        loginData.setLogin(login);
        loginData.setPassword(password);

        apiService.login(loginData).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else if (response.code() == 401) {
                    callback.onFail("wrong_credentials");
                } else {
                    callback.onFail(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }
}
