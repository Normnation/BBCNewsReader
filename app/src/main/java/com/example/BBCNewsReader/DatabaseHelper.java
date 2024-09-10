package com.example.BBCNewsReader;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE = "NEWSDATA";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "DESCRIPTION";
    public static final String COL_4 = "LINK";
    public static final String COL_5 = "PUBLICATION_DATE";
    public static final String DATABASE_NAME = "TheDatabase";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " text,"
                + COL_3 + " text," + COL_4 + " text," + COL_5 + " text);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " text,"
                + COL_3 + " text," + COL_4 + " text," + COL_5 + " text);");
        onCreate(sqLiteDatabase);
    }


}