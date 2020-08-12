package com.example.iiatimd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        final EditText inputUsername = findViewById(R.id.inputUsernameSignUp);
        final EditText inputEmail = findViewById(R.id.inputEmailSignUp);
        final EditText inputPassword = findViewById(R.id.inputPasswordSignUp);
        final EditText inputPasswordVerification = findViewById(R.id.inputPasswordVerificationSignUp);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Button submitButtonRegistration = findViewById(R.id.submitButtonSignUp);

        submitButtonRegistration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                HashMap userMap = new HashMap();
                userMap.put("naam", inputUsername.getText().toString());
                userMap.put("email", inputEmail.getText().toString());
                userMap.put("wachtwoord", inputPassword.getText().toString());

                JSONObject userJson = new JSONObject(userMap);

                // Als de twee wachtwoord velden gelijk zijn wordt het account toegevoegd anders
                // krijgt de gebruiker een foutmelding
                if(inputPassword.getText().toString().equals(inputPasswordVerification.getText().toString())) {
                    API api = new API();
                    api.apiPOST("http://142.93.235.231/api/gebruikerToevoegen", userJson);
                    openMainActivity();
                } else {
                    builder.setTitle("Wachtwoorden komen niet overeen");
                    builder.setPositiveButton("Probeer opnieuw", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity .class);
        startActivity(intent);
    }
}
