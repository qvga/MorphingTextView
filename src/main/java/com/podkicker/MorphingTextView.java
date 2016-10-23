package com.podkicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class MorphingTextView extends TextView {

    public MorphingTextView(Context context) {
        super(context);
    }

    public MorphingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void morphTo(CharSequence text){
        TextMorphAnimator.animate(this, text, 200);
    }

    public void morphTo(CharSequence text, int duration){

        TextMorphAnimator.animate(this, text, duration);

    }

}
