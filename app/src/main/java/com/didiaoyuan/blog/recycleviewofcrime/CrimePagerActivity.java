package com.didiaoyuan.blog.recycleviewofcrime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {
//声明变量
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
//        获取组件
        mViewPager=findViewById(R.id.view_pager);
        mCrimes=CrimeLab.get(this).getCrimes();
        FragmentManager fm=getSupportFragmentManager();/*FragmentStatePagerAdapter需要fragmentManager*/
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime=mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId(),position);
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
    }
//    方法
    public Intent newIntent(Context packagecontext, UUID crimeID){
        Intent i=new Intent(packagecontext,CrimePagerActivity.class);
        i.putExtra("CrimeId",crimeID);
        return i;
    }
}
