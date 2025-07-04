package com.example.fitstepo;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_schedule);

        // Обработка нажатия на кнопку "Назад"
        ImageView backButton = findViewById(R.id.Back);
        backButton.setOnClickListener(v -> {
            finish(); // Закрывает эту активность и возвращает в MainActivity
        });
    }
}

