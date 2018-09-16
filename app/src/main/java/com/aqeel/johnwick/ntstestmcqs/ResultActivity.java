package com.aqeel.johnwick.ntstestmcqs;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ResultActivity extends AppCompatActivity {
    TextView subjectnameText, percentText, attemptText, statusText ;
    Button backBtn ;

    AdView adViewUp, adViewDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        loadAd();
        subjectnameText = findViewById(R.id.result_text_subjectname);
        percentText = findViewById(R.id.result_text_percentage);
        attemptText = findViewById(R.id.result_text_attempt);
        statusText = findViewById(R.id.result_text_result);
        backBtn = findViewById(R.id.result_btn_back);


        int total = Integer.parseInt(getIntent().getStringExtra("totalQuestions"));
        int correct = Integer.parseInt(getIntent().getStringExtra("correctQuestions"));
        double per = ((correct*100)/total);
        percentText.setText(per + "%");
        attemptText.setText("Correct : " + correct +" Total : " + total);
        if(per<51){
            subjectnameText.setText("Try Again!");
            statusText.setText("Fail");
        }else{
            subjectnameText.setText("Congratulation");
            statusText.setText("Pass");
        }




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ResultActivity.this, SubjectsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(ResultActivity.this, SubjectsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mShare:

                shareCall();

                break;

            case R.id.mInfo:
                infoCall();
                break;


        }

        return super.onOptionsItemSelected(item);

    }


    void infoCall(){
        Intent intent = new Intent(ResultActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    void shareCall(){
        Intent  i = new Intent(

                android.content.Intent.ACTION_SEND);

        i.setType("text/plain");

        i.putExtra(

                android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.aqeel.johnwick.ntstestmcqs");

        startActivity(Intent.createChooser(

                i,

                "Share Using"));
    }

    void loadAd(){

        MobileAds.initialize(this, getString(R.string.ad_app_id));
        adViewUp = findViewById(R.id.result_up_adView);
        adViewDown = findViewById(R.id.result_down_adView);
        AdRequest requestUp = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest requestDown = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();


        adViewUp.loadAd(requestUp);
        adViewDown.loadAd(requestDown);
    }
}
