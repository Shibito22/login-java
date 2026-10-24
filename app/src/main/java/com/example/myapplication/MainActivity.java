package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Add 24dp (which is roughly 60px depending on density, but let's just use fixed padding in the content)
            // It's better to wrap the content in a ScrollView or just add padding dynamically
            float density = getResources().getDisplayMetrics().density;
            int padding24 = (int) (24 * density);
            v.setPadding(systemBars.left + padding24, systemBars.top + padding24, systemBars.right + padding24, systemBars.bottom + padding24);
            return insets;
        });
    }
}