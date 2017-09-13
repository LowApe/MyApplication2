package com.didiaoyuan.blog.recycleviewofcrime;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mr.Qu on 2017/9/10.
 */

public class CrimeLab {
//    声明变量
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mCrimes=new ArrayList<>();
//        生成一百个Crime
        for(int i=0;i<100;i++){
            Crime crime=new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved( i % 2 ==0);
            mCrimes.add(crime);
        }
    }
    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
