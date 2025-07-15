package com.example.fitstepo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkoutScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_schedule);

        TextView dayOfWeekText = findViewById(R.id.day_of_week);
        TextView dateText = findViewById(R.id.date);

        Date currentDate = new Date();

        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(currentDate); // Thursday
        String date = new SimpleDateFormat("d MMMM", Locale.ENGLISH).format(currentDate);     // 4 July

        dayOfWeekText.setText(dayOfWeek);
        dateText.setText(date);

        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(WorkoutScheduleActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
