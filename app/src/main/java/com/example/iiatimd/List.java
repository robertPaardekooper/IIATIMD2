package com.example.iiatimd;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class List extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        //AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://142.93.235.231/api/producten/email/TijsRuigrok15@gmail.com", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //String[] products = new String[response.length()];
                Product[] products = new Product[response.length()];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        //products[i].setNaam(response.getJSONObject(i).get("naam").toString());
                        products[i] = new Product( 0,
                                response.getJSONObject(i).get("naam").toString(),
                                response.getJSONObject(i).get("barcode").toString(),
                                response.getJSONObject(i).get("soort").toString(),
                                response.getJSONObject(i).get("notitie").toString()
                                );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("producten", products.toString());
                recyclerViewAdapter = new ListView(products);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}
