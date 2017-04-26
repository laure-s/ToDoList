package com.example.laurescemama.todo;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TODO_ARRAY_KEY = "todoArray";
    private static final String EDIT_TEXT_KEY = "editText";
    private RecyclerView rvTodos;
    private TodoAdapter adapter;
    private EditText newItemText;
    private FloatingActionButton fab;
    ArrayList<TodoItem> todoArray;

    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference mTodoDBRef;
    private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodos = (RecyclerView) findViewById(R.id.rvList);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        newItemText = (EditText) findViewById(R.id.enterNew);

        mFirebaseDB = FirebaseDatabase.getInstance();
        mTodoDBRef = mFirebaseDB.getReference().child("todos");

        // Initialize todoArray
        adapter = new TodoAdapter(this, todoArray);
        rvTodos.setAdapter(adapter);

        // layout manager setting
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvTodos.setLayoutManager(layoutManager);

        /// adding to list

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = newItemText.getText().toString();
                if (getText != null && getText.trim().length() > 0) {
                    createNewTodoItem(getText, v);
                }
                newItemText.setText("");
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getTodosFromFirebase(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getTodosFromFirebase(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseDB = FirebaseDatabase.getInstance();
        mFirebaseDB.getReference("todos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                todoArray.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    TodoItem todoItem = data.getValue(TodoItem.class);
                    todoArray.add(todoItem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        }


    private void createNewTodoItem(String text, View v){

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

        TodoItem newTodo = new TodoItem(text, newFragment.getDateStr());

//        todoArray.add(newTodo);
        String key = "todos";

        mTodoDBRef.child("todos").push().setValue(newTodo.toFirebaseObject()); //creates an new one
        adapter.notifyItemInserted(todoArray.size() - 1);
        rvTodos.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void getTodosFromFirebase(DataSnapshot dataSnapshot){
        todoArray.clear();
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            TodoItem newItem = singleSnapshot.getValue(TodoItem.class);
            todoArray.add(newItem);
            adapter = new TodoAdapter(this, todoArray);
            rvTodos.setAdapter(adapter);
        }
    }



}