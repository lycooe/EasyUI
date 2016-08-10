package com.lz.easyui.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ViewAnimation {
    public static void changeViewVorG(final View view, final int animOpen, final int animClose, boolean isToVisible) {
        int anim;
        final int visibleOrGone;
        if(isToVisible && view.getVisibility() == View.VISIBLE){
            return;
        }
        if(!isToVisible && (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE)){
            return;
        }
        if (isToVisible) {
            anim = animOpen;
            visibleOrGone = View.VISIBLE;
        } else {
            anim = animClose;
            visibleOrGone = View.GONE;
        }

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(visibleOrGone);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(animation);
    }
}
