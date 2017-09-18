package com.didiaoyuan.blog.recycleviewofcrime;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.Date;
import java.util.UUID;

import database.CrimeDbSchema.CrimeDbSchema;
import database.CrimeDbSchema.CrimeDbSchema.CrimeTable;

/**
 * Created by Mr.Qu on 2017/9/18.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //    返回单个Crime对象
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        return crime;
    }
}
