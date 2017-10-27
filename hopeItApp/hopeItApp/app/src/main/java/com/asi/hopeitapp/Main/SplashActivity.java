package com.asi.hopeitapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.asi.hopeitapp.Network.NetworkManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkManager.getInstance().checkForUpdate(getApplicationContext());

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }
}
