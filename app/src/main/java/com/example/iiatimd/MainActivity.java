package com.example.iiatimd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Test room database------------------------------------------------------------------------
//        Product[] producten = new Product[3];
//        producten[0] = new Product(1, "Tompoes", "8711400408543", "Brood en Gebak", "Notitie test");

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

//        new Thread(new InsertProductTask(db, producten[0])).start();
        //new Thread(new GetProductTask(db)).start();


        //Test API----------------------------------------------------------------------------------

        //Hier wordt een nieuw product aangemaakt om aan de api toe te voegen
        Map<String, String> testProduct = new HashMap();
        testProduct.put("naam", "Appel");
        testProduct.put("barcode", "8711400408541");
        testProduct.put("soort", "Groente en Fruit");
        testProduct.put("houdbaarheidsdatum", "2020-08-10");
        testProduct.put("notitie", "Appels zijn ook lekker.");
        //testProduct.put("gebruiker_id", 1);

        JSONObject testProductJson = new JSONObject(testProduct);

        API api = new API();
        //api.apiPOST("http://142.93.235.231/api/productToevoegen", testProductJson);

        //api.apiGET("http://142.93.235.231/api/gebruikers");


        //Barcode scanner---------------------------------------------------------------------------
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

        Button lijstButton = findViewById(R.id.lijstBtn);
        lijstButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openLijst();
            }
        });
    }

    @Override
    public void onClick(View v){
        scanCode();
    }

    public void openLijst(){
        Intent intent = new Intent(this, List .class);
        startActivity(intent);
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
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
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
                        Intent intent = new Intent(MainActivity.this,DatePickerFragment.class);
                        startActivity(intent);
                    }
                });
                AlertDialog diaglog = builder.create();
                diaglog.show();
            }
            else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}