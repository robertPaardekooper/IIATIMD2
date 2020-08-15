package com.example.iiatimd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        final AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        //new Thread(new GetUserTask(db)).start();

        submitButtonRegistration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String username = inputUsername.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                HashMap userMap = new HashMap();
                userMap.put("naam", username);
                userMap.put("email", email);
                userMap.put("wachtwoord", password);

                JSONObject userJson = new JSONObject(userMap);

                // Als de twee wachtwoord velden gelijk zijn wordt het account toegevoegd anders
                // krijgt de gebruiker een foutmelding
                if(inputPassword.getText().toString().equals(inputPasswordVerification.getText().toString())) {
                    API api = new API();
                    api.apiPOST("http://142.93.235.231/api/gebruikerToevoegen", userJson);


                    // De gebruiker wordt eerst voor de zekerheid uitgelogd
                    // Zo is er altijd maar een iemand ingelogd
                    new Thread(new LogOutUserTask(db)).start();
                    // Nieuwe gebruiker wordt aan de room database toegevoegd
                    User newUser = new User(0, username, email, password, true);
                    new Thread(new InsertUserTask(db, newUser)).start();

                    openMainActivity();
                }

                else {
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
