package cn.jx.jvtc.mytrafficclient.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountOpenHelper extends SQLiteOpenHelper {
    public AccountOpenHelper(Context context) {
        super(context, "recharge.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE record(carNum,reMoney,balance,person,time)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
