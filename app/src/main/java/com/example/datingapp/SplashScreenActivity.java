package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.imageView);

        // Membuat animasi pada logo, menggunakan fadeIn dan fadeout
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imageView, "alpha", 1,0.3f);
        fadeOut.setDuration(2000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1);
        fadeIn.setDuration(2000);

        //Menjalankan animasi dari objectanimator
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(fadeIn).after(fadeOut);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorSet.start();
            }
        });
        mAnimatorSet.start();

        //akan membuka aplikasi setelah logo beranimasi 4 detik
        new CountDownTimer(4000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class); //dari splashscreen menuju mainactivity
                startActivity(intent);
                finish();
            }
        }.start();


    }
}