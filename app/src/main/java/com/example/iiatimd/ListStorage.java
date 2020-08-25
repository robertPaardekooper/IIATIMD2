package com.example.iiatimd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
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

public class ListStorage extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton scanBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_storage);

        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://142.93.235.231/api/producten/email/TijsRuigrok15@gmail.com", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ProductInRecycler[] products = new ProductInRecycler[response.length()];

                String[] dateStringList = new String[response.length()];
                Date[] customDateList = new Date[response.length()];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        dateStringList[i] = response.getJSONObject(i).get("houdbaarheidsdatum").toString();
                        customDateList[i] = new SimpleDateFormat("yyyy-MM-dd").parse(dateStringList[i]);

                        products[i] = new ProductInRecycler(
                                response.getJSONObject(i).get("naam").toString(),
                                response.getJSONObject(i).get("barcode").toString(),
                                response.getJSONObject(i).get("soort").toString(),
                                customDateList[i],
                                response.getJSONObject(i).get("notitie").toString()
                                );
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("producten", products.toString());
                recyclerViewAdapter = new ListViewStorage(products);
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
                        Intent intent = new Intent(ListStorage.this, AddProduct.class);
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