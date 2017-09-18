package com.didiaoyuan.blog.recycleviewofcrime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CrimeDbSchema.CrimeBaseHelper;
import database.CrimeDbSchema.CrimeDbSchema;
import database.CrimeDbSchema.CrimeDbSchema.CrimeTable;

/**
 * Created by Mr.Qu on 2017/9/10.
 */

public class CrimeLab {
    //    声明变量
    private static CrimeLab sCrimeLab;
    //    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
//        mCrimes=new ArrayList<>();
/*//        随机生成一百个Crime
        for(int i=0;i<100;i++){
            Crime crime=new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved( i % 2 ==0);
            mCrimes.add(crime);
        }*/
        mContext = context.getApplicationContext();
//        写入数据库变量
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();

    }

    public List<Crime> getCrimes() {
//        return mCrimes;
                List<Crime> crimes=new ArrayList<>();
//
        CrimeCursorWrapper cursor=queryCrimes(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
//        for(Crime crime:mCrimes){
//            if(crime.getId().equals(id)){
//                return crime;
//            }
//        }
        CrimeCursorWrapper cursor=queryCrimes(
                CrimeTable.Cols.UUID+"= ?",
                new String[]{id.toString()});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }

    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

    public void addCrime(Crime c) {
//      mCrimes.add(c);
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + "=?", new String[]{uuidString});
    }
    private CrimeCursorWrapper queryCrimes(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }
}
