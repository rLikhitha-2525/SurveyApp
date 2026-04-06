package com.example.surveyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SurveyDB";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_RESPONSES = "responses";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_QUESTIONS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RESPONSES
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, question_id INTEGER, answer TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSES);
        onCreate(db);
    }
}