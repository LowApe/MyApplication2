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
//        获取bundle中日期数据
        Date date= (Date) getArguments().getSerializable("date");
//        实例化Calendar对象
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
//        对话框view对象
        View  view= LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
//        初始化 DatePicker 组件
        DatePicker datePicker=view.findViewById(R.id.date_picker);
        datePicker.init(year,month,day,null);
//        AlertDialog.Builder 创建对话框
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Date_Picker_title")
                .setPositiveButton(android.R.string.ok,null)
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
}
