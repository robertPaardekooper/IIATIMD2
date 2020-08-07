package com.example.iiatimd;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API extends AppCompatActivity {

    public API(){

    }

    //Met deze functie kan er een object toegevoegd worden aan de api
    //Geef de url en een JSON object mee
    public void apiPOST(String url, JSONObject jsonObject){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d("gelukt", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public String apiGET(String url){

        final String[] naam = {new String()};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("apiGETGelukt", response.toString());
                        try {
                            naam[0] = response.get("naam").toString();
                            Log.d("responseWerkt", naam[0]);
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

        return naam[0];
    }
}
