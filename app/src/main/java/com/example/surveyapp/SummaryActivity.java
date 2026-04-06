package com.example.surveyapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    TextView textViewSummary;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textViewSummary = findViewById(R.id.textViewSummary);
        dbHelper = new DBHelper(this);
        displaySummary();
    }

    private void displaySummary() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT q.question, r.answer, COUNT(*) "
                + "FROM " + DBHelper.TABLE_RESPONSES + " r "
                + "JOIN " + DBHelper.TABLE_QUESTIONS + " q ON r.question_id = q.id "
                + "GROUP BY q.question, r.answer "
                + "ORDER BY q.id, r.answer";
        Cursor cursor = db.rawQuery(sql, null);

        StringBuilder result = new StringBuilder();
        String lastQuestion = null;
        while (cursor.moveToNext()) {
            String question = cursor.getString(0);
            if (lastQuestion == null || !question.equals(lastQuestion)) {
                if (lastQuestion != null) {
                    result.append("\n");
                }
                result.append("Q: ").append(question).append("\n");
                lastQuestion = question;
            }
            result.append(cursor.getString(1))
                    .append(" : ")
                    .append(cursor.getInt(2))
                    .append("\n");
        }
        cursor.close();

        if (result.length() == 0) {
            result.append("No responses yet.");
        }
        textViewSummary.setText(result.toString());
    }
}