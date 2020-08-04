package com.example.iiatimd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Aanmelden extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aanmelden);


        Button signUpButton = findViewById(R.id.buttonSignUp);
        final TextInputEditText inputUsername = findViewById(R.id.inputUsername);
        final EditText inputPassword = findViewById(R.id.inputPassword);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Map<String, String> userMap = new HashMap();
                userMap.put("name", inputUsername.getText().toString());
                userMap.put("email", "test@test.nl");
                userMap.put("password", inputPassword.getText().toString());

                JSONObject userJson = new JSONObject(userMap);

                API api = new API();
                //api.apiPOST("http://142.93.235.231/api/gebruikerToevoegen", userJson);




                openMainActivity();
            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity .class);
        startActivity(intent);
    }
}
