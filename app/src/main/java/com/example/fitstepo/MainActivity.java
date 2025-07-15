package com.example.fitstepo;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ImageView progressFill;
    private ImageView calendarButton;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressFill = findViewById(R.id.progress_fill);
        calendarButton = findViewById(R.id.calendarButton);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(DateHelper.getFormattedDate());

        setProgressHeight(100);

        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WorkoutScheduleActivity.class);
            startActivity(intent);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_medal) {
                Fragment dashboardFragment = new DashboardFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, dashboardFragment)
                        .commit();
                return true;

            } else if (itemId == R.id.nav_home) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .remove(fragment)
                            .commit();
                }
                return true;

            } else if (itemId == R.id.nav_heart) {
                Fragment overviewFragment = new DailyOverviewFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, overviewFragment)
                        .commit();
                return true;
            }

            return false;
        });
    }

    private void setProgressHeight(int heightPx) {
        ViewGroup.LayoutParams params = progressFill.getLayoutParams();
        params.height = heightPx;
        progressFill.setLayoutParams(params);
    }
}
