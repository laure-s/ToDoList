package com.example.laurescemama.todo;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TODO_ARRAY_KEY = "todoArray";
    private static final String EDIT_TEXT_KEY = "editText";
    private RecyclerView rvTodos;
    private TodoAdapter adapter;
    //created for firebaseui android tutorial by Vamsi Tallapudi

    private static final String TODO_ARRAY_KEY = "todoArray";
    private static final String EDIT_TEXT_KEY = "editText";
    private EditText newItemText;
    private FloatingActionButton fab;
    ArrayList<TodoItem> todoArray = new ArrayList<TodoItem>();

    private FirebaseRecyclerAdapter mAdapter;
    private ChildEventListener mChildEventListener;


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TextView todoTextView;

    //Getting reference to Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing our Recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        todoTextView = (TextView) findViewById(R.id.todo_text);
        newItemText = (EditText) findViewById(R.id.enterNew);

        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered linear layout in recyclerview
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<TodoItem, TodoViewHolder>(
                TodoItem.class,
                R.layout.todo_item,
                TodoViewHolder.class,
                //referencing the node where we want the database to store the data from our Object
                mDatabaseReference.child("todos").getRef()
        )
        {
            @Override
            protected void populateViewHolder(final TodoViewHolder viewHolder, TodoItem model, int position) {

                viewHolder.value.setText(model.getValue());
                viewHolder.date.setText(model.getDate());

                String key = this.getRef(position).getKey();
                mDatabaseReference.child("todos").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.child("value").getValue(String.class);
//                        String date = dataSnapshot.child("date").getDate(String.class);

                        ((TextView)viewHolder.itemView.findViewById(R.id.todo_text)).setText(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        mRecyclerView.setAdapter(mAdapter);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
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

        private void createNewTodoItem(String text, View view){

            DatePickerFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");

            TodoItem newTodo = new TodoItem(text, newFragment.getDateStr());
            mDatabaseReference.child("todos").push().setValue(newTodo.toFirebaseObject()); //creates an new one
            todoArray.add(newTodo);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }




    //ViewHolder for our Firebase UI
    public static class TodoViewHolder extends RecyclerView.ViewHolder{

        TextView value;
        TextView date;

        public TodoViewHolder(View v) {
            super(v);
            value = (TextView) v.findViewById(R.id.todo_text);
            date = (TextView) v.findViewById(R.id.todo_date);
        }
    }
}
