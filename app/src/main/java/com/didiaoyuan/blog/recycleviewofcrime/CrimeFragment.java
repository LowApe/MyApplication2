package com.didiaoyuan.blog.recycleviewofcrime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

public class CrimeFragment extends Fragment {
//    变量声明
    private Crime mCrime;
    private EditText mEditText;
    private Button mDateButton;
    private CheckBox mCheckBox;
    private static int mClickIndex;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime=new Crime();
        UUID crimeID= (UUID) getArguments().getSerializable("UUID");
//        获取CrimeListFragment传入到上下文MainActivity的信息
//        UUID crimeID= (UUID) getActivity().getIntent().getSerializableExtra("Key");
//        通过UUID获取指定的详情视图
        mCrime=CrimeLab.get(getActivity()).getCrime(crimeID);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_crime_fragment,container,false);
//        获取组件
        mEditText=v.findViewById(R.id.content_EditText);
        mDateButton=v.findViewById(R.id.crime_date);
        mCheckBox=v.findViewById(R.id.crime_solve);
//        更新视图
        mEditText.setText(mCrime.getTitle());
        mDateButton.setText(mCrime.getDate().toString());
        mCheckBox.setChecked(mCrime.isSolved());
//        添加监听事件
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//         添加动作
                mCrime.setTitle(charSequence.toString());
                returnResult();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDateButton.setText(mCrime.getDate().toString());
//        mDateButton.setEnabled(false);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });
        return v;
    }
    public static CrimeFragment newInstance(UUID crimeID,int index){
        mClickIndex=index;
        Bundle args=new Bundle();
        args.putSerializable("UUID",crimeID);
        CrimeFragment fragment=new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void returnResult(){
        Intent data=new Intent();
        data.putExtra("index",mClickIndex);
        getActivity().setResult(Activity.RESULT_CANCELED,data);
    }
}
