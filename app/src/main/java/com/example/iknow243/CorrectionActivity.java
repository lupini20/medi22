package com.example.iknow243;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class CorrectionActivity extends AppCompatActivity {
    private RecyclerView cat_reycler_view;
    private Button addCatB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);


        cat_reycler_view = findViewById(R.id.catGridview);

        List<String> catList = new ArrayList<>();
        List<String> score_str = getIntent().getStringArrayListExtra("SCORE");



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


    }
}