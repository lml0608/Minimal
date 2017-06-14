package com.bignerdranch.android.minimal;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bignerdranch.android.minimal.adapter.TodoAdapter;
import com.bignerdranch.android.minimal.data.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<TodoItem> items;

    public List<TodoItem> setData(){

        List<TodoItem> items = new ArrayList<>();

        for(int i = 0; i < 20; i++ ) {

            TodoItem item = new TodoItem();

            items.add(item);

        }

        return items;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        items = setData();

        for (TodoItem item : items) {
            Log.i(TAG, String.valueOf(item));
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout mCoordLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);

        FloatingActionButton mAddFAB  = (FloatingActionButton) findViewById(R.id.add_todo_item_fab);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.todo_recycler_view);

//        mRecyclerView.set(findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TodoAdapter adapter = new TodoAdapter(this, items);

        mRecyclerView.setAdapter(adapter);


    }
}
