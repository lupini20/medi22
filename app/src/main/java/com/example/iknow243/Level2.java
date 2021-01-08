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

class Randomizer2 {
    public static int generate(int min,int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}

public class Level2 extends AppCompatActivity implements  QuestionResponseHandler, View.OnClickListener {
    private TextView questionView, qCount, timer, scoreView;
    private Button option1,option3,option4, option2;
    private int score;
    private int numquestion;
    private int currentQuestionIndex = 0;
    ArrayList<QuestionModel> set;
    ArrayList<Integer> askedSet = new ArrayList<>();
    ArrayList<String[]> askedSetString = new ArrayList<>();
    private QuestionControler_Level2 controller1;
    private CountDownTimer countDown;
    Dialog dialog, dialog2;
    TextView scoreSucess, scoreEchec, commentEchec;
    String s;


    private long backPressedTime;
    private Toast backToast;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        controller1 = QuestionControler_Level2.getInstance();
        controller1.readQuestionSet(this);

        questionView = findViewById(R.id.question);
        qCount = findViewById( R.id.questionNum);
        timer = findViewById(R.id.time);
        option1 = findViewById(R.id.options1);
        option2 = findViewById(R.id.options2);
        option3 = findViewById(R.id.options3);
        option4 = findViewById(R.id.options4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        score = 0;
        numquestion=1;
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Niveau-2");

        // dialogue sucess
        dialog = new Dialog(Level2.this);
        dialog.setContentView(R.layout.score_view);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialoguebacground));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button continuer = dialog.findViewById(R.id.suivant);
        Button reconmencer = dialog.findViewById(R.id.recommencer);
        scoreSucess= dialog.findViewById(R.id.succes);

        //dilogu echec
        dialog2 = new Dialog(Level2.this);
        dialog2.setContentView(R.layout.echec_score_view);
        dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialoguebacground));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);
        dialog2.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button recommencer2 = dialog2.findViewById(R.id.bandela);
        Button correction = dialog2.findViewById(R.id.correction);


        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level2.this,Demi_finale.class);
                startActivity(intent);
            }
        });
        reconmencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        recommencer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        correction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level2.this,MainActivity.class);
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

    public void setQuestionView(){
        int randSet = Randomizer2.generate(0,(set.size() - 1));
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
        int randomIndex = Randomizer2.generate(1,6);
        s= questionModel.suggestion.get(randomIndex);
        if (questionModel.response.trim().equals(s))
            correctRand = true;
        this.option1.setText(s);
        chosenRand.add(randomIndex);

        //option2
        randomIndex = Randomizer2.generate(1,6);
        while(chosenRand.contains(randomIndex)) {
            randomIndex = Randomizer.generate(1,6);
        }

        s=questionModel.suggestion.get(randomIndex);
        if(questionModel.response.trim().equals(s))
            correctRand=true;
        this.option2.setText(s);
        chosenRand.add(randomIndex);

        //option3
        randomIndex = Randomizer2.generate(1,6);
        while(chosenRand.contains(randomIndex)) {
            randomIndex = Randomizer.generate(1,6);
        }
        s=questionModel.suggestion.get(randomIndex);
        if(questionModel.response.trim().equals(s))
            correctRand=true;
        this.option3.setText(s);
        chosenRand.add(randomIndex);

        //option4
        randomIndex = Randomizer2.generate(1,6);
        while(chosenRand.contains(randomIndex)) {
            randomIndex = Randomizer.generate(1,6);
        }
        if (!correctRand) {
            s = questionModel.response;
        } else {
            s=questionModel.suggestion.get(randomIndex);
        }
        this.option4.setText(s);
        chosenRand.add(randomIndex);


        // set question asked
        this.questionView.setText("Q/" +questionModel.prompt);
        StarTimer();
        qCount.setText(String.valueOf(1) + "/3");




}

   public void StarTimer(){
       countDown = new CountDownTimer(12000, 1000) {
           @Override
           public void onTick(long millisUntilFinished) {
               if(millisUntilFinished < 10000)
                   timer.setText(String.valueOf(millisUntilFinished / 500));
           }

           @Override
           public void onFinish() {
               ChangeQuestion();
           }
       };
       countDown.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

            case R.id.options4:
                selectedOption = (String) option4.getText();
                break;


            default:
        }
        countDown.cancel();
        checkAnswer(selectedOption, view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(String selectedOption, View view) {

        if (selectedOption.equals(set.get(currentQuestionIndex).response)) {
            //Right Answer
            score++;
            Toast.makeText(this, "bonne reponse ", Toast.LENGTH_SHORT).show();

        } else {
            //Wrong Answer
            Toast.makeText(this, "mauvaise reponse  ", Toast.LENGTH_SHORT).show();
        }
        ChangeQuestion();
    }

    public void ChangeQuestion(){

        if ( numquestion<=2) {
            numquestion++;
            setQuestionView();
            qCount.setText(numquestion +"/3");


        } else {

            if (score > 1) {
                dialog.show();
                scoreSucess.setText("votre score est:"   + score + "/15");

            } else {

                dialog2.show();
                scoreEchec.setText("votre Score est:"   + score + "/15");

            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        QuestionControler_Demi_finale.destroyInstance();
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