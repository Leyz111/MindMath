package com.example.mindmath;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationFragment extends Fragment {
    MaterialButton homeButton, tasksButton, accountButton;


    public NavigationFragment() {
        // Required empty public constructor
    }

    public static NavigationFragment newInstance(String param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Я очень сильно боюсь что за код ниже мне прилетит, но не важно, оно работает

    private void toggleSelected(MaterialButton button, boolean selected) {
        if (selected) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#35618e")));
            animateCornerRadius(button, 30);
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#73777f")));
            animateCornerRadius(button, 5);
        }
    }

    private void switchSelected(MaterialButton selected, MaterialButton unselected1, MaterialButton unselected2) {
        toggleSelected(selected, true);
        toggleSelected(unselected1, false);
        toggleSelected(unselected2, false);
    }

    private void animateCornerRadius(MaterialButton button, int targetRadiusDp) {
        float currentRadius = button.getShapeAppearanceModel().getBottomLeftCornerSize().getCornerSize(new RectF(0, 0, button.getWidth(), button.getHeight()));
        float targetRadiusPx = targetRadiusDp * getResources().getDisplayMetrics().density;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_navigation, container, false);

        loadFragment(new HomeFragment());

        homeButton = v.findViewById(R.id.btn_home);
        tasksButton = v.findViewById(R.id.btn_tasks);
        accountButton = v.findViewById(R.id.btn_account);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchSelected(homeButton, tasksButton, accountButton);
                loadFragment(new HomeFragment());
            }
        });
        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchSelected(tasksButton, homeButton, accountButton);
                loadFragment(new TasksFragment());
            }
        });
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchSelected(accountButton, homeButton, tasksButton);
                loadFragment(new AccountFragment());
            }
        });
        return v;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();

        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}