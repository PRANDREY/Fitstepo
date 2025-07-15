package com.example.fitstepo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DailyOverviewFragment extends Fragment {

    public DailyOverviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_daily_overview, container, false);

        TextView dateText = view.findViewById(R.id.date_text);
        TextView dateMonth = view.findViewById(R.id.date_month);
        dateText.setText(DateHelper.getFormattedDate());
        dateMonth.setText(DateHelper.getDayAndMonth());

        ImageView calendarButton = view.findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WorkoutScheduleActivity.class);
            startActivity(intent);
        });

        return view;
    }
}

