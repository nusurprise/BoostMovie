package com.connect.boostcamp.glenn.boostmovie;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingAdapters {

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        ImageUtil.loadImage(imageView, url, errorDrawable);
    }

    @BindingAdapter({"bind:htmlText"})
    public static void toHtmlText(TextView title, String sourceString) {
        title.setText(Html.fromHtml(sourceString));
    }
}

