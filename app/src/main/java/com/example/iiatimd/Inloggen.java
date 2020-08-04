package com.example.iiatimd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Inloggen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inloggen);

        Button inlogButton = findViewById(R.id.loginButton);
        inlogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMainActivity();
            }
        });

        Button aanmeldButton = findViewById(R.id.aanmeldButton);
        aanmeldButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAanmelden();
            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity .class);
        startActivity(intent);
    }

    public void openAanmelden(){
        Intent intent = new Intent(this, Aanmelden .class);
        startActivity(intent);
    }
}
