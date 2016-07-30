package com.twiscode.kubisadmin;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

/**
 * Created by Crusader on 7/29/2016.
 */
public class MenuArrowDrawable extends DrawerArrowDrawable{
    private final ValueAnimator mMenuToArrowAnimator;
    private final ValueAnimator mArrowToMenuAnimator;

    public MenuArrowDrawable(Context context) {
        super(context);

        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setPosition((float)animation.getAnimatedValue());
            }
        };

        mMenuToArrowAnimator = ValueAnimator.ofFloat(0f, 1f);
        mMenuToArrowAnimator.setDuration(250);
        mMenuToArrowAnimator.addUpdateListener(animatorUpdateListener);

        mArrowToMenuAnimator = ValueAnimator.ofFloat(1f, 0f);
        mArrowToMenuAnimator.setDuration(250);
        mArrowToMenuAnimator.addUpdateListener(animatorUpdateListener);
    }

    public void setPosition(float position) {
        if (position >= 1f) {
            setVerticalMirror(true);
        } else if (position <= 0f) {
            setVerticalMirror(false);
        }
        setProgress(position);
    }

    public float getPosition() {
        return getProgress();
    }

    public void animateDrawable(boolean menuToArrow) {
        if (menuToArrow && getPosition() >= 1f) return;
        if (!menuToArrow && getPosition() <= 0f) return;

        ValueAnimator animator = menuToArrow? mMenuToArrowAnimator : mArrowToMenuAnimator;
        if (animator.isRunning()) animator.end();
        animator.start();
    }
}
