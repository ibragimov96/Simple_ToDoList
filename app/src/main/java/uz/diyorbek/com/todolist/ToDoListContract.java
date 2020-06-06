package uz.diyorbek.com.todolist;

import android.provider.BaseColumns;

public class ToDoListContract {

    public static final class ToDoListEntry implements BaseColumns {

        public static final String TABLE_NAME = "todoList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
