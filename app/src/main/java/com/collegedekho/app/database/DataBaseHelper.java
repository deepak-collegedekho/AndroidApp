package com.collegedekho.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bashir on 16/2/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "collegedekho_db.db";
    private static DataBaseHelper INSTANCE;
    private String CREATE_EXAMS_TABLE = "CREATE TABLE user_exams IF NOT EXISTS (id INTEGER AUTOINCREMENT PRIMARY KEY,exam_id INTEGER,exam_name TEXT)";
    private String CREATE_EXAMS_SUMMARY="CREATE TABLE exams_summary IF NOT EXISTS (id INTEGER AUTOINCREMENT PRIMARY KEY,yearly_exam_id INTEGER,summary TEXT)";

    public static DataBaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return INSTANCE;
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXAMS_TABLE);
        db.execSQL(CREATE_EXAMS_SUMMARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
