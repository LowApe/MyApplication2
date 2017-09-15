package com.didiaoyuan.blog.recycleviewofcrime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Mr.Qu on 2017/9/15.
 */

public class DatePickerFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Date_Picker_title")
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }
}
