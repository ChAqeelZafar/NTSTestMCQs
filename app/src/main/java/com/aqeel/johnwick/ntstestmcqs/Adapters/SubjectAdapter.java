package com.aqeel.johnwick.ntstestmcqs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aqeel.johnwick.ntstestmcqs.ChaptersActivity;
import com.aqeel.johnwick.ntstestmcqs.Models.Subject;
import com.aqeel.johnwick.ntstestmcqs.R;
import com.aqeel.johnwick.ntstestmcqs.SubjectsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.Holder>{

    List<Subject> subjectList ;
    Context ctx ;
    InterstitialAd interstitialAd;

    public SubjectAdapter(List<Subject> subjectList, Context ctx) {
        this.subjectList = subjectList;
        this.ctx = ctx;





    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        interstitialAd = new InterstitialAd(ctx);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);



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

                if(haveNetworkConnection()) {


                    if(interstitialAd.isLoaded()) {
                        // Step 1: Display the interstitial
                        interstitialAd.show();
                        // Step 2: Attach an AdListener
                        interstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                // Step 2.1: Load another ad
                                AdRequest adRequest = new AdRequest.Builder()
                                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                        .build();
                                interstitialAd.loadAd(adRequest);

                                // Step 2.2: Start the new activity
                                Intent intent = new Intent(ctx, ChaptersActivity.class);
                                intent.putExtra("subjectId", subject.getId());
                                intent.putExtra("subjectName", subject.getSubjectName());
                                ctx.startActivity(intent);
                            }
                        });
                    }
// If it has not loaded due to any reason simply load the next activity
                    else {
                        Intent intent = new Intent(ctx, ChaptersActivity.class);
                        intent.putExtra("subjectId", subject.getId());
                        intent.putExtra("subjectName", subject.getSubjectName());
                        ctx.startActivity(intent);
                    }



                }
                else{
                    Toast.makeText(ctx, "Internet is Unavailble\nConnect to the Internet", Toast.LENGTH_SHORT).show();
                    ctx.startActivity(new Intent(ctx, SubjectsActivity.class));
                }
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


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }








}
