package com.devreport.cloud.service;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.widget.ImageView;

public class AnimateService {
    private static final String TAG = "AnimateService";

    // 함수가 끝나면 ValueAnimation 이 사라지기 때문에 클래스 변수로 등록해둔 animator 중에서 돌아가고 있지 않은 애니메이터를 찾아야 한다.
    private static ValueAnimator[] animators = new ValueAnimator[10];

    static {
        // 애니메이터 객체 초기화
        for (int i = 0; i < animators.length; i++) {
            animators[i] = new ValueAnimator();
        }
    }

    // 함수가 끝나면 ValueAnimation 이 사라지기 때문에 클래스 변수로 등록해둔 animator 중에서 돌아가고 있지 않은 애니메이터를 찾아야 한다.
    public static void changeImageColor (ImageView imageView, String colorTo, long duration) {
        for (ValueAnimator animator : animators) {
            if (!animator.isRunning()) {
                int colorFrom = (int) imageView.getTag();

                Log.d(TAG, "sent : " + colorFrom);

                animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, Color.parseColor(colorTo));
                animator.setDuration(duration);

                animator.addUpdateListener(animatorRun -> {
                    imageView.setColorFilter((int) animatorRun.getAnimatedValue(), PorterDuff.Mode.SRC_IN);
                    imageView.setTag(animatorRun.getAnimatedValue());
                });

                animator.start();
            }
        }
    }

    public static void changeGradientColor (GradientDrawable gradient, String topToString, String bottomToString, long duration) {
        for (ValueAnimator animator : animators) {
            if (!animator.isRunning()) {
                final int topFrom = gradient.getColors()[0];
                final int bottomFrom = gradient.getColors()[1];

                final int topTo = Color.parseColor(topToString);
                final int bottomTo = Color.parseColor(bottomToString);

                final ArgbEvaluator evaluator = new ArgbEvaluator();

                animator = TimeAnimator.ofFloat(0.0f, 1.0f);
                animator.setDuration(duration);

                animator.addUpdateListener(valueAnimator -> {
                    Float fraction = valueAnimator.getAnimatedFraction();
                    int topColor = (int) evaluator.evaluate(fraction, topFrom, topTo);
                    int bottomColor = (int) evaluator.evaluate(fraction, bottomFrom, bottomTo);

                    int[] colorArray = {topColor, bottomColor};

                    gradient.setColors(colorArray);
                });

                animator.start();
            }
        }
    }
}
