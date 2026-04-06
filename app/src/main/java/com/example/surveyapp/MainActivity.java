package com.example.surveyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCreateSurvey, btnTakeSurvey, btnViewSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateSurvey = findViewById(R.id.btnCreateSurvey);
        btnTakeSurvey = findViewById(R.id.btnTakeSurvey);
        btnViewSummary = findViewById(R.id.btnViewSummary);

        btnCreateSurvey.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateSurveyActivity.class)));

        btnTakeSurvey.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TakeSurveyActivity.class)));

        btnViewSummary.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SummaryActivity.class)));
    }
}