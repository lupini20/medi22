package com.example.iknow243;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

class Randomizer1 {
    public static int generate(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
public class Demi_finale extends AppCompatActivity   implements  QuestionResponseHandler{
    private EditText reponse;
    private Button validez;
    private TextView questionView,qCount,timer;
    private int score;
    private int currentQuestionIndex = 0;
    ArrayList<QuestionModel> set;
    ArrayList<Integer> askedSet = new ArrayList<>();
    ArrayList<String[]> askedSetString = new ArrayList<>();
    private QuestionControler_Demi_finale controller1;
    private CountDownTimer countDown;
    String s;
    Dialog dialog, dialog2;
    TextView scoreSucess, scoreEchec, commentEchec, CommentSucces;
    private int nombreFaute, Numquestion;

    private long backPressedTime;
    private Toast backToast;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demi_finale);
        reponse = findViewById(R.id.options1);
        validez = findViewById(R.id.valider);
        questionView = findViewById(R.id.question);
        qCount = findViewById( R.id.questionNum);
        timer = findViewById(R.id.time);
        controller1 = QuestionControler_Demi_finale.getInstance();
        controller1.readQuestionSet(this);

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Demi-fnale");

        // dialogue sucess
        dialog = new Dialog(Demi_finale.this);
        dialog.setContentView(R.layout.score_view);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialoguebacground));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button continuer = dialog.findViewById(R.id.suivant);
        Button recommencer = dialog.findViewById(R.id.recommencer);
        scoreSucess = dialog.findViewById(R.id.succes);
        CommentSucces = dialog.findViewById(R.id.comment1);



        //dilogu echec
        dialog2 = new Dialog(Demi_finale.this);
        dialog2.setContentView(R.layout.echec_score_view);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialoguebacground));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);
        dialog2.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button recommencer2 = dialog2.findViewById(R.id.bandela);
        Button correction = dialog2.findViewById(R.id.correction);
        scoreEchec = dialog2.findViewById(R.id.echec);
        commentEchec = dialog2.findViewById(R.id.comment);


        score = 0;
        nombreFaute = 0;
        Numquestion = 1;


        validez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Demi_finale.this, Finale.class);
                startActivity(intent);

            }
        });

        recommencer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Demi_finale.this, MainActivity.class);
                startActivity(intent);

            }
        });

        recommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Demi_finale.this, MainActivity.class);
                startActivity(intent);

            }
        });


        correction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Demi_finale.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }



    @Override
    public void onQuestionSubmitted(boolean isSuccess, @Nullable QuestionModel modelValue) {

    }

    @Override
    public void onFirstQuestionReceived() {
        ArrayList<QuestionModel> set = controller1.questionSet;
        this.set = set;

        if (!set.isEmpty()) setQuestionView();

    }

    private void setQuestionView() {
        int randSet = Randomizer1.generate(0,(set.size() - 1));
        while(askedSet.contains(randSet)) {
            randSet = Randomizer.generate(0,(set.size() - 1));
        }
        askedSet.add(randSet);
        currentQuestionIndex = randSet;


        final QuestionModel questionModel = set.get(randSet);
        String response = questionModel.response;
        String question = questionModel.prompt;
        askedSetString.add(new String[]{question , response});

        ArrayList<Integer> chosenRand = new ArrayList<>();

        // set question asked
        this.questionView.setText("Q/" +questionModel.prompt);
         StarTimer();
        qCount.setText(String.valueOf(1) + "/3");


    }

    private void StarTimer() {
        countDown = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 10000)
                    timer.setText(String.valueOf(millisUntilFinished / 200));
            }

            @Override
            public void onFinish() {
                ChangeQuestion();
            }
        };
        countDown.start();
    }


    private void checkAnswer() {
        String reponsecheck = reponse.getText().toString();
        if(reponsecheck.equalsIgnoreCase(set.get(currentQuestionIndex).response)){
            score++;
            Toast.makeText(this, "bonne reponse ", Toast.LENGTH_SHORT).show();
        }
        else {
            nombreFaute++;
            Toast.makeText(this, "mauvaise reponse ", Toast.LENGTH_SHORT).show();
            if(nombreFaute>=2){
                dialog2.show();
                scoreEchec.setText("votre Score est:" + score + "/" + Numquestion);
                commentEchec.setText("mawa tu verra pas la finale, 3 fautes egale a echec total, okokemba yaya");
            }

        }
        reponse.setText("");
         ChangeQuestion();
    }

    private void ChangeQuestion() {
        if (Numquestion <4) {
            Numquestion++;
            setQuestionView();
            qCount.setText(Numquestion +"4");


        }
        else {

            if (score >= 2) {
                dialog.show();
                scoreSucess.setText("votre score est:" + score + "/5" );
                CommentSucces.setText("felicitation, vous etes en finale, mais vous devez savoir qu'une seule faute est synomyne de l'echec total!!!");

            } else {

                dialog2.show();
                scoreEchec.setText("votre Score est:" + score + "/4" );
                CommentSucces.setText("fayika fayika, oko kemba pona kobeta finale eza blague te, bandela kaka");


            }


        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        QuestionControler_Finale.destroyInstance();
    }





    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();

        }
        backPressedTime = System.currentTimeMillis();
    }
}