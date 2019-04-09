package cn.jx.jvtc.mytrafficclient.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jx.jvtc.mytrafficclient.bean.RecordBean;

public class AccountDAO {
    private AccountOpenHelper openHelper;

    public AccountDAO(AccountOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    public long add(RecordBean recordBean) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("carNum", recordBean.getCarNum());
        contentValues.put("reMoney", recordBean.getReMoney());
        contentValues.put("balance", recordBean.getBalance());
        contentValues.put("person", recordBean.getPerson());
        contentValues.put("time", recordBean.getReTime());
        long record = db.insert("record", null, contentValues);
        return record;
    }

    public List<RecordBean> query(String username) {
        List<RecordBean> rList = new ArrayList<>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("record", null, "person=?", new String[]{username}, null, null, null);
        while (cursor.moveToNext()) {
            String carNum = cursor.getString(cursor.getColumnIndex("carNum"));
            String reMoney = cursor.getString(cursor.getColumnIndex("reMoney"));
            String balance = cursor.getString(cursor.getColumnIndex("balance"));
            String person = cursor.getString(cursor.getColumnIndex("person"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date(time));
            String week = new SimpleDateFormat("EEEE").format(new Date(time));
            String reTime = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date(time));
            rList.add(new RecordBean(date,week,person,carNum,reMoney,balance,reTime));
        }
        return rList;
    }
    public int allMoney(String username){
        int money = 0;
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("record", null, "person=?", new String[]{username}, null, null, null);
        while (cursor.moveToNext()){
            String reMoney = cursor.getString(cursor.getColumnIndex("reMoney"));
            money = money + Integer.parseInt(reMoney);
        }
        return money;
    }
}
