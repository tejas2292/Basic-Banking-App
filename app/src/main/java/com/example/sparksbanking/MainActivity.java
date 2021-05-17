package com.example.sparksbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewUsers(View view) {
        startActivity(new Intent(MainActivity.this, AllUsersActivity1.class));
        finish();
    }

    public void onViewTransaction(View view) {
    }
}