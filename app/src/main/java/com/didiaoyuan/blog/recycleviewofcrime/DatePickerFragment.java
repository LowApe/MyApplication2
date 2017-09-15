package com.didiaoyuan.blog.recycleviewofcrime;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mr.Qu on 2017/9/15.
 */

public class DatePickerFragment extends android.support.v4.app.DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date= (Date) getArguments().getSerializable("date");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        View  view= LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
        DatePicker datePicker=view.findViewById(R.id.date_picker);
        datePicker.init(year,month,day,null);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Date_Picker_title")
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }
    public static DatePickerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable("date",date);
        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
