package fr.stjolorient.snir2.navyseaexplorator;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash_screen extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(splash_screen.this, main_menu.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
