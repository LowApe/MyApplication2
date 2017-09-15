package com.didiaoyuan.blog.recycleviewofcrime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Mr.Qu on 2017/9/15.
 */

public class DatePickerFragment extends android.support.v4.app.DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View  view= LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Date_Picker_title")
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }
}
