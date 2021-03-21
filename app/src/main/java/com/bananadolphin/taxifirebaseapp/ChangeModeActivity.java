package com.bananadolphin.taxifirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChangeModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
    }

    public void goToPassengerSignIn(View view) {
        startActivity(new Intent(ChangeModeActivity.this, PassеngerSignInActivity.class));
    }

    public void goToDriverSignIn(View view) {
        startActivity(new Intent(ChangeModeActivity.this, DriverSighInActivity.class));
    }
}