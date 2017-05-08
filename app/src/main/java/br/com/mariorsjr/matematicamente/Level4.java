package br.com.mariorsjr.matematicamente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Level4 extends AppCompatActivity {

    EditText maximum, minimum, time;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        maximum = (EditText)findViewById(R.id.maximum);
        minimum = (EditText)findViewById(R.id.minimum);
        time = (EditText)findViewById(R.id.time);

        Button playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    Intent intent = new Intent(Level4.this, Level41.class);
                    int max = Integer.parseInt(maximum.getText().toString());
                    int min = Integer.parseInt(minimum.getText().toString());
                    int t = Integer.parseInt(time.getText().toString());
                    intent.putExtra("maximum", max);
                    intent.putExtra("minimum", min);
                    intent.putExtra("time", t);
                    Log.i("time", String.valueOf(t));
                    startActivity(intent);
                }
            }
        });
    }

    public boolean check(){
        String max = maximum.getText().toString();
        String min = minimum.getText().toString();
        String t = time.getText().toString();

        if(max.matches("") || min.matches("") || t.matches("")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillAll), Toast.LENGTH_SHORT).show();
            return false;
        } else if(Integer.parseInt(max) <= 0 || Integer.parseInt(max) > 100){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorMax), Toast.LENGTH_SHORT).show();
            return false;
        }else if(Integer.parseInt(min) < 0 || Integer.parseInt(min) > 100){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorMin), Toast.LENGTH_SHORT).show();
            return false;
        }else if(Integer.parseInt(min) > Integer.parseInt(max)){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.minBigger), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Integer.parseInt(t) < 0){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorTime), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

}
