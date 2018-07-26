package com.example.mitch.guessinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_score);
        Intent intent = getIntent();
        TextView scoreView = findViewById(R.id.scoreView);
        scoreView.setText("" + intent.getIntExtra("score", 0));
    }
}
