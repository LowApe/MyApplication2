package database.CrimeDbSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mr.Qu on 2017/9/18.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper{
//    定义书库版本和名称变量
    private static final int VERSION=1;
    private static final String DATABASE_NAME="crimeBase.db";
    public CrimeBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
