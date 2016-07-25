package com.collegedekho.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bashir on 16/2/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "collegedekho_db.db";
    private static DataBaseHelper DB_INSTANCE;
    private String CREATE_EXAMS_TABLE = "CREATE TABLE IF NOT EXISTS user_exams(id INTEGER AUTO INCREMENT PRIMARY KEY,exam_id INTEGER,exam_data TEXT)";
    private String CREATE_EXAMS_SUMMARY = "CREATE TABLE IF NOT EXISTS exams_summary(id INTEGER AUTO INCREMENT PRIMARY KEY,yearly_exam_id INTEGER,summary TEXT)";
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS mDeviceProfile(id INTEGER AUTO INCREMENT PRIMARY KEY,user_id INTEGER,user_data TEXT)";

    public static DataBaseHelper getInstance(Context context) {
        if (DB_INSTANCE == null) {
            DB_INSTANCE = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return DB_INSTANCE;
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_EXAMS_TABLE);
        db.execSQL(CREATE_EXAMS_SUMMARY);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addExamSummary(int yearlyExamId, String summary) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String insertQuery = "INSERT INTO exams_summary (yearly_exam_id,summary) VALUES (" + yearlyExamId + ",'" + summary + "')";
            db.execSQL(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    public void updateExamSummary(int yearlyExamId, String summary) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String updateQuery = "UPDATE exams_summary SET summary= '" + summary + "' WHERE yearly_exam_id = " + yearlyExamId;
            db.execSQL(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    public String getExamSummary(int yearlyExamId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String examSummary = null;
        try {
            String getQuery = "SELECT * FROM exams_summary WHERE yearly_exam_id = " + yearlyExamId;
            Cursor cursor = db.rawQuery(getQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        examSummary = cursor.getString(cursor.getColumnIndex("summary"));
//                    examSummary = JSON.std.beanFrom(ExamSummary.class, summary);
//                    Log.e("DEBUG", "Exam Summary from DB " + examSummary);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
        return examSummary;
    }

    public boolean isExamSummaryExists(int yearlyExamId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isExists = false;
        try {
            String getQuery = "SELECT * FROM exams_summary WHERE yearly_exam_id = " + yearlyExamId;
            Cursor cursor = db.rawQuery(getQuery, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    isExists = true;
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
        return isExists;
    }

    public void deleteExamSummary(int yearlyExamId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String updateQuery = "DELETE FROM exams_summary WHERE yearly_exam_id = " + yearlyExamId;
            db.execSQL(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    public void deleteAllExamSummary() {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String updateQuery = "DELETE FROM exams_summary";
            db.execSQL(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    private void addUser(int userId, String userData) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String insertQuery = "INSERT INTO mDeviceProfile (user_id,user_data) VALUES (" + userId + ",'" + userData + "')";
            db.execSQL(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    public void addUser(String userData) {
        this.addUser(1, userData);
    }

    private String getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String user = null;
        try {
            String getQuery = "SELECT * FROM mDeviceProfile WHERE user_id = " + userId;
            Cursor cursor = db.rawQuery(getQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        user = cursor.getString(cursor.getColumnIndex("user_data"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
        return user;
    }

    public String getUser() {
        return this.getUser(1);
    }

    private void updateUser(int userId, String userData) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String updateQuery = "UPDATE mDeviceProfile SET user_data= '" + userData + "' WHERE user_id = " + userId;
            db.execSQL(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void updateUser(String userData) {
        this.updateUser(1, userData);
    }

    private void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String updateQuery = "DELETE FROM mDeviceProfile WHERE user_id = " + userId;
            db.execSQL(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    public void deleteUser() {
        this.deleteUser(1);
    }
}
