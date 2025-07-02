package com.example.fitstepo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    private ImageView progressFill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressFill = findViewById(R.id.progress_fill);

        // Пример: задать прогресс = 100 пикселей высоты
        setProgressHeight(100);
    }

    // Метод для изменения высоты прогресс-бара
    private void setProgressHeight(int heightPx) {
        ViewGroup.LayoutParams params = progressFill.getLayoutParams();
        params.height = heightPx;
        progressFill.setLayoutParams(params);
    }
}
