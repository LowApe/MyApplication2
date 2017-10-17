package com.didiaoyuan.blog.recycleviewofcrime;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class CrimeListActivity extends SimpleFragmentActivity implements CrimeListFragment.Callbacks,CrimeFragment.CallBacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
//        return R.layout.activity_twopane;
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.detail_fragment_container)==null){
            Intent intent=CrimePagerActivity.newIntent(this,crime.getId());
            startActivity(intent);
        }else{
            Fragment newDetail=CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container,newDetail)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment crimeListFragment= (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        crimeListFragment.updateUI();
    }
}
