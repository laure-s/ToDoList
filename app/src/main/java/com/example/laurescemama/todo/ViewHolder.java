package com.example.laurescemama.todo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.laurescemama.todo.R;
import com.example.laurescemama.todo.TodoAdapter;
import com.example.laurescemama.todo.TodoItem;

/**
 * Created by laurescemama on 26/04/2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView todoTextView;
//    private TextView todoTimeView;

    public ViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        todoTextView = (TextView) itemView.findViewById(R.id.todo_text);

//        todoTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                final TodoItem currentItem = FirebaseArray.getIndexForKey(todoTextView.getText().toString());
//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle(todoTextView.getText())
//                        .setItems(new String[]{
//                                        currentItem.getValue(),
//                                        "till: " + currentItem.getDate().toString()
//                                },
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                            }
//                        })
//                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //TODO: find out how to delete from gui
//                            }
//                        })
//                        .show();
//            }
//        });
    }

    public void setValue(String todoStr) {
        todoTextView.setText(todoStr);
    }

    public void setDate(String todoDate) {
        todoTextView.setText(todoDate);
    }

}

