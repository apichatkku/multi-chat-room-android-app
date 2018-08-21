package com.api.tawan.tawanapichatapplication;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position > 1 || position < -1) {
            view.setAlpha(0);
        } else {
            float alpha = 1 - (float) (Math.abs(position));
            view.setAlpha(alpha);
        }
    }
}
