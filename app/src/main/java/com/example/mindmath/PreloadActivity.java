package com.example.mindmath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mindmath.authorization.AuthorizationActivity;
import com.example.mindmath.person.LocalPerson;
import com.example.mindmath.person.Person;
import com.example.mindmath.repository.PersonRepository;
import com.example.mindmath.repository.RepositoryCallback;

public class PreloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_preload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LocalPerson.getInstance().loadFromSharedPreferences(this);

        PersonRepository personRepository = new PersonRepository();

        if (LocalPerson.getInstance().getName().equals("null") || LocalPerson.getInstance().getPassword().equals("null")) {
            goToActivity(AuthorizationActivity.class);
        } else {
            personRepository.login(LocalPerson.getInstance().getName(), LocalPerson.getInstance().getPassword(), new RepositoryCallback<Person>() {
                @Override
                public void onSuccess(Person result) {
                    goToActivity(MainActivity.class);
                }

                @Override
                public void onFail(String error) {
                    if (error.equals("wrong_credentials")) {
                        goToActivity(AuthorizationActivity.class);
                    } else {
                        ConnectionStatus.getInstance().setStatus("ERROR");
                        goToActivity(MainActivity.class);
                        Toast.makeText(PreloadActivity.this, "Сервер недоступен", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void goToActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(PreloadActivity.this, activityClass);
        startActivity(intent);
        PreloadActivity.this.finish();
    }
}