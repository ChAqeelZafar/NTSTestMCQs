package com.aqeel.johnwick.ntstestmcqs;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nullable;

import static android.graphics.Color.parseColor;

public class PreprationActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    ArrayList<Question>  questionList = new ArrayList<>();
    int questionNo = 0, nextToast= 0 , previousToast=0 ;



    TextView totalQuestionsText,  statementText, option1Text , option2Text, option3Text, option4Text, correctText ;
    Button nextBtn, previousBtn, startQuizBtn ;
    CardView loadindCard, statementCard, optionsCard ;


    String subjectId, chapterId, chapterName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepration);

        startQuizBtn = findViewById(R.id.prepration_btn_startQuiz);
        totalQuestionsText = findViewById(R.id.prepration_text_totalQuestions);
        statementText = findViewById(R.id.question_questionStatement);
        option1Text = findViewById(R.id.question_text_option1);
        option2Text = findViewById(R.id.question_text_option2);
        option3Text = findViewById(R.id.question_text_option3);
        option4Text = findViewById(R.id.question_text_option4);
        correctText = findViewById(R.id.questionshow_text_correct);
        nextBtn = findViewById(R.id.question_btn_next);
        previousBtn = findViewById(R.id.question_btn_previous);


        loadindCard = findViewById(R.id.questionShow_card_loading);
        statementCard = findViewById(R.id.questionshow_cardview_statement) ;
        optionsCard = findViewById(R.id.questionshow_card_options);



        subjectId = getIntent().getStringExtra("subjectId");
        chapterId = getIntent().getStringExtra("chapterId");
        chapterName = getIntent().getStringExtra("chapterName");
        firestore = FirebaseFirestore.getInstance();

        loadFirstQuestion();


        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreprationActivity.this, QuizActivity.class);
                intent.putExtra("questionsList",  questionList);
                PreprationActivity.this.startActivity(intent);
            }
        });




        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionNo<(questionList.size()-1)){
                    nextToast=0;
                    previousToast = 0 ;
                    printQuestion(++questionNo);

                }
                else{
                    if(nextToast!=1) {
                        nextToast++;
                        Toast.makeText(PreprationActivity.this, "Questions End", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionNo>0){
                    nextToast=0;
                    previousToast = 0 ;
                    printQuestion(--questionNo);

                }
                else{
                    if(previousToast!=1) {
                        previousToast++;
                        Toast.makeText(PreprationActivity.this, "No more back", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });









    }

    void printQuestion(int index){
        Question question = questionList.get(index);
        totalQuestionsText.setText((index+1) + "/" + questionList.size());
        statementText.setText(question.getStatement());
        option1Text.setText(question.getOption1());
        option2Text.setText(question.getOption2());
        option3Text.setText(question.getOption3());
        option4Text.setText(question.getOption4());
        correctText.setText("CORRECT : " + question.getCorrect());
        isLoading(false);
    }

    void loadFirstQuestion(){
        isLoading(true);
        CollectionReference temp = firestore.collection("nts").document(subjectId).collection("chapters").document(chapterId).collection("questions");

        temp.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                questionList.clear();



                for(QueryDocumentSnapshot document : queryDocumentSnapshots) {

                    String statement = "", option1 = "", option2 = "", option3 = "", option4 = "", correct = "";

                    String s = document.getId();
                    if (document.contains("s")) {
                        statement = document.get("s").toString();
                    }


                    if (document.contains("o1")) {
                        option1 = document.get("o1").toString();
                    }

                    if (document.contains("o2")) {
                        option2 = document.get("o2").toString();
                    }

                    if (document.contains("o3")) {
                        option3 = document.get("o3").toString();
                    }

                    if (document.contains("o4")) {
                        option4 = document.get("o4").toString();
                    }

                    if (document.contains("c")) {
                        correct = document.get("c").toString();
                    }

                    if(!(statement.equals(""))) {
                        questionList.add(new Question(statement, option1, option2, option3, option4, correct));
                    }
                }



                Collections.shuffle(questionList);
                printQuestion(0) ;
            }
        });
    }

    void isLoading(boolean isShow){
        if(isShow){
            loadindCard.setVisibility(View.VISIBLE);
            statementCard.setVisibility(View.GONE);
            optionsCard.setVisibility(View.GONE);
            correctText.setVisibility(View.GONE);
            nextBtn.setVisibility(View.GONE);
            previousBtn.setVisibility(View.GONE);
            startQuizBtn.setVisibility(View.GONE);
            totalQuestionsText.setVisibility(View.GONE);
        }
        else{
            loadindCard.setVisibility(View.GONE);
            statementCard.setVisibility(View.VISIBLE);
            optionsCard.setVisibility(View.VISIBLE);
            correctText.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
            previousBtn.setVisibility(View.VISIBLE);
            startQuizBtn.setVisibility(View.VISIBLE);
            totalQuestionsText.setVisibility(View.VISIBLE);
        }
    }
}

