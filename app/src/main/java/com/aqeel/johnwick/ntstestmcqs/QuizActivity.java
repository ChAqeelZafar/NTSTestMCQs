package com.aqeel.johnwick.ntstestmcqs;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aqeel.johnwick.ntstestmcqs.Models.Question;
import com.aqeel.johnwick.ntstestmcqs.Models.Subject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nullable;

import static android.graphics.Color.parseColor;

public class QuizActivity extends AppCompatActivity {

    Spinner spinner;
    int totalQuestions = 0 ;
    int liveQuestion = 0 ;
    TextView statementText, totalText, correctText ;

    RadioGroup radioGroup;
    RadioButton o1Radio, o2Radio, o3Radio, o4Radio;
    String subjectName, forceName ;

    Button nextBtn, skipBtn, checkBtn ;


    int correctScore = 0 ;
    //FirebaseFirestore firestore;


    ArrayList<Question>  questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

//        subjectName = getIntent().getStringExtra("subjectName");
//        forceName = getIntent().getStringExtra("forceName");
//        spinner = findViewById(R.id.quiz_spinner_totalQuestions);
        statementText = findViewById(R.id.quiz_questionStatement);
        totalText = findViewById(R.id.quiz_text_total);
        radioGroup = findViewById(R.id.quiz_radiogroup);
        nextBtn = findViewById(R.id.quiz_btn_next);
        skipBtn = findViewById(R.id.quiz_btn_skip);
        checkBtn = findViewById(R.id.quiz_btn_check);
        correctText = findViewById(R.id.quiz_text_correct);

        o1Radio = findViewById(R.id.quiz_radio_o1);
        o2Radio = findViewById(R.id.quiz_radio_o2);
        o3Radio = findViewById(R.id.quiz_radio_o3);
        o4Radio = findViewById(R.id.quiz_radio_o4);

        //firestore = FirebaseFirestore.getInstance();

        loadFirstQuestion();

//        Integer[] items = new Integer[]{20, 30, 40, 50};
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, items);



//        spinner.setAdapter(adapter);

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                totalQuestions = Integer.parseInt(adapterView.getSelectedItem().toString());
//                totalText.setText(liveQuestion + "/" + totalQuestions);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtnClicked();
            }
        });


        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipBtnClicked();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBtn.getText().toString().equals("SHOW")){
                    correctText.setVisibility(View.VISIBLE);
                    checkBtn.setText("HIDE");
                }
                else{
                    correctText.setVisibility(View.GONE);
                    checkBtn.setText("SHOW");
                }

            }
        });

    }

    void startQuiz(int liveQuestion){
        //Toast.makeText(Quiz.this, questionList.size() + "size" , Toast.LENGTH_SHORT).show();
        totalText.setText(liveQuestion + 1 + "/" + totalQuestions);
        Question question = questionList.get(liveQuestion);
        statementText.setText(question.getStatement());
        correctText.setVisibility(View.GONE);
        checkBtn.setText("SHOW");
        //Toast.makeText(Quiz.this, question.getStatement()  , Toast.LENGTH_SHORT).show();

        radioGroup.clearCheck();
        o1Radio.setText(question.getOption1());
        o2Radio.setText(question.getOption2());
        o3Radio.setText(question.getOption3());
        o4Radio.setText(question.getOption4());
        correctText.setText(question.getCorrect());

    }

    void loadFirstQuestion(){

        Intent i = getIntent();
        questionList = (ArrayList<Question>) i.getSerializableExtra("questionsList");
        totalQuestions = questionList.size();

        Collections.shuffle(questionList);
        startQuiz(liveQuestion);
    }

    void nextBtnClicked(){
        if(liveQuestion  < totalQuestions -1) {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(QuizActivity.this, "Select any option", Toast.LENGTH_SHORT).show();
            } else {

                if (o1Radio.isChecked() && o1Radio.getText().toString().equals(correctText.getText()) ||  o2Radio.isChecked() && o2Radio.getText().toString().equals(correctText.getText())  ||  o3Radio.isChecked() && o3Radio.getText().toString().equals(correctText.getText()) || o4Radio.isChecked() && o4Radio.getText().toString().equals(correctText.getText())) {
                    correctScore++;
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();

                }

                startQuiz(++liveQuestion);

            }
        }
        else{
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(QuizActivity.this, "Select any option", Toast.LENGTH_SHORT).show();
            } else {
                startResultActivity();
            }

        }
    }

    void skipBtnClicked(){
        //Toast.makeText(QuizActivity.this, "live : " + liveQuestion + "  Totak : "+ totalQuestions, Toast.LENGTH_SHORT).show();

        if(liveQuestion    < (totalQuestions -1 )) {
            startQuiz(++liveQuestion);

        }
        else{
            startResultActivity();

        }
    }

    void startResultActivity(){

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("subjectName", subjectName);
        intent.putExtra("correctQuestions", correctScore + "");
        intent.putExtra("totalQuestions", totalQuestions + "");
        startActivity(intent);
    }


}
