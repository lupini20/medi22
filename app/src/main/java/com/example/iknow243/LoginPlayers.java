package com.example.iknow243;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginPlayers extends AppCompatActivity {
    private EditText mail, pass;
    private TextView NeedNouveauCompte;
    private Button login;
    private FirebaseAuth auth;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_players);

        mail = findViewById( R.id.login_email);
        pass = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        NeedNouveauCompte = findViewById(R.id.need_new_account_link);


        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("cree un compte");

        NeedNouveauCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPlayers.this, NouveauComptePlayer.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(mail.getText().toString().isEmpty()){
                     mail.setError("mettez votre mail svp!!!");
                     return;
                 }
                 else {
                     mail.setError(null);
                 }

                 if(pass.getText().toString().isEmpty()){
                     pass.setError("votre mot de passe svp");
                     return;
                 }
                 else {
                     pass.setError(null);
                 }

                       firebaseLogin();
            }
        });
        if (auth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(LoginPlayers.this, LoginPlayers.class);
            startActivity(intent);
            finish();

        }
    }
    private void firebaseLogin() {

        auth.signInWithEmailAndPassword(mail.getText().toString(),pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginPlayers.this, "sucess", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPlayers.this, MeilleurJoueres.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(LoginPlayers.this, "erreur, ressayez encore", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}