package com.example.iiatimd;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddProduct extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        final String barcode = getIntent().getStringExtra("SCAN_RESULT");

        final TextView addProductName = findViewById(R.id.addProductName);
        final TextView addProductBarcode = findViewById(R.id.addProductBarcode);
        addProductBarcode.setText(barcode);

        final EditText inputDate = findViewById(R.id.inputDate);
        final EditText inputNote = findViewById(R.id.inputNote);
        final Button addProductButton = findViewById(R.id.addProductButton);

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://142.93.235.231/api/products/barcode/" + barcode, null,
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    addProductName.setText(response.get("name").toString());
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

        addProductButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

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

                String date = inputDate.getText().toString();
                String note = inputNote.getText().toString();

                HashMap productMap = new HashMap();
                productMap.put("product_barcode", barcode);
                productMap.put("android_user_email", email);
                productMap.put("expiration_date", date);
                productMap.put("note", note);

                JSONObject productJson = new JSONObject(productMap);

                // Niew product wordt toegevoegd aan laravel API
                API api = new API();
                api.apiPOST("http://142.93.235.231/api/addProductInList", productJson);

                openList();
            }
        });

    }
    public void openList(){
        Intent intent = new Intent(this, List.class);
        startActivity(intent);
    }
}