package com.didiaoyuan.blog.recycleviewofcrime;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mr.Qu on 2017/9/15.
 */

public class DatePickerFragment extends android.support.v4.app.DialogFragment{
    private DatePicker mDatePicker;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        获取bundle中日期数据
        Date date= (Date) getArguments().getSerializable("date");
//        实例化Calendar对象
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
//        对话框view对象
        View  view= LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
//        初始化 DatePicker 组件
        mDatePicker=view.findViewById(R.id.date_picker);
        mDatePicker.init(year,month,day,null);
//        AlertDialog.Builder 创建对话框
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Date_Picker_title")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year=mDatePicker.getYear();
                        int month=mDatePicker.getMonth();
                        int day=mDatePicker.getDayOfMonth();
                        Date date=new GregorianCalendar(year,month,day).getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                })
                .create();
    }
//    返回添加 Bundle 对象的Fragment
    public static DatePickerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable("date",date);
        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void sendResult(int resultCode,Date date){
        if(getTargetFragment()==null){
            return;
        }
        Intent i=new Intent();
        i.putExtra("Date",date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);
    }
}
