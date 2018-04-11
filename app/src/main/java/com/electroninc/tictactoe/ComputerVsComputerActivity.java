package com.electroninc.tictactoe;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ComputerVsComputerActivity extends AppCompatActivity {

    private int mGridSize;
    private GridLayout mGrid;
    private ArrayList<TextView> mGridSquares;
    private Board mBoard;
    private int mGameCounter;

    private TextView mStatusTextView;
    private TextView mToPlayTextView;
    private Button mPlayAgainButton;

    private Handler mHandler;
    private Runnable mRunnable;

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

        mStatusTextView.setText(R.string.game_is_on);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                think();
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

    private void clearGame() {
        mGrid.removeAllViews();
        mGridSquares.clear();
        mBoard = null;
        mPlayAgainButton.setVisibility(View.INVISIBLE);
        mHandler = null;
        mRunnable = null;
    }

    private void think() {
        mToPlayTextView.setText(getResources().getString((mGameCounter % 2 == 1) ? R.string.computer_1_thinking : R.string.computer_2_thinking));
        Move move = Minimax.getMove(mBoard, ((mGameCounter % 2 == 1) ? 1 : 2));
        clicked(move.getX(), move.getY());
        if (Minimax.checkWin(mBoard, 1) == 0 && mBoard.getCounter() < Math.pow(mGridSize, 2))
            mToPlayTextView.setText(getResources().getString((mGameCounter % 2 == 1) ? R.string.computer_1_thinking : R.string.computer_2_thinking));
    }

    private void clicked(int x, int y) {
        Move move = new Move(((mGameCounter % 2 == 1) ? 1 : 2), x, y);
        mBoard.makeMove(move);
        int ind = (mGridSize * (y - 1)) + x - 1;
        mGridSquares.get(ind).setText((mGameCounter % 2 == 1) ? Board.X : Board.O);

        if (Minimax.checkWin(mBoard, 1) == 0 && mGameCounter == Math.pow(mGridSize, 2)) {
            mStatusTextView.setText(getResources().getString(R.string.draw));
            mToPlayTextView.setText("");
            mPlayAgainButton.setVisibility(View.VISIBLE);
        } else if (Minimax.checkWin(mBoard, 1) == 1) {
            mStatusTextView.setText(getResources().getString(R.string.computer_1_wins));
            mToPlayTextView.setText("");
            mPlayAgainButton.setVisibility(View.VISIBLE);
        } else if (Minimax.checkWin(mBoard, 2) == 1) {
            mStatusTextView.setText(getResources().getString(R.string.computer_2_wins));
            mToPlayTextView.setText("");
            mPlayAgainButton.setVisibility(View.VISIBLE);
        } else {
            mHandler.postDelayed(mRunnable, 1000);
            mGameCounter++;
        }
    }

}
