package com.electroninc.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class HumanOnlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_online);

        Toast.makeText(this, "This feature is not yet available", Toast.LENGTH_LONG).show();
    }
}
