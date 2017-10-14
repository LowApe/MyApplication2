package com.didiaoyuan.blog.recycleviewofcrime;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Mr.Qu on 2017/10/14.
 */

public class ImageFragment extends DialogFragment{
    private View v;

    public  ImageFragment(View v) {
        this.v = v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("确定",null)
                .create();
    }
}
