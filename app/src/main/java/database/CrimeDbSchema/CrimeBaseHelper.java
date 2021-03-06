package database.CrimeDbSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.CrimeDbSchema.CrimeDbSchema.CrimeTable;

/**
 * Created by Mr.Qu on 2017/9/18.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    //    定义书库版本和名称变量
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override/* onCreate 创建数据库表 */
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        创建数据库并执行数据库创建表的命令
        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME +
                "(" + "_id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT +
                ")"
        );

    }

    @Override /*onUpgrade处理数据库版本问题*/
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
