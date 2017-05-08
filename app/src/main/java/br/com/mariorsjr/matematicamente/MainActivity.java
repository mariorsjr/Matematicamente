//icon Created using Freepik

package br.com.mariorsjr.matematicamente;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    private AdView mAdView;

    final int MINIMUM_SCORE_LEVEL_1 = 15;
    final int MINIMUM_SCORE_LEVEL_2 = 20;
    final int MINIMUM_SCORE_LEVEL_3 = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Button level1Button = (Button) findViewById(R.id.level1Button);
        level1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Level1.class);
                startActivity(i);
            }
        });

        Button level2Button = (Button) findViewById(R.id.level2Button);
        level2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkHighScore(getString(R.string.saved_high_score_level1));

            }
        });

        Button level3Button = (Button) findViewById(R.id.level3Button);
        level3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHighScore(getString(R.string.saved_high_score_level2));
            }
        });

        Button level4Button = (Button) findViewById(R.id.level4Button);
        level4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHighScore(getString(R.string.saved_high_score_level3));
            }
        });


    }

    public void checkHighScore(String level){

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        if(level.equals(getString(R.string.saved_high_score_level1))){
            int highScore = sharedPreferences.getInt(getString(R.string.saved_high_score_level1), 0);
            if(highScore >= MINIMUM_SCORE_LEVEL_1){
                Intent i = new Intent(MainActivity.this, Level2.class);
                startActivity(i);
            }else{
                showDialog("Level2");
            }

        }

        if(level.equals(getString(R.string.saved_high_score_level2))){
            int highScore = sharedPreferences.getInt(getString(R.string.saved_high_score_level2), 0);
            if(highScore >= MINIMUM_SCORE_LEVEL_2){
                Intent i = new Intent(MainActivity.this, Level3.class);
                startActivity(i);
            }else{
                showDialog("Level3");
            }
        }

        if(level.equals(getString(R.string.saved_high_score_level3))){
            int highScore = sharedPreferences.getInt(getString(R.string.saved_high_score_level3), 0);
            if(highScore >= MINIMUM_SCORE_LEVEL_3){
                Intent i = new Intent(MainActivity.this, Level4.class);
                startActivity(i);
            }else{
                showDialog("Level4");
            }
        }

    }

    public void showDialog(String level) {

        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(MainActivity.this)
                .setTextTitle("Espere um pouco...")
                .setPositiveButtonText("OK")
                .setTitleColor(R.color.colorAccent)
                .setBodyColor(android.R.color.black)
                .setPositiveColor(R.color.colorAccent)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                }).build();

        if (level.equals("Level2")) {

            alert.setBody("Você precisa de pelo menos " + MINIMUM_SCORE_LEVEL_1 + " pontos" +
                    " no Nível 1 para jogar o Nível 2!");


        } else if (level.equals("Level3")) {

            alert.setBody("Você precisa de pelo menos " + MINIMUM_SCORE_LEVEL_2 + " pontos" +
                    " no Nível 2 para jogar o Nível 3!");

        } else if (level.equals("Level4")) {

            alert.setBody("Você precisa de pelo menos " + MINIMUM_SCORE_LEVEL_3 + " pontos" +
                    " no Nível 3 para jogar o Nível 4!");
        }

        alert.build();
        alert.show();

    }
}
