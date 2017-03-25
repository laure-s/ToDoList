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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTodos;
    private TodoAdapter adapter;
    private EditText newItemText;
    private FloatingActionButton fab;
    //    private ListView listView;
    //    private ArrayAdapter<String> adapter;
    ArrayList<String> todoArray;
    private boolean paintRed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        rvTodos = (RecyclerView) findViewById(R.id.rvList);

        // Initialize todoArray
        todoArray = new ArrayList<String>();
        // Create adapter passing in the sample user data
        adapter = new TodoAdapter(this, todoArray);
        // Attach the adapter to the recyclerview to populate items
        rvTodos.setAdapter(adapter);
        // layout manager setting
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        rvTodos.setLayoutManager(layoutManager);


        /// adding to list
        newItemText = (EditText)findViewById(R.id.enterNew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = newItemText.getText().toString();
                if (getText != null && getText.trim().length()>0){
                    todoArray.add((getText));

                    adapter.notifyItemInserted(todoArray.size() - 1);
                    rvTodos.scrollToPosition(adapter.getItemCount() - 1);
                }
                newItemText.setText("");
            }
        });


//        /// dailog
//        TextView textView = (TextView) findViewById(R.id.todo_text);
//        textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setMessage(getString(R.string.dialog_text))
//                        .setPositiveButton(
//                                R.string.delete,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(
//                                            DialogInterface dialog,
//                                            int which) {
//                                        dialog.cancel();
//                                    }
//                                }).show();
//                return false;
//            }
//        });



    }
}
