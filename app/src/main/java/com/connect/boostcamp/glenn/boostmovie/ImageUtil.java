package com.connect.boostcamp.glenn.boostmovie;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil {

    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorDrawable)
                .into(imageView);
    }

}