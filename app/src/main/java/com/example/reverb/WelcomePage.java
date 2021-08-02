package com.example.reverb;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;




public class WelcomePage extends AppCompatActivity {
    Button Homebtn;
   static EditText username;
   static String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        username =findViewById(R.id.NameUser);
        //s = username.getText().toString();
        Homebtn = findViewById(R.id.homepage);

        Homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdata();

            }
        });
//        Prevbtn = findViewById(R.id.preview);
//        Prevbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(WelcomePage.this, R.style.BottomSheetDialogTheme);
//                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
//                        R.layout.bottom_slider,
//                        (LinearLayout)findViewById(R.id.bottom_sheet_container)
//                );
//                bottomSheetDialog.setContentView(bottomSheetView);
//                bottomSheetDialog.show();
//            }
//
//        });

    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void checkdata() {
        boolean isValid = true;
        String user_nm = username.getText().toString();
        if (isEmpty(username)) {
            username.setError("Name is required");
            isValid = false;
        }
        else
        {
            isValid = true;
             name = username.getText().toString();

            Intent homeintent = new Intent(WelcomePage.this,HomePage.class);
            //homeintent.putExtra("Username",name);
            startActivity(homeintent);
            finish();
        }




    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences=getSharedPreferences(name, Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(name,false))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(name,true);
            editor.apply();
        } else {
            Intent i = new Intent(this,HomePage.class);
            startActivity(i);
            finish();
        }
    }
}