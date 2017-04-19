package com.example.laurescemama.todo;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TODO_ARRAY_KEY = "todoArray";
    private static final String EDIT_TEXT_KEY = "editText";
    private RecyclerView rvTodos;
    private TodoAdapter adapter;
    private EditText newItemText;
    private FloatingActionButton fab;
    //    private ListView listView;
    //    private ArrayAdapter<String> adapter;
    ArrayList<TodoItem> todoArray;
    private boolean paintRed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodos = (RecyclerView) findViewById(R.id.rvList);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        newItemText = (EditText) findViewById(R.id.enterNew);

        if (savedInstanceState != null) {
            todoArray = (ArrayList<TodoItem>) savedInstanceState.getSerializable(TODO_ARRAY_KEY);
            if (todoArray == null) {
                todoArray = new ArrayList<TodoItem>();
            }
            String pastEditText = savedInstanceState.getString(EDIT_TEXT_KEY);
            if (pastEditText != null ||
                    pastEditText.trim().length() > 0) {
                newItemText.setText(pastEditText);
            }
        } else{
            todoArray = new ArrayList<TodoItem>();
        }

        // Initialize todoArray
        adapter = new TodoAdapter(this, todoArray);
        rvTodos.setAdapter(adapter);

        // layout manager setting
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvTodos.setLayoutManager(layoutManager);



        /// adding to list

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = newItemText.getText().toString();
                if (getText != null && getText.trim().length() > 0) {

                    todoArray.add(new TodoItem(getText, showDatePickerDialog(v)));
                    adapter.notifyItemInserted(todoArray.size() - 1);
                    rvTodos.scrollToPosition(adapter.getItemCount() - 1);
                }
                newItemText.setText("");
            }
        });


//                /// dailog
//        TextView textView = (TextView) findViewById(R.id.todo_text);
//        textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showDatePickerDialog(v);
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putSerializable(TODO_ARRAY_KEY, todoArray);
        outState.putString(EDIT_TEXT_KEY, newItemText.getText().toString());
    }



    public Date showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
        return newFragment.getDate();
    }


}
