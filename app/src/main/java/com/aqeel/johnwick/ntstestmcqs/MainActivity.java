package com.aqeel.johnwick.ntstestmcqs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    Button subjectsBtn, ntsLinkBtn;
    ImageButton infoBtn, rateBtn, shareBtn ;
    TextView internetUnavailable;

    AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAd();

        subjectsBtn = findViewById(R.id.main_btn_subjects);
        ntsLinkBtn = findViewById(R.id.main_btn_link);
        infoBtn = findViewById(R.id.main_btn_about);
        rateBtn = findViewById(R.id.main_btn_rate);
        shareBtn = findViewById(R.id.main_btn_share);
        internetUnavailable = findViewById(R.id.main_text_internetUnavailable);


        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoCall();
            }
        });

        ntsLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ntsLink();
            }
        });

        subjectsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(haveNetworkConnection()){
                    Intent intent = new Intent(MainActivity.this, SubjectsActivity.class);
                    startActivity(intent);
                }
                else{
                    isError(true);

                }

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCall();
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });

        internetUnavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });


    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
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

    void ntsLink(){
        Uri uri = Uri.parse("http://nts.org.pk/Announcement.php");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    void isError(boolean isShow){
        if(isShow){
            internetUnavailable.setVisibility(View.VISIBLE);
            subjectsBtn.setVisibility(View.GONE);
            ntsLinkBtn.setVisibility(View.GONE);
            infoBtn.setVisibility(View.GONE);
            rateBtn.setVisibility(View.GONE);
            shareBtn.setVisibility(View.GONE);
        }
        else{
            internetUnavailable.setVisibility(View.GONE);
            subjectsBtn.setVisibility(View.VISIBLE);
            ntsLinkBtn.setVisibility(View.VISIBLE);
            infoBtn.setVisibility(View.VISIBLE);
            rateBtn.setVisibility(View.VISIBLE);
            shareBtn.setVisibility(View.VISIBLE);
        }
    }

    void loadAd(){

        MobileAds.initialize(this, getString(R.string.ad_app_id));
        adView = findViewById(R.id.main_adView);
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

        adView.loadAd(request);
    }
}
