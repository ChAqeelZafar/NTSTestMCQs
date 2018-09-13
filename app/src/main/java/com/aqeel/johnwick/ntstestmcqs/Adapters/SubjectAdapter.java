package com.aqeel.johnwick.ntstestmcqs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqeel.johnwick.ntstestmcqs.ChaptersActivity;
import com.aqeel.johnwick.ntstestmcqs.Models.Subject;
import com.aqeel.johnwick.ntstestmcqs.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.Holder> {

    List<Subject> subjectList ;
    Context ctx ;

    public SubjectAdapter(List<Subject> subjectList, Context ctx) {
        this.subjectList = subjectList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjectviewholder, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final Subject subject = subjectList.get(position);

        holder.subjectNameText.setText(subject.getSubjectName());
        if(!(subject.getImgUrl().trim().equals(""))){
            Glide.with(this.ctx).load(subject.getImgUrl()).apply(RequestOptions.circleCropTransform()).into(holder.subjectImg);
        }

        holder.parentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ChaptersActivity.class);
                intent.putExtra("subjectId", subject.getId());
                intent.putExtra("subjectName", subject.getSubjectName());
                ctx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView subjectImg ;
        TextView subjectNameText ;
        CardView parentCard;

        public Holder(@NonNull View itemView) {
            super(itemView);
            subjectImg = itemView.findViewById(R.id.subjectviewholder_img_subject);
            subjectNameText = itemView.findViewById(R.id.subjectviewholder_text_subjectname);
            parentCard = itemView.findViewById(R.id.subjectviewholder_card);
        }
    }
}
