package com.didiaoyuan.blog.recycleviewofcrime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.CallBacks{
    //声明变量
    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        加载ViewPager布局
        setContentView(R.layout.activity_crime_pager);
//        获取组件
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();/*FragmentStatePagerAdapter需要fragmentManager*/
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
//                获取模型层列表的位置
                Crime crime = mCrimes.get(position);
//              生成一个拥有特定位置的Fragment
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
//        通过匹配Intent的ID与列表相匹配，进入当前id页面
        UUID crimeID = (UUID) getIntent().getSerializableExtra("CrimeId");
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    //    方法
    public static Intent newIntent(Context packagecontext, UUID crimeID) {
        Intent i = new Intent(packagecontext, CrimePagerActivity.class);
        i.putExtra("CrimeId", crimeID);
//        i.putExtra("index",index);
        return i;
    }

    //    public static Intent newIntent(Context packagecontext, UUID crimeID){
//        Intent i=new Intent(packagecontext,CrimePagerActivity.class);
//        i.putExtra("CrimeId",crimeID);
//        return i;
//    }
//返回按钮不重置数据，方法1
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
}
