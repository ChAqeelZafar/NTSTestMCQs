package com.aqeel.johnwick.ntstestmcqs;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView subjectnameText, percentText, attemptText, statusText ;
    Button backBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        subjectnameText = findViewById(R.id.result_text_subjectname);
        percentText = findViewById(R.id.result_text_percentage);
        attemptText = findViewById(R.id.result_text_attempt);
        statusText = findViewById(R.id.result_text_result);
        backBtn = findViewById(R.id.result_btn_back);


        int total = Integer.parseInt(getIntent().getStringExtra("totalQuestions"));
        int correct = Integer.parseInt(getIntent().getStringExtra("correctQuestions"));
        double per = ((correct*100)/total);
        percentText.setText(per + "%");
        attemptText.setText("Attempted : " + correct +" Total : " + total);
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
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }
}
