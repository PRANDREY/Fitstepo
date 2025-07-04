package com.example.fitstepo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    private ImageView progressFill;
    private ImageView calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressFill = findViewById(R.id.progress_fill);
        setProgressHeight(100);

        calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WorkoutScheduleActivity.class);
            startActivity(intent);
        });
    }

    private void setProgressHeight(int heightPx) {
        ViewGroup.LayoutParams params = progressFill.getLayoutParams();
        params.height = heightPx;
        progressFill.setLayoutParams(params);
    }
}

