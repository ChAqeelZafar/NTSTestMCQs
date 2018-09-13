package com.aqeel.johnwick.ntstestmcqs;

import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;

import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.infipic)
                .addGroup("Connect with us")
                .addEmail("aqeelzafar19@gmail.com")
                .addFacebook("ch.aqeel.zafar")
                .addPlayStore("com.aqeel.johnwick.ntstestmcqs")
                .addGitHub("ChAqeelZafar")
                .addInstagram("chaudhary.here")
                .setDescription("I’m a beginner in Android Development, I work well in a team but also on my own as I like to set myself goals which I will achieve,  I have a creative mind and am always up for new challenges. I am well organized and always plan ahead to make sure I manage my time well.")
                .create();

        setContentView(aboutPage);




    }
}
