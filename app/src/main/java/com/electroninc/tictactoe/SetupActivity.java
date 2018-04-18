package com.electroninc.tictactoe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SetupActivity extends AppCompatActivity {

    public static final String GAMETYPE = "gametype";
    public static final String GAMETYPE1 = "gametype1";
    public static final String GAMETYPE2 = "gametype2";
    public static final String GAMETYPE3 = "gametype3";
    public static final String GAMETYPE4 = "gametype4";
    public static final String GRIDSIZE = "gridsize";

    private String mGametype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Fade fadeAnimation = new Fade();
            fadeAnimation.setDuration(300);
            getWindow().setEnterTransition(fadeAnimation);
        }

        setContentView(R.layout.activity_setup);

        mGametype = getIntent().getStringExtra(GAMETYPE);
        getSupportActionBar().setTitle("Setup");

        TextView grid3 = findViewById(R.id.grid3);
        TextView grid4 = findViewById(R.id.grid4);
        TextView grid5 = findViewById(R.id.grid5);
        TextView grid6 = findViewById(R.id.grid6);
        TextView grid7 = findViewById(R.id.grid7);
        TextView grid8 = findViewById(R.id.grid8);
        TextView grid9 = findViewById(R.id.grid9);
        TextView grid10 = findViewById(R.id.grid10);

        grid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 3);
            }
        });
        grid4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 4);
            }
        });
        grid5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 5);
            }
        });
        grid6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 6);
            }
        });
        grid7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 7);
            }
        });
        grid8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 8);
            }
        });
        grid9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 9);
            }
        });
        grid10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(mGametype, 10);
            }
        });
    }

    void startGame(String type, int gridSize) {
        Intent intent = null;
        switch (type) {
            case GAMETYPE1:
                intent = new Intent(SetupActivity.this, ComputerVsHumanActivity.class);
                break;
            case GAMETYPE2:
                intent = new Intent(SetupActivity.this, ComputerVsComputerActivity.class);
                break;
            case GAMETYPE3:
                intent = new Intent(SetupActivity.this, HumanOfflineActivity.class);
                break;
            case GAMETYPE4:
                intent = new Intent(SetupActivity.this, HumanOnlineActivity.class);
                break;
        }
        intent.putExtra(GRIDSIZE, gridSize);
        startActivity(intent);
    }

}
