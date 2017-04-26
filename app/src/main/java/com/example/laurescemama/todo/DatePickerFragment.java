package com.example.laurescemama.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import static android.app.TimePickerDialog.*;

/**
 * Created by laurescemama on 28/03/2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    private String dateStr;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);

        // TODO ind a way to transfer through savedInstance the todo item that was picked
        // // in order to set its date

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = view.getYear();
        this.month = view.getMonth()+1;
        this.day = view.getDayOfMonth();
        dateStr =  Integer.toString(this.year) +"/"+ Integer.toString(this.month)
                +"/"+ Integer.toString(this.day);

    }

//
//    public Date getDateFromPicker(){
//        return (new Date(this.year, this.month+1, this.day));
//    }
    public String getDateFromPicker(){
        String dateStr = Integer.toString(this.year) +"/"+ Integer.toString(this.month)
                +"/"+ Integer.toString(this.day);
        return dateStr;
    }
    public String getDateStr(){return this.dateStr;}
}
