package com.example.mindmath;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationFragment extends Fragment {
    public Fragment currentFragment;
    MaterialButton homeButton, tasksButton, accountButton;
    private MaterialButton currentlySelectedButton = null;

    public NavigationFragment() {
        // Required empty public constructor
    }

    public static NavigationFragment newInstance(String param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }

    // Я очень сильно боюсь что за код ниже мне прилетит, но не важно, оно работает

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setSelectedItem(int itemId) {
        if (itemId == R.id.btn_home) {
            switchSelected(homeButton, tasksButton, accountButton);
        } else if (itemId == R.id.btn_tasks) {
            switchSelected(tasksButton, homeButton, accountButton);
        } else if (itemId == R.id.btn_account) {
            switchSelected(accountButton, homeButton, tasksButton);
        }
    }

    private void switchSelected(MaterialButton selected, MaterialButton unselected1, MaterialButton unselected2) {
        float density = getResources().getDisplayMetrics().density;
        currentlySelectedButton = NavigationManager.switchSelected(currentlySelectedButton, selected, unselected1, unselected2, density);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_navigation, container, false);

        loadFragment(new HomeFragment());
        switchSelected(homeButton, tasksButton, accountButton);

        homeButton = v.findViewById(R.id.btn_home);
        tasksButton = v.findViewById(R.id.btn_tasks);
        accountButton = v.findViewById(R.id.btn_account);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(currentFragment instanceof HomeFragment)) {
                    loadFragment(new HomeFragment());
                    switchSelected(homeButton, tasksButton, accountButton);
                }
            }
        });
        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(currentFragment instanceof TrainingListFragment)) {
                    loadFragment(new TrainingListFragment());
                    switchSelected(tasksButton, homeButton, accountButton);
                }
            }
        });
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(currentFragment instanceof AccountFragment)) {
                    loadFragment(new AccountFragment());
                    switchSelected(accountButton, homeButton, tasksButton);
                }
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