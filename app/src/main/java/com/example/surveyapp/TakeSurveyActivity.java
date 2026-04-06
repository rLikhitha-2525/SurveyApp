package com.example.surveyapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TakeSurveyActivity extends AppCompatActivity {

    TextView textViewQuestion;
    RadioGroup radioGroup;
    Button buttonSubmit;
    DBHelper dbHelper;

    private final ArrayList<Long> questionIds = new ArrayList<>();
    private final ArrayList<String> questionTexts = new ArrayList<>();
    private int currentIndex = 0;

    /** Row id of the question currently on screen. */
    private long currentQuestionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_survey);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        dbHelper = new DBHelper(this);

        loadAllQuestions();
        showQuestionAt(currentIndex);

        buttonSubmit.setOnClickListener(v -> {
            if (currentQuestionId < 0) {
                Toast.makeText(this, "No questions yet. Add some in Create Survey.", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedRadioButton = findViewById(selectedId);
            String answer = selectedRadioButton.getText().toString();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("question_id", currentQuestionId);
            values.put("answer", answer);
            db.insert(DBHelper.TABLE_RESPONSES, null, values);
            Toast.makeText(this, "Answer Saved", Toast.LENGTH_SHORT).show();
            radioGroup.clearCheck();

            currentIndex++;
            if (currentIndex < questionIds.size()) {
                showQuestionAt(currentIndex);
            } else {
                Toast.makeText(this, "Survey complete", Toast.LENGTH_SHORT).show();
                textViewQuestion.setText("All questions answered.");
                buttonSubmit.setEnabled(false);
                currentQuestionId = -1;
            }
        });
    }

    private void loadAllQuestions() {
        questionIds.clear();
        questionTexts.clear();
        currentIndex = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id, question FROM " + DBHelper.TABLE_QUESTIONS + " ORDER BY id ASC",
                null);
        while (cursor.moveToNext()) {
            questionIds.add(cursor.getLong(0));
            questionTexts.add(cursor.getString(1));
        }
        cursor.close();
    }

    private void showQuestionAt(int index) {
        if (questionIds.isEmpty()) {
            currentQuestionId = -1;
            textViewQuestion.setText("Question");
            buttonSubmit.setEnabled(false);
            return;
        }
        buttonSubmit.setEnabled(true);
        currentQuestionId = questionIds.get(index);
        textViewQuestion.setText(questionTexts.get(index));
    }
}