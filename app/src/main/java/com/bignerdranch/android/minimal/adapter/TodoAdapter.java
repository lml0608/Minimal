package com.bignerdranch.android.minimal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.minimal.R;
import com.bignerdranch.android.minimal.data.TodoItem;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zengzhi on 2017/6/14.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private List<TodoItem> todoItems;

    private Context mContext;

    public TodoAdapter(Context context,List<TodoItem> Items) {
        this.todoItems = Items;
        this.mContext = context;
    }

    @Override
    public TodoAdapter.TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item_view_future, parent, false);
        return new TodoHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TodoAdapter.TodoHolder holder, int position) {

        TodoItem item = todoItems.get(position);
//        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
//                .textColor(Color.WHITE)
//                .useFont(Typeface.DEFAULT)
//                .toUpperCase()
//                .endConfig()
//                .buildRound(item.getmTodoText().substring(0,1),item.getmTodoColor());

//            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
        holder.imageView.setImageResource(R.drawable.check);
        holder.mTitle.setText(item.getmTodoText());
        holder.mDate.setText(item.getmTodoDate().toString());

    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public class TodoHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView mTitle;
        private TextView mDate;

        public TodoHolder(View v) {
            super(v);

            imageView = (ImageView) v.findViewById(R.id.todo_litsitem_image);
            mTitle = (TextView) v.findViewById(R.id.list_item_todo_title);
            mDate = (TextView) v.findViewById(R.id.list_item_todo_time);

        }
    }
}
