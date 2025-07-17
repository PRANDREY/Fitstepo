package com.example.fitstepo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ImageView calendarButton;
    private BottomNavigationView bottomNavigation;

    private TextView stepsText;
    private TextView distanceText;
    private TextView caloriesText;

    private TextView rope;
    private TextView treadmill;
    private TextView barbell;
    private TextView pointsText;

    private StepTracker stepTracker;
    private Handler handler = new Handler();

    private DashboardFragment currentDashboardFragment = null;

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            int steps = stepTracker.getCurrentStepCount();
            float distance = stepTracker.getDistanceKm();
            float calories = stepTracker.getCaloriesBurned();
            float points = stepTracker.getPoints();

            stepsText.setText(" " + steps);
            pointsText.setText(String.format("%.1f", points));
            distanceText.setText(String.format("%.2f km", distance));
            caloriesText.setText(String.format("%.0f kcal", calories));

            double ropeValue = distance / 0.00135;
            double treadmillValue = distance * 4000;
            double barbellValue = distance / 0.003;

            rope.setText(String.format("%.0f", ropeValue));
            treadmill.setText(String.format("%.0f", treadmillValue));
            barbell.setText(String.format("%.0f", barbellValue));

            if (currentDashboardFragment != null) {
                currentDashboardFragment.updateProgressBar(25);
                currentDashboardFragment.updatePtsText((int) points);
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView fill0 = findViewById(R.id.progress_fill1);
        ImageView fill1 = findViewById(R.id.progress_fill3);
        ImageView fill2 = findViewById(R.id.progress_fill5);

        int maxHeightDp = 146;
        float scale = getResources().getDisplayMetrics().density;
        int maxHeightPx = (int) (maxHeightDp * scale + 0.5f);

        MainProgressBarsManager progressManager = new MainProgressBarsManager(fill0, fill1, fill2, maxHeightPx);
        progressManager.setProgress(0, 70, 100);
        progressManager.setProgress(1, 40, 100);
        progressManager.setProgress(2, 75, 100);

        startService(new Intent(this, PulseService.class));

        calendarButton = findViewById(R.id.calendarButton);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        stepsText = findViewById(R.id.stepsText);
        distanceText = findViewById(R.id.distanceText);
        caloriesText = findViewById(R.id.caloriesText);
        pointsText = findViewById(R.id.pointsText);

        rope = findViewById(R.id.rope);
        treadmill = findViewById(R.id.treadmill);
        barbell = findViewById(R.id.barbell);

        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(DateHelper.getFormattedDate());

        // Переход по клику на calendarButton
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WorkoutScheduleActivity.class);
            startActivity(intent);
        });

        // Переход по клику на barbell (как было у тебя)
        barbell.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WorkoutScheduleActivity.class);
            startActivity(intent);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_medal) {
                currentDashboardFragment = new DashboardFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, currentDashboardFragment)
                        .commit();
                return true;

            } else if (itemId == R.id.nav_home) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .remove(fragment)
                            .commit();
                }
                currentDashboardFragment = null;
                return true;

            } else if (itemId == R.id.nav_heart) {
                Fragment overviewFragment = new DailyOverviewFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, overviewFragment)
                        .commit();
                currentDashboardFragment = null;
                return true;
            }

            return false;
        });

        float heightCm = 175f;
        float weightKg = 70f;
        boolean isMale = true;

        stepTracker = new StepTracker(this, heightCm, weightKg, isMale);
        stepTracker.startTracking();
        handler.post(updateRunnable);

        checkAndRequestActivityRecognitionPermission();
    }

    public StepTracker getStepTracker() {
        return stepTracker;
    }

    private void checkAndRequestActivityRecognitionPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        1001);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
