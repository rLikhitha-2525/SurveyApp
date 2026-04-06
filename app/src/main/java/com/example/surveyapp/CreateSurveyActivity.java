package com.example.surveyapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateSurveyActivity extends AppCompatActivity {

    EditText editTextOption;
    Button buttonAddOption;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        editTextOption = findViewById(R.id.editTextOption);
        buttonAddOption = findViewById(R.id.buttonAddOption);
        dbHelper = new DBHelper(this);

        buttonAddOption.setOnClickListener(v -> {
            String question = editTextOption.getText().toString().trim();
            if (question.isEmpty()) {
                Toast.makeText(this, "Enter a question", Toast.LENGTH_SHORT).show();
                return;
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("question", question);
            db.insert(DBHelper.TABLE_QUESTIONS, null, values);
            Toast.makeText(this, "Question Added", Toast.LENGTH_SHORT).show();
            editTextOption.setText("");
        });
    }
}