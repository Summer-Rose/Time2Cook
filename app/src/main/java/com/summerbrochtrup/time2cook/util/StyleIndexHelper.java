package com.summerbrochtrup.time2cook.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.summerbrochtrup.time2cook.R;

/**
 * Created by summerbrochtrup on 7/26/16.
 */
public class StyleIndexHelper {
    Context mContext;

    public StyleIndexHelper(Context context) {
        mContext = context;
    }

    public void styleImageView(ImageView imageView, int styleIndex) {
        if (styleIndex == 0) {
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.peach));
            imageView.setImageResource(R.drawable.image);
        } else if (styleIndex == 1) {
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.brown));
            imageView.setImageResource(R.drawable.image_two);
        } else if (styleIndex == 2) {
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_salmon));
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mint));
            imageView.setImageResource(R.drawable.image_four);
        } else {
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_salmon));
            imageView.setImageResource(R.drawable.image_five);
        }
    }

    public void styleTextView(TextView textView, int styleIndex) {
        if (styleIndex == 0) {
            textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.brown));
        } else if (styleIndex == 1) {
            textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.peach));
        } else if (styleIndex == 2) {
            textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_salmon));
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mint));
        }
    }
}
