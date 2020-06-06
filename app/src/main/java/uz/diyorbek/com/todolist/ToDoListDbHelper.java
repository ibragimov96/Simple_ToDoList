package uz.diyorbek.com.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uz.diyorbek.com.todolist.ToDoListContract.*;

import androidx.annotation.Nullable;

public class ToDoListDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todolist.db";
    public static final int DATABASE_VERSION = 2;

    public ToDoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODOLIST_TABLE = "CREATE TABLE " + ToDoListEntry.TABLE_NAME +
                " (" + ToDoListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ToDoListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_TODOLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ToDoListEntry.TABLE_NAME);
        onCreate(db);
    }
}
