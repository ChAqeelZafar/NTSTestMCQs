package com.aqeel.johnwick.ntstestmcqs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aqeel.johnwick.ntstestmcqs.Adapters.SubjectAdapter;
import com.aqeel.johnwick.ntstestmcqs.Models.Subject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    List<Subject> subjectList = new ArrayList<>();
    String NTS = "nts";
    CardView loadingCard ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);


        recyclerView = findViewById(R.id.subjects_recycler_subjects);
        loadingCard = findViewById(R.id.subject_card_loading);

        isLoading(true);





        firestore = FirebaseFirestore.getInstance();


        //Toast.makeText(this, buttonName, Toast.LENGTH_LONG).show();


        firestore.collection(NTS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.get("name").toString();
                                String url = document.get("url").toString();
                                String id = document.getId();

                                subjectList.add(new Subject(name, url, id));


                            }


                            printsubjects() ;
                        } else {
                            Toast.makeText(SubjectsActivity.this, "Failed to connect Internet", Toast.LENGTH_SHORT).show();

                        }
                    }
                });






    }

    void printsubjects(){
        recyclerView.setAdapter(new SubjectAdapter(subjectList, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        isLoading(false);

    }

    void isLoading(boolean isShow){
        if(isShow){
            recyclerView.setVisibility(View.GONE);
            loadingCard.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            loadingCard.setVisibility(View.GONE);
        }
    }
}
