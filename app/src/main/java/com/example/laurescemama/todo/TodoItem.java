package com.example.laurescemama.todo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by laurescemama on 28/03/2017.
 * An object representing a todo item in the list.
 */

public class TodoItem implements Serializable {

    private String value;
    private String date;

    TodoItem(){} // default constructor fr firebase calls

//    TodoItem(String value){
//        this.value = value;
//    }

    TodoItem(String value, String date){
        this.value = value;
        this.date = date;
    }

    public String getValue(){
        return this.value;
    }

    public String getDate(){
        return this.date;
    }

    public void setValue(String value){
        this.value = value;
    }

    public void setDate(String date){
         this.date = date;
    }

    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> todo =  new HashMap<String,String>();
        todo.put("value", value);
        todo.put("date", date);

        return todo;
    }
}
