package com.example.mindmath;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.shape.CornerFamily;

public class NavigationManager {

    public static MaterialButton switchSelected(MaterialButton currentlySelectedButton, MaterialButton selected, MaterialButton unselected1, MaterialButton unselected2, float density) {
        if (currentlySelectedButton == selected) return currentlySelectedButton;
        toggleSelected(selected, true, density);
        toggleSelected(unselected1, false, density);
        toggleSelected(unselected2, false, density);
        return selected;
    }

    public static void toggleSelected(MaterialButton button, boolean selected, float density) {
        if (selected) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#35618e")));
            animateCornerRadius(button, 30, density);
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#73777f")));
            animateCornerRadius(button, 5, density);
        }
    }

    public static void animateCornerRadius(MaterialButton button, int targetRadiusDp, float density) {
        float currentRadius = button.getShapeAppearanceModel().getBottomLeftCornerSize().getCornerSize(new RectF(0, 0, button.getWidth(), button.getHeight()));
        float targetRadiusPx = targetRadiusDp * density;

        ValueAnimator animator = ValueAnimator.ofFloat(currentRadius, targetRadiusPx);
        animator.setDuration(150);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            button.setShapeAppearanceModel(
                    button.getShapeAppearanceModel()
                            .toBuilder()
                            .setAllCorners(CornerFamily.ROUNDED, animatedValue)
                            .build()
            );
        });

        animator.start();
    }
}
