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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

class Randomizer3 {
    public static int generate(int min,int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
public class Finale extends AppCompatActivity implements QuestionResponseHandler, View.OnClickListener {
    private TextView questionView, qCount, timer, scoreView;
    private Button option1,option3,option4, option2;
    private int score;
    private int currentQuestionIndex = 0;
    ArrayList<QuestionModel> set;
    ArrayList<Integer> askedSet = new ArrayList<>();
    ArrayList<String[]> askedSetString = new ArrayList<>();
    private QuestionControler_Finale controller1;
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
        setContentView(R.layout.activity_finale);

        controller1 = QuestionControler_Finale.getInstance();
        controller1.readQuestionSet(this);
        qCount = findViewById( R.id.questionNum);
        questionView = findViewById(R.id.question);
        timer = findViewById(R.id.time);
        option1 = findViewById(R.id.options1);
        option2 = findViewById(R.id.options2);
        option3 = findViewById(R.id.options3);


        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);


        score = 0;
        Numquestion = 1;
        nombreFaute = 0;

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Niveau-1");

        // dialogue sucess
        dialog = new Dialog(Finale.this);
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
        dialog2 = new Dialog(Finale.this);
        dialog2.setContentView(R.layout.echec_score_view);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialoguebacground));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);
        dialog2.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button recommencer2 = dialog2.findViewById(R.id.bandela);
        Button correction = dialog2.findViewById(R.id.correction);
        scoreEchec = dialog2.findViewById(R.id.echec);



        continuer.isEnabled();

        recommencer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Finale.this, MainActivity.class);
                startActivity(intent);

            }
        });

        recommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Finale.this, MainActivity.class);
                startActivity(intent);

            }
        });


        correction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Finale.this,MainActivity.class);
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
        int randSet = Randomizer3.generate(0,(set.size() - 1));
        while(askedSet.contains(randSet)) {
            randSet = Randomizer.generate(0,(set.size() - 1));
        }
        askedSet.add(randSet);
        currentQuestionIndex = randSet;


        final QuestionModel questionModel = set.get(randSet);
        String response = questionModel.response;
        String question = questionModel.prompt;
        askedSetString.add(new String[]{question , response});

        boolean correctRand = false;
        ArrayList<Integer> chosenRand = new ArrayList<>();

        //option1
        int randomIndex = Randomizer3.generate(1,5);
        s= questionModel.suggestion.get(randomIndex);
        if (questionModel.response.trim().equals(s))
            correctRand = true;
        this.option1.setText(s);
        chosenRand.add(randomIndex);

        //option2
        randomIndex = Randomizer3.generate(1,5);
        while(chosenRand.contains(randomIndex)) {
            randomIndex = Randomizer.generate(1,5);
        }

        s=questionModel.suggestion.get(randomIndex);
        if(questionModel.response.trim().equals(s))
            correctRand=true;
        this.option2.setText(s);
        chosenRand.add(randomIndex);

        //option3
        randomIndex = Randomizer3.generate(1,5);
        while(chosenRand.contains(randomIndex)) {
            randomIndex = Randomizer.generate(1,5);
        }
        if (!correctRand) {
            s = questionModel.response;
        } else {
            s=questionModel.suggestion.get(randomIndex);
        }
        this.option3.setText(s);
        chosenRand.add(randomIndex);


        // set question asked
        this.questionView.setText("Q/" +questionModel.prompt);
        StarTimer();
        qCount.setText(String.valueOf(1) + "/10");
    }

    private void StarTimer() {
        countDown = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 10000)
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                ChangeQuestion();
            }
        };
        countDown.start();
    }

    @Override
    public void onClick(View view) {
        String selectedOption = "";

        switch (view.getId())
        {
            case R.id.options1 :
                selectedOption= (String) option1.getText();
                break;

            case R.id.options2:
                selectedOption = (String) option2.getText();
                break;

            case R.id.options3:
                selectedOption = (String) option3.getText();
                break;




            default:
        }
        countDown.cancel();
        checkAnswer(selectedOption, view);

    }

    private void checkAnswer(String selectedOption, View view) {
        if (selectedOption.equals(set.get(currentQuestionIndex).response)) {
            //Right Answer
            Toast.makeText(this, "bonne reponse ", Toast.LENGTH_SHORT).show();
            score++;

        } else {
            //Wrong Answer
            Toast.makeText(this, "mauvaise reponse  ", Toast.LENGTH_SHORT).show();
            countDown.cancel();
            nombreFaute++;
            if(nombreFaute>=2){
                dialog2.show();
                scoreEchec.setText("votre Score est:" + score + "/" + Numquestion);
                commentEchec.setText("mawa coupe ekeyi hooo, 3 fautes egale a echec total, okokemba yaya, jeu oyo eza pasi pour gagner, bandela kk niveau-1");
            }


        }
        ChangeQuestion();
    }

    private void ChangeQuestion() {
        if (Numquestion < 4) {
            Numquestion++;
            setQuestionView();
            qCount.setText(Numquestion +"/20");


        } else {
            if (score >= 2) {
                dialog.show();
                scoreSucess.setText("votre Score est :" + score + "/20");
                CommentSucces.setText("bravos, enfin obeti yango show, donc tu connais la RDC, felicition , kamata bisous yaya");



            } else {

                dialog2.show();
                scoreEchec.setText("votre Score est:"   + score + "/20");
                commentEchec.setText("fayika fayika, oko kemba pona kolonga  finale eza blague te, bandela kaka");

            }

            }


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
