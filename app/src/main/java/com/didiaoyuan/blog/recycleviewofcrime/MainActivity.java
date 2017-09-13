package com.didiaoyuan.blog.recycleviewofcrime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class MainActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID crimeID= (UUID) getIntent().getSerializableExtra("KEY");
        int clickIndex=getIntent().getIntExtra("index",-1);
        return CrimeFragment.newInstance(crimeID,clickIndex);
    }

//    方法
    public static Intent newIntent(Context PackageContext, UUID crimeID,int index){
        Intent i=new Intent(PackageContext,MainActivity.class);
        i.putExtra("KEY",crimeID);
        i.putExtra("index",index);
        return i;
    }
}
