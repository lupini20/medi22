package com.example.iknow243;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  {

    private Button jouer, reglements;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jouer=findViewById(R.id.jouer);
        reglements=findViewById(R.id.regles);

        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Level1.class);
                startActivity(intent);
            }
        });
        reglements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Reglements.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Iknow243");



    }

    public boolean onCreateOptionsMenu(Menu menu) {  // il nous permet de cree un link avec notre manu
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.regles:
                Intent intent = new Intent(MainActivity.this, Reglements.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent intent2 = new Intent(MainActivity.this, AboutUS.class);
                startActivity(intent2);
                return true;
            case R.id.meilleur:
                Intent intent3 = new Intent(MainActivity.this, MeilleurJoueres.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        QuestionControler.destroyInstance();
    }






}