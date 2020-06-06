package uz.diyorbek.com.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ToDoListAdapter tAdapter;
    private SQLiteDatabase tDataBase;
    private EditText tdEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToDoListDbHelper dbHelper = new ToDoListDbHelper(this);
        tDataBase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tAdapter = new ToDoListAdapter(this, getAllItems());
        recyclerView.setAdapter(tAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        tdEditText = findViewById(R.id.edt_main);
        final Button addButton = findViewById(R.id.btn_add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Added to your list ;)", Toast.LENGTH_SHORT).show();
                addItem();
            }
        });
    }

    private void addItem() {
        if (tdEditText.getText().toString().trim().length() == 0) {
            return;
        }

        String name = tdEditText.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(ToDoListContract.ToDoListEntry.COLUMN_NAME, name);

        tDataBase.insert(ToDoListContract.ToDoListEntry.TABLE_NAME, null, cv);
        tAdapter.swapCursor(getAllItems());

        tdEditText.getText().clear();
    }

    private void removeItem(long id) {
        tDataBase.delete(ToDoListContract.ToDoListEntry.TABLE_NAME, ToDoListContract.ToDoListEntry._ID + "=" + id, null);
        tAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return tDataBase.query(ToDoListContract.ToDoListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null, ToDoListContract.ToDoListEntry.COLUMN_TIMESTAMP + " DESC");
    }
}
