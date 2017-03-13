package xmart.com.xmart_android.service;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;
import xmart.com.xmart_android.db.Info;

public class InfoService {
    public DaoSession daoSession;

    InfoService() {

    }

    public InfoService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(Info info) {
        daoSession.getInfoDao().insertOrReplace(info);
    }

    public void insertList(ArrayList<Info> list) {
        daoSession.getInfoDao().insertInTx(list);
    }

    public void update(Info info) {
        daoSession.getInfoDao().update(info);
    }

    public void delete(Info info) {
        daoSession.getInfoDao().delete(info);
    }

    public void deleteAll() {
        daoSession.getInfoDao().deleteAll();
    }

    public ArrayList<Info> selectAllInfo() {
        return (ArrayList<Info>) daoSession.getInfoDao().loadAll();
    }


    public boolean isEmpty() {
        return (daoSession.getInfoDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getInfoDao().count();
    }
}
