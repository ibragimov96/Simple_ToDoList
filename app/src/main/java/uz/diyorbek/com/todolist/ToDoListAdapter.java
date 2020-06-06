package uz.diyorbek.com.todolist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {

    private Context tContext;
    private Cursor tCursor;

    public ToDoListAdapter(Context context, Cursor cursor) {
        tContext = context;
        tCursor = cursor;
    }

    public class ToDoListViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;


        public ToDoListViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textview_name_item);
        }
    }

    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(tContext);
        View view = inflater.inflate(R.layout.todolist_item, parent, false);
        return new ToDoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
        if (!tCursor.moveToPosition(position)) {
            return;
        }
        String name = tCursor.getString(tCursor.getColumnIndex(ToDoListContract.ToDoListEntry.COLUMN_NAME));
        long id = tCursor.getLong(tCursor.getColumnIndex(ToDoListContract.ToDoListEntry._ID));

        holder.nameText.setText(name);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return tCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (tCursor != null) {
            tCursor.close();
        }
        tCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
