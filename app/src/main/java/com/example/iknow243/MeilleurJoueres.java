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

import java.util.Objects;

public class MeilleurJoueres extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meilleur_joueres);
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("les meilleurs joueres");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu2,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.regles:
                Intent intent = new Intent(MeilleurJoueres.this, Reglements.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent intent2 = new Intent(MeilleurJoueres.this, AboutUS.class);
                startActivity(intent2);
                return true;
            case R.id.meilleur:
                Intent intent3 = new Intent(MeilleurJoueres.this, MeilleurJoueres.class);
                startActivity(intent3);
                return true;

            case R.id.jouer1:
                Intent intent4 = new Intent(MeilleurJoueres.this, MainActivity.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}