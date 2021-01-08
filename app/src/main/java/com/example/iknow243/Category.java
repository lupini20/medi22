package com.example.iknow243;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {
    private GridView catGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        catGrid = findViewById(R.id.catGridview);

        List<String> catList = new ArrayList<>();
        catList.add("histoire");
        catList.add("geographie");
        catList.add("cinema");
        catList.add("politique");
        catList.add("les dates");
        catList.add("sport");


        categori_adaptor adapter = new categori_adaptor(catList);
        catGrid.setAdapter(adapter);


    }
}