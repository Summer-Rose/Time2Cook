package com.summerbrochtrup.time2cook.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by summerbrochtrup on 7/22/16.
 */
public class RobotoFontButton extends Button {

    public RobotoFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto.ttf"));
    }
}
