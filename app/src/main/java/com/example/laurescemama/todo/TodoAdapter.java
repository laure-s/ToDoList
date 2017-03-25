package com.example.laurescemama.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by laurescemama on 22/03/2017.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<String> mTodos;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public TodoAdapter(Context context, List<String> todos) {
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
        String curTodo = mTodos.get(position); //TODO not sure that's necessary

        // Set item views based on your views and data model
        TextView textView = viewHolder.todoTextView;
        textView.setText(curTodo);
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView todoTextView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            todoTextView = (TextView) itemView.findViewById(R.id.todo_text);
        }

    }


    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

}
