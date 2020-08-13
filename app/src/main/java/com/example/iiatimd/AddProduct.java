package com.example.iiatimd;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.HashMap;

public class AddProduct extends Activity implements AdapterView.OnItemSelectedListener {

    String category = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        final EditText inputProductName = findViewById(R.id.inputProductName);
        final EditText inputDate = findViewById(R.id.inputDate);
        final EditText inputNote = findViewById(R.id.inputNote);
        final Button addProductButton = findViewById(R.id.addProductButton);

        // Spinner (dropdown menu) voor de verschillende soorten
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setOnItemSelectedListener(this);
        // Adapter voor spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_category_array, android.R.layout.simple_spinner_item);
        // Styling van de spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Adapter aan spinner toevoegen
        categorySpinner.setAdapter(adapter);

        addProductButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String name = inputProductName.getText().toString();
                String barcode = getIntent().getStringExtra("SCAN_RESULT");
                String date = inputDate.getText().toString();
                String note = inputNote.getText().toString();

                HashMap productMap = new HashMap();
                productMap.put("naam", name);
                productMap.put("barcode", barcode);
                productMap.put("soort", category);
                productMap.put("houdbaarheidsdatum", date);
                productMap.put("notitie", note);
                //productMap.put("gebruiker_id", 1);

                JSONObject productJson = new JSONObject(productMap);

                // Niew product wordt toegevoegd aan laravel API
                API api = new API();
                api.apiPOST("http://142.93.235.231/api/productToevoegen", productJson);

                // Nieuw product wordt toegevoegd aan room database
                Product newProduct = new Product(0, name, barcode, category, note);
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                new Thread(new InsertProductTask(db, newProduct)).start();
                //new Thread(new GetProductTask(db)).start();
            }
        });

    }
    public void openList(){
        Intent intent = new Intent(this, List .class);
        startActivity(intent);
    }

    // Als een van de soorten geselecteerd wordt, dan wordt de waarde aangepast
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}