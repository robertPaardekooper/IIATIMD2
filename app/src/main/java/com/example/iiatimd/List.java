package com.example.iiatimd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class List extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton scanBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        GetLoggedInUserEmailTask getLoggedInUserEmailTask = new GetLoggedInUserEmailTask(db);
        Thread thread = new Thread(getLoggedInUserEmailTask);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String email = getLoggedInUserEmailTask.getEmail();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "http://142.93.235.231/api/productsInList/email/" + email,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                ProductInRecycler[] products = new ProductInRecycler[response.length()];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        products[i] = new ProductInRecycler(
                                response.getJSONObject(i).get("name").toString(),
                                response.getJSONObject(i).get("barcode").toString(),
                                response.getJSONObject(i).get("expiration_date").toString(),
                                response.getJSONObject(i).get("note").toString()
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

    @Override
    public void onClick(View v){
        scanCode();
    }

    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning Result");
                builder.setNeutralButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setPositiveButton("Bevestigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(List.this, AddProduct.class);
                        intent.putExtra("SCAN_RESULT", result.getContents());
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}