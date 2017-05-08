package br.com.mariorsjr.matematicamente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

public class Level1 extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    private AdView mAdView;

    int time=60, maximum=20, minimum=0;
    String[] operators = {"+", "-"};
    //string level;

    Button button0,button1,button2,button3, playAgainButton;
    TextView resultTextView, scoreTextView, sumTextView, secondsTextView;

    int numberOfAnswers = 4;

    ArrayList<Integer> answers = new ArrayList<>(numberOfAnswers);
    int locationOfCorrectAnswer;
    int score = 0;

    public void playAgain(View view){
        score = 0;
        secondsTextView.setText(time+"s");
        scoreTextView.setText("0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);

        generateQuestion();

        new CountDownTimer(time*1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                secondsTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                secondsTextView.setText("0s");
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);

                if(score > getHighScore())
                    saveHighScore();

                resultTextView.setText(getResources().getString(R.string.finalScore)+ " " +score + "\n"
                        + getString(R.string.your_high_score) + " " + getHighScore());

            }
        }.start();
    }

    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            resultTextView.setText(getResources().getString(R.string.correct));
            score++;
        }
        else{
            resultTextView.setText(getResources().getString(R.string.wrong));
        }
        scoreTextView.setText(String.valueOf(score));
        generateQuestion();
    }

    public void generateQuestion(){
        Random random = new Random();
        int n1 = random.nextInt(maximum - minimum) + minimum;
        int n2 = random.nextInt(maximum - minimum) + minimum;
        String operator = operators[random.nextInt(operators.length)];

        sumTextView.setText(n1 + " " + operator + " " + n2);
        int correctAnswer;
        locationOfCorrectAnswer = random.nextInt(numberOfAnswers-1);
        try {
            correctAnswer = operation(operator, n1, n2);
        } catch (Exception e) {
            sumTextView.setText(n1 + " " + "+" + " " + n2);
            correctAnswer = n1+n2;
        }
        int wrongAnswer;
        answers.clear();

        int randomMax, randomMin;

        if(correctAnswer>0) {
            randomMax = correctAnswer * 4;
            randomMin = -correctAnswer * 4;
        }else {
            randomMax = -correctAnswer * 4;
            randomMin = correctAnswer * 4;
        }

        for(int i=0; i < numberOfAnswers; i++){
            if(i == locationOfCorrectAnswer)
                answers.add(correctAnswer);
            else{
                do{
                    try{
                        wrongAnswer = random.nextInt(randomMax - randomMin) + randomMin;
                    }catch (Exception e){
                        wrongAnswer = random.nextInt(maximum);
                    }
                } while(wrongAnswer == correctAnswer);
                answers.add(wrongAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public int operation(String operator, int a, int b) throws Exception {
        if(operator.equals("+"))
            return a + b;
        else if (operator.equals("-"))
            return a - b;
        else if (operator.equals("*"))
            return a * b;
        else if (operator.equals("/")){
            if(a % b == 0){
                return a/b;
            }
            else
                throw new Exception("Not integer");
        }
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        sumTextView = (TextView) findViewById(R.id.sumTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        secondsTextView = (TextView) findViewById(R.id.secondsTextView);


        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(v);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(v);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(v);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(v);
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain(null);
            }
        });

        playAgain(null);
    }

    public void saveHighScore(){

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.saved_high_score_level1), score);
        editor.commit();

    }

    public int getHighScore(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(getString(R.string.saved_high_score_level1), 0);
    }
}
