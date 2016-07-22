package com.summerbrochtrup.time2cook.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by summerbrochtrup on 7/22/16.
 */
public class RobotoFontTextView extends TextView {

    public RobotoFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto.ttf"));
    }
}