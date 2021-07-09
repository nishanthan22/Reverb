package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;




public class WelcomePage extends AppCompatActivity {
    Button Homebtn,Prevbtn;
    EditText username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        username =findViewById(R.id.NameUser);
        Homebtn = findViewById(R.id.homepage);
        Prevbtn = findViewById(R.id.preview);
        Prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(WelcomePage.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.bottom_slider,
                        (LinearLayout)findViewById(R.id.bottom_sheet_container)
                );
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }

        });
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