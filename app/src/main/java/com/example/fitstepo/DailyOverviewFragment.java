package com.example.fitstepo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DailyOverviewFragment extends Fragment {

    private TextView stepsTextView;
    private TextView caloriesTextView;
    private TextView pulseTextView;

    private Handler handler = new Handler();
    private Runnable updateRunnable;

    public DailyOverviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_daily_overview, container, false);

        TextView dateText = view.findViewById(R.id.date_text);
        TextView dateMonth = view.findViewById(R.id.date_month);
        stepsTextView = view.findViewById(R.id.stepsText);
        caloriesTextView = view.findViewById(R.id.kcalText);
        pulseTextView = view.findViewById(R.id.heartRateText);

        dateText.setText(DateHelper.getFormattedDate());
        dateMonth.setText(DateHelper.getDayAndMonth());

        // Обновляем данные каждые 2 секунды
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                if (getActivity() instanceof MainActivity) {
                    StepTracker stepTracker = ((MainActivity) getActivity()).getStepTracker();
                    if (stepTracker != null) {
                        int steps = stepTracker.getCurrentStepCount();
                        float calories = stepTracker.getCaloriesBurned();
                        float pulse = PulseService.simulatedHeartRate;

                        stepsTextView.setText(String.valueOf(steps));
                        caloriesTextView.setText(String.format("%.1f", calories));
                        pulseTextView.setText(String.format("%.0f", pulse));
                    }
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(updateRunnable);

        ImageView calendarButton = view.findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WorkoutScheduleActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(updateRunnable);
    }
}
