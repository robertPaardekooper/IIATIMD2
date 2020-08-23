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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

//        Date currentTime = Calendar.getInstance().getTime();
        Log.d("hoi2", dateFormat.format(date));

        final EditText inputEmail = findViewById(R.id.inputEmailSignIn);
        final EditText inputPassword = findViewById(R.id.inputPasswordSignIn);
        Button submitButtonSignIn = findViewById(R.id.submitButtonSignIn);
        Button openSignUpButton = findViewById(R.id.openSignUpButton);

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        submitButtonSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkPassword(inputEmail.getText().toString(), inputPassword.getText().toString());
            }
        });

        openSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSignUp();
            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, ListStorage .class);
        startActivity(intent);
    }

    public void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    // Haalt het wachtwoord die bij het e-mailadres hoort op en verifieërd of deze klopt
    // Als deze klopt dan wordt de gebruiker naar de main activity doorgestuurd
    // Anders krijgt de gebruiker een foutmelding
    public void checkPassword(final String email, final String password) {

        final AlertDialog.Builder dataIncorrectBuilder = new AlertDialog.Builder(this);
        final AlertDialog.Builder otherErrorBuilder = new AlertDialog.Builder(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://142.93.235.231/api/gebruikers/" + email, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (password.equals(response.get("wachtwoord"))){
                        // Gebruiker wordt voor de zekerheid uitgelogd
                        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                        new Thread(new LogOutUserTask(db)).start();
                        // Vervolgens wordt de gebruiker ingelogd
                        new Thread(new LogInUserTask(db, email)).start();
                        openMainActivity();
                    } else if(!password.equals(response.get("wachtwoord"))){
                        dataIncorrectBuilder.setTitle("E-mailadres of wachtwoord incorrect");
                        dataIncorrectBuilder.setPositiveButton("Probeer opnieuw", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        });
                        AlertDialog dialog = dataIncorrectBuilder.create();
                        dialog.show();
                    }
                    // Werkt momenteel niet, moet in gang gaan als gegevens niet kunnen worden opgehaald
                    else {
                        otherErrorBuilder.setTitle("Er is iets misgegaan");
                        otherErrorBuilder.setPositiveButton("Probeer opnieuw", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { }
                        });
                        AlertDialog dialog = otherErrorBuilder.create();
                        dialog.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
