package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
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
    }
}