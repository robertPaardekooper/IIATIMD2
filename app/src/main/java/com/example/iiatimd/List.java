package com.example.iiatimd;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        String[] villagers = new String[200]; //0..199

        for (int i = 0; i < 200; i++) {
            villagers[i] = "Villager " + i;
        }

        recyclerViewAdapter = new ListView(villagers);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
