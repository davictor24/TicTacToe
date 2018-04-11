package com.electroninc.tictactoe;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HumanOfflineActivity extends AppCompatActivity {

    private int mGridSize;
    private GridLayout mGrid;
    private ArrayList<TextView> mGridSquares;
    private Board mBoard;
    private int mGameCounter;

    private TextView mStatusTextView;
    private TextView mToPlayTextView;
    private Button mPlayAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_offline);

        mStatusTextView = findViewById(R.id.status);
        mToPlayTextView = findViewById(R.id.to_play);
        mPlayAgainButton = findViewById(R.id.play_again);

        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearGame();
                setup();
            }
        });

        getSupportActionBar().setTitle(getResources().getString(R.string.tic_tac_toe));
        mGridSize = getIntent().getIntExtra(SetupActivity.GRIDSIZE, 3);
        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearGame();
    }

    private void setup() {
        mGrid = findViewById(R.id.grid);
        mGrid.setRowCount(mGridSize);
        mGrid.setColumnCount(mGridSize);

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int totalWidth = Math.round(360 * (metrics.densityDpi / 160f));
        int unitWidth = (totalWidth / mGridSize) - 2;

        mGridSquares = new ArrayList<>();

        for (int i = 0; i < Math.pow(mGridSize, 2); i++) {
            TextView textView = new TextView(getApplicationContext());
            textView.setClickable(true);
            textView.setLayoutParams(new LinearLayout.LayoutParams(unitWidth, unitWidth));
            textView.setBackgroundResource(R.drawable.rect);
            textView.setTextAppearance(this, android.R.style.TextAppearance_Large);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(8, 8, 8, 8);
            mGrid.addView(textView);
            mGridSquares.add(textView);
        }

        mBoard = new Board(mGridSize);
        mGameCounter = 1;

        enableListeners();
        mToPlayTextView.setText(getResources().getString(R.string.player_1_turn));
        mStatusTextView.setText(R.string.game_is_on);
    }

    private void clearGame() {
        mGrid.removeAllViews();
        mGridSquares.clear();
        mBoard = null;
        mPlayAgainButton.setVisibility(View.INVISIBLE);
    }

    private void enableListeners() {
        for (int i = 0; i < mGridSquares.size(); i++) {
            final int ind = i;
            if (mBoard.getMarker(1 + (i % mGridSize), (i + mGridSize) / mGridSize).equals(Board.D)) {
                mGridSquares.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clicked(1 + (ind % mGridSize), (ind + mGridSize) / mGridSize);
                    }
                });
            }
        }
    }

    private void disableListeners() {
        for (int i = 0; i < mGridSquares.size(); i++) {
            mGridSquares.get(i).setOnClickListener(null);
        }
    }

    private void clicked(int x, int y) {
        disableListeners();
        Move move = new Move(((mGameCounter % 2 == 1) ? 1 : 2), x, y);
        mBoard.makeMove(move);
        int ind = (mGridSize * (y - 1)) + x - 1;
        mGridSquares.get(ind).setText((mGameCounter % 2 == 1) ? Board.X : Board.O);

        if (Minimax.checkWin(mBoard, 1) == 0 && mGameCounter == Math.pow(mGridSize, 2)) {
            mStatusTextView.setText(getResources().getString(R.string.draw));
            mToPlayTextView.setText("");
            mPlayAgainButton.setVisibility(View.VISIBLE);
        } else if (Minimax.checkWin(mBoard, 1) == 1) {
            mStatusTextView.setText(getResources().getString(R.string.player_1_wins));
            mToPlayTextView.setText("");
            mPlayAgainButton.setVisibility(View.VISIBLE);
        } else if (Minimax.checkWin(mBoard, 2) == 1) {
            mStatusTextView.setText(getResources().getString(R.string.player_2_wins));
            mToPlayTextView.setText("");
            mPlayAgainButton.setVisibility(View.VISIBLE);
        } else {
            mGameCounter++;
            enableListeners();
            mToPlayTextView.setText(getResources().getString((mGameCounter % 2 == 1) ? R.string.player_1_turn : R.string.player_2_turn));
        }
    }

}
