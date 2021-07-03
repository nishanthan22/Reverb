package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {
    Button Homebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Homebtn = findViewById(R.id.homepage);
        Homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(WelcomePage.this,HomePage.class);
                startActivity(homeintent);
                finish();
            }
        });
    }
}