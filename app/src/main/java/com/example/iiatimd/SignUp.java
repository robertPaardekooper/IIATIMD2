package com.example.iiatimd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.HashMap;

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

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        final AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        submitButtonRegistration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String username = inputUsername.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                HashMap userMap = new HashMap();
                userMap.put("name", username);
                userMap.put("email", email);
                userMap.put("password", password);

                JSONObject userJson = new JSONObject(userMap);

                // TODO: betere beveiliging maken, gebruiker niet doorsturen als account al bestaat
                // Als de twee wachtwoord velden gelijk zijn wordt het account toegevoegd anders
                // krijgt de gebruiker een foutmelding
                if(inputPassword.getText().toString().equals(
                        inputPasswordVerification.getText().toString())) {

                    API api = new API();
                    api.apiPOST("http://142.93.235.231/api/addUser", userJson);

                    // Nieuwe gebruiker wordt aan de room database toegevoegd
                    User newUser = new User(0, username, email, password, true);
                    InsertUserTask insertUserTask = new InsertUserTask(db, newUser);
                    Thread thread = new Thread(insertUserTask);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    openMainActivity();
                }

                else {
                    builder.setTitle("Wachtwoorden komen niet overeen");
                    builder.setPositiveButton("Probeer opnieuw",
                            new DialogInterface.OnClickListener() {

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
        Intent intent = new Intent(this, List.class);
        startActivity(intent);
    }
}
