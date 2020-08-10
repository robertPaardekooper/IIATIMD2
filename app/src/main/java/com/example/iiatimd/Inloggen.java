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

public class Inloggen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inloggen);

        Button inlogButton = findViewById(R.id.loginButton);
        final TextInputEditText inputEmail = findViewById(R.id.inputEmail);
        final EditText inputPassword = findViewById(R.id.inputPassword);

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        inlogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkPassword(inputEmail.getText().toString(), inputPassword.getText().toString());
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

    // Haalt het wachtwoord die bij het e-mailadres hoort op en verifieerd of deze klopt
    // Als deze klopt dan wordt de gebruiker naar de main activity doorgestuurd
    // Anders krijgt de gebruiker een foutmelding
    public void checkPassword(String email, final String password) {
        final AlertDialog.Builder dataIncorrectBuilder = new AlertDialog.Builder(this);
        final AlertDialog.Builder otherErrorBuilder = new AlertDialog.Builder(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://142.93.235.231/api/gebruikers/" + email, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (password.equals(response.get("wachtwoord"))){
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
                Log.d("apiGETGefaald", error.getMessage());
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
