package com.wiatec.bplay.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by patrick on 2017/2/14.
 */

public class Zoom {

    public static void zoomIn09to10(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"scaleX" ,0.9f ,1.0f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view,"scaleY" ,0.9f,1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(150);
        animatorSet.play(animator).with(animator1);
        animatorSet.start();
    }

    public static void zoomIn10to11to10(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"scaleX" ,1.0f ,1.1f,1.0f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view,"scaleY" ,1.0f ,1.1f,1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(150);
        animatorSet.play(animator).with(animator1);
        animatorSet.start();
    }
}
