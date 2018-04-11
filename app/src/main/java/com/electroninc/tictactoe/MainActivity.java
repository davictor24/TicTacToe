package com.electroninc.tictactoe;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_PLAY = "play";
    private static final String FRAGMENT_TAG_SETTINGS = "settings";
    private static final String FRAGMENT_TAG_ABOUT = "about";

    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTags;
    private ActionBar mActionBar;
    private ArrayList<String> mActionBarTexts;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_play:
                    switchFragment(0);
                    return true;
                case R.id.navigation_settings:
                    switchFragment(1);
                    return true;
                case R.id.navigation_about:
                    switchFragment(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();
        BottomNavigationView mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupFragment();
    }

    private void setupFragment() {
        mFragmentManager = getFragmentManager();

        mFragments = new ArrayList<>();
        mFragments.add(new PlayFragment());
        mFragments.add(new SettingsFragment());
        mFragments.add(new AboutFragment());

        mTags = new ArrayList<>();
        mTags.add(FRAGMENT_TAG_PLAY);
        mTags.add(FRAGMENT_TAG_SETTINGS);
        mTags.add(FRAGMENT_TAG_ABOUT);

        mActionBarTexts = new ArrayList<>();
        mActionBarTexts.add(getString(R.string.title_play));
        mActionBarTexts.add(getResources().getString(R.string.settings));
        mActionBarTexts.add(getString(R.string.title_about));

        mActionBar.setTitle(mActionBarTexts.get(0));

        if (mFragmentManager.findFragmentByTag(FRAGMENT_TAG_PLAY) == null
                && mFragmentManager.findFragmentByTag(FRAGMENT_TAG_ABOUT) == null) {
            switchFragment(0);
        }
    }

    private void switchFragment(int i) {
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mFragments.get(i), mTags.get(i))
                .commit();
        mActionBar.setTitle(mActionBarTexts.get(i));
    }

}
