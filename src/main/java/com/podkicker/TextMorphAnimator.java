package com.podkicker;

import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;


public class TextMorphAnimator {

    public static TextChangeEvaluator newTextChangeEvaluator() {
        return new TextChangeEvaluator();
    }

    public static void animate(final @NonNull TextView titleLabel, final CharSequence title, int duration) {

        if (Objects.equals(titleLabel.getText(), title)) {
            return;
        }

        ObjectAnimator o = ObjectAnimator.ofObject(TextMorphAnimator.wrap(titleLabel), "text", TextMorphAnimator.newTextChangeEvaluator(), title);

        o.setInterpolator(new AccelerateDecelerateInterpolator());
        o.setDuration(duration);

        o.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                titleLabel.setText(title);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        o.start();

    }

    public static class TextChangeEvaluator implements TypeEvaluator<String> {

        private final IntEvaluator intEvaluator = new IntEvaluator();
        private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        private final StringBuilder sb = new StringBuilder();

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {

            endValue = endValue.toLowerCase(Locale.getDefault());

            int endValueLength = endValue.length();
            int startValueLength = startValue.length();
            int newStringLength = intEvaluator.evaluate(fraction, startValueLength, endValueLength);

            sb.setLength(0);
            sb.append(startValue);

            if (endValueLength > startValueLength) {
                sb.append(endValue.substring(startValueLength));
            }

            sb.setLength(newStringLength);

            for (int i = 0; i < Math.min(startValueLength, endValueLength); i++) {

                int is = alphabet.indexOf(startValue.charAt(i));
                int ie = alphabet.indexOf(endValue.charAt(i));

                if (is == -1 || ie == -1) {
                    sb.replace(i, i + 1, String.valueOf(endValue.charAt(i)));
                } else {
                    int pos = intEvaluator.evaluate(fraction, is, ie);
                    sb.replace(i, i + 1, String.valueOf(alphabet.charAt(pos)));
                }
            }

            return sb.toString();
        }
    }

    public static class TextViewWrapper {
        private final TextView textView;

        public TextViewWrapper(TextView textView) {
            this.textView = textView;
        }

        public String getText() {
            return textView.getText().toString();
        }

        public void setText(String text) {
            textView.setText(text);
        }
    }


    public static TextViewWrapper wrap(TextView textview) {
        return new TextViewWrapper(textview);
    }


}
