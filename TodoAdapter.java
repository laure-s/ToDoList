package com.example.laurescemama.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by laurescemama on 22/03/2017.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private static ArrayList<TodoItem> mTodos;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public TodoAdapter(Context context, ArrayList<TodoItem> todos) {
        mTodos = todos;
        mContext = context.getApplicationContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View todoView = inflater.inflate(R.layout.todo_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(todoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodoAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        TodoItem curTodo = mTodos.get(position); //TODO not sure that's necessary

        // Set item views based on your views and data model
        TextView textView = viewHolder.todoTextView;
        textView.setText(curTodo.getValue());
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    /**
     * Goes through all the todo items and returns the one matching the given string
     * @param text string
     * @return TodoItem matching the given string
     */
    public static TodoItem findTodo(String text){
        for (TodoItem item: mTodos){
            if(item.getValue().compareTo(text) == 0){
                return item;
            }
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView todoTextView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            todoTextView = (TextView) itemView.findViewById(R.id.todo_text);
            todoTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final TodoItem currentItem = TodoAdapter.findTodo(todoTextView.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(todoTextView.getText())
                            .setItems(new String[]{
                                    currentItem.getValue(),
                                    "till: "+ currentItem.getDate().toString()
                                    },
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){}
                            })
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    mTodos.remove(mTodos.indexOf(currentItem));
                                    //TODO: find out how to delete from gui
                                }
                            })
                            .show();
                }
            });
        }

    }

}


//TODO:
// If the todo item start with the word "call", initiate the dialer to the number
//
