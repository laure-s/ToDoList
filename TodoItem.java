package com.example.laurescemama.todo;

import java.util.Date;

/**
 * Created by laurescemama on 28/03/2017.
 * An object representing a todo item in the list.
 */

public class TodoItem {

    private String value;
    private Date date;

    TodoItem(String value){
        this.value = value;
    }

    TodoItem(String value, Date date){
        this.value = value;
        this.date = date;
    }

    public String getValue(){
        return this.value;
    }

    public Date getDate(){
        return this.date;
    }



}
