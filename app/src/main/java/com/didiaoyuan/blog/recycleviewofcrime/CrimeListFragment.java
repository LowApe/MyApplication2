package com.didiaoyuan.blog.recycleviewofcrime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    //      声明变量
    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private int mIndexNotific;
    //    private static final int INTENT_REQUEST_CODE=1;
    private boolean mSubtitleVisible;
    //    创建一个回调接口变量
    private Callbacks mCallbacks;

    //    创建一个回调接口
    public interface Callbacks {
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        获取列表View对象并返回生成视图
        View v = inflater.inflate(R.layout.activity_crime_list_fragment, container, false);
//        获取组件
        mRecyclerView = v.findViewById(R.id.fragment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean("count");
        }
        updateUI();
        return v;
    }

    //    更新数据与视图方法
    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.setCrimes(crimes);
//            索引减1
//            Log.e("update",mIndexNotific+"");
            mCrimeAdapter.notifyItemChanged(mIndexNotific);
            updateSubtitle();
        }
    }

    //     ViewHolder内部类，生成itemView，并通过bindCrime()绑定数据，并进行点击事件
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Crime mCrime;
        private TextView mTitleTextView;
        private CheckBox mCheckBox;
        private TextView mDateTextView;

        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.list_title);
            itemView.setOnClickListener(this);
            mCheckBox = itemView.findViewById(R.id.list_solve);
            mDateTextView = itemView.findViewById(R.id.list_date);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mCheckBox.setChecked(mCrime.isSolved());
            mDateTextView.setText(mCrime.getDate().toString());
//给checkbox添加点击事件
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mCrime.setSolved(b);
                }
            });
        }
/*
* 在Fragment中获取上上下文用getActivity
* */

        @Override
        public void onClick(View view) {
//        Toast.makeText(getActivity(),"点击了"+mCrime.getTitle(),Toast.LENGTH_LONG).show();
            mIndexNotific = mRecyclerView.getChildAdapterPosition(view);
//        Intent i=MainActivity.newIntent(getActivity(),mCrime.getId(),mIndexNotific);
//        Log.e("click",mIndexNotific+"");
//            Intent i = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//            startActivity(i);
            mCallbacks.onCrimeSelected(mCrime);
        }
    }


    //      Adapter的内部类，进行获取数据，并将数据绑定到ViewHolder
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        /*
        * adapter 获取模型数据，并利用ViewHolder填充视图
        *
        * */
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            创建一个每一个ItemView的View对象
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
/*
* 出现的问题：
* 在这三个参数中设置的是全局变量，所以在展示的过程中 会变化
* */
//            holder.mTitleTextView.setText(crime.getTitle());
//            holder.mCheckBox.setChecked(crime.isSolved());
//            holder.mDateTextView.setText(crime.getDate().toString());
            holder.bindCrime(crime);

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }
    }

    /*
    * 当返回此页面，更新视图
    * */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
//                Intent i = CrimePagerActivity.newIntent(getActivity()
//                        , crime.getId());
//                startActivity(i);
                updateUI();
                mCallbacks.onCrimeSelected(crime);
                return true;
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime();
                updateUI2();
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*删除操作后，重新加载一遍数据库的数据*/
    private void updateUI2() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mCrimeAdapter = new CrimeAdapter(crimes);
        mRecyclerView.setAdapter(mCrimeAdapter);

        mCrimeAdapter.setCrimes(crimes);
        mCrimeAdapter.notifyDataSetChanged();
        updateSubtitle();
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
//        替换站位符
        String subtitle = getString(R.string.subtitle_format, crimeCount);
//        强制转换为AppCompat
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("count", mSubtitleVisible);
    }
}
