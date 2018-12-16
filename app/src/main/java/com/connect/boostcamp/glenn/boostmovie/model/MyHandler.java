package com.connect.boostcamp.glenn.boostmovie.model;

import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.view.View;

import com.connect.boostcamp.glenn.boostmovie.R;

public class MyHandler {
    private String link;

    public MyHandler(String link) {
        this.link = link;
    }

    public void onMovieClick(View view) {
        String url = link;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.BLUE);
        builder.setStartAnimations(view.getContext(), R.anim.translate_right_in, R.anim.translate_left_out);
        builder.setExitAnimations(view.getContext(), R.anim.translate_left_in, R.anim.translate_right_out);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
    }
}
