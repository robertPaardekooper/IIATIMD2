package com.example.iiatimd;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        Product[] products = new Product[5];
        products[0] = new Product(2, "Melk", "894323429", "Zuivel", "niks");
        products[1] = new Product(3, "Kwark", "894323429", "Zuivel", "niks");
        products[2] = new Product(4, "Eieren", "894323429", "Zuivel", "niks");
        products[3] = new Product(5, "Karnemelk", "894323429", "Zuivel", "niks");
        products[4] = new Product(6, "Mayo", "894323429", "Zuivel", "niks");

        //Test room database
//        Product[] producten = new Product[3];
//        producten[0] = new Product(1, "Tompoes", "8711400408543", "Brood en Gebak", "Notitie test");

//        new Thread(new InsertProductTask(db, producten[0])).start();
        //new Thread(new GetProductTask(db)).start();

        //test API
        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://142.93.235.231/api/producten/barcode/8711400408540", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("gelukt", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.getMessage());
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        mAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(mAdapter);
//
        //final String URL = "http://127.0.0.1:8000/api/producten";

//        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
//
//        new Thread(new InsertProductTask(db, products[2])).start();
        // new Thread(new GetProductTask(db)).start();
    }
}
