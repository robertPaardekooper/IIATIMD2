package com.example.iiatimd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inloggen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inloggen);

        Button inlogButton = findViewById(R.id.loginButton);

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        //Account verificatie-----------------------------------------------------------------------
//        final ArrayList<String> accountList = new ArrayList<String>();
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://142.93.235.231/api/gebruikers", null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                for (int i=0;i<response.length();i++){
//                    try {
//                        accountList.add(response.getString(i));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                Log.d("apiGETArrayGelukt", response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("apiGETArrayGefaald", error.getMessage());
//            }
//        });
//
//        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
//        Log.d("arrayGelukt", accountList.toString());

        final String[] naam = {new String()};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://142.93.235.231/api/producten/barcode/8711400408540", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("apiGETGelukt", response.toString());
                try {
                    naam[0] = response.get("naam").toString();
                    Log.d("naamResponseGelukt", naam[0]);
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

        Log.d("GELUKT", naam[0]);

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
