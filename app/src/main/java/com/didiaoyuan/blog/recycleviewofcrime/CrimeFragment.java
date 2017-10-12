package com.didiaoyuan.blog.recycleviewofcrime;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    //    变量声明
    private Crime mCrime;
    private EditText mEditText;
    private Button mDateButton;
    private CheckBox mCheckBox;
    private Button mSuspect;
    private Button mSendMsg;
    private ImageButton mImageButton;
    private ImageView mImageView;
    private static int mClickIndex;
    private static final int RELATIVE_REQUEST_CODE = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
        UUID crimeID = (UUID) getArguments().getSerializable("UUID");
//        获取CrimeListFragment传入到上下文MainActivity的信息
//        UUID crimeID= (UUID) getActivity().getIntent().getSerializableExtra("Key");
//        通过UUID获取指定的详情视图
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeID);

    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity())
                .updateCrime(mCrime);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_crime_fragment, container, false);
//        获取组件
        mEditText = v.findViewById(R.id.content_EditText);
        mDateButton = v.findViewById(R.id.crime_date);
        mCheckBox = v.findViewById(R.id.crime_solve);
        mSuspect = v.findViewById(R.id.choose_suspect);
        mSendMsg = v.findViewById(R.id.send_report);
        mImageButton=v.findViewById(R.id.use_camera);
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
        mDateButton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                returnResult();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDateButton.setText(mCrime.getDate().toString());
//        mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Date date = mCrime.getDate();
                DatePickerFragment dialog = DatePickerFragment.newInstance(date);
//                添加关联
                dialog.setTargetFragment(CrimeFragment.this, RELATIVE_REQUEST_CODE);
                dialog.show(fm, "dialog");
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
                returnResult();
            }
        });
        /*发送短信的监听事件*/
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                /*添加正文内容*/
//                i.putExtra(Intent.EXTRA_TEXT, "TEXT");
//                i.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
//                i = Intent.createChooser(i, "选择你想使用的");
//                startActivity(i);
                /*利用 ShareCompat 类 发送消息*/
                ShareCompat.IntentBuilder intentBuilder=ShareCompat.IntentBuilder.from(getActivity());
                intentBuilder.setType("text/plain");
                intentBuilder.setText("text");
                intentBuilder.setSubject("what");
                intentBuilder.setChooserTitle("select");
                intentBuilder.startChooser();
            }
        });
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
       /* *//*给 Intent 添加类别，作用是不让联系人应用与你的 intent 匹配*//*
        pickContact.addCategory(Intent.CATEGORY_HOME);*/
        /*利用 packageManage 进行自检，如果没有匹配到就做处理，防止应用崩溃*/
        PackageManager packageManager=getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY)==null){
            mSuspect.setEnabled(false);
        }
        /*联系人的监听事件*/
        mSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(pickContact, 1);
            }
        });
        if (mCrime.getPeopleName() != null) {
            mSuspect.setText(mCrime.getPeopleName());
        }
        /**/
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,2);
            }
        });
        return v;

    }

    /*
    * 返回一个带参数的Fragment
    * */
    public static CrimeFragment newInstance(UUID crimeID, int index) {
        mClickIndex = index;
        Bundle args = new Bundle();
        args.putSerializable("UUID", crimeID);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*
    * Fragment不能设置返回结果。通过该方法设置返回结果
    * */
    public void returnResult() {
        Intent data = new Intent();
        data.putExtra("index", mClickIndex);
        getActivity().setResult(Activity.RESULT_CANCELED, data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*该 Intent 包含 Uri 数据 ，它是数据定位符，指向用户所选联系人*/
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == RELATIVE_REQUEST_CODE) {
            Date date = (Date) data.getSerializableExtra("Date");
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getDate().toString());
        } else if (requestCode == 1 && data != null){
            /*创建一个Uri 数据地址*/
            Uri contactUri=data.getData();
            /*创建查询语句*/
            String[] queryFields=new String[]{
//                    ContactsContract.Contacts.DISPLAY_NAME
                    ContactsContract.Contacts._ID
            };
            /*查询联系人数据库，并返回一个 cursor*/
            Cursor c=getActivity().getContentResolver().query(contactUri,queryFields,null,null,null);

            try {
                c.moveToFirst();
            /*判断返回结果是否为0 表示没有数据*/
                if(c.getCount()==0){
                    return;
                }
                /*String suspect=c.getString(0);
                mCrime.setPeopleName(suspect);
                mSuspect.setText(suspect);*/
                Uri number=Uri.parse("tel:"+c.getString(0));
                Intent i=new Intent(Intent.ACTION_DIAL,number);
                startActivity(i);


            }finally {
                c.close();
            }
        }
    }
}
