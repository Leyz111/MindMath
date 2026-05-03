package com.example.mindmath.api;

import com.example.mindmath.Person;
import com.example.mindmath.result.NormalResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/results")
    Call<Void> sendResult(@Body NormalResult result);

    @POST("/api/persons")
    Call<Person> createPerson(@Body Person person);

    @GET("/api/person")
    Call<List<Person>> getAllPerson();
}
