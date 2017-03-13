package xmart.com.xmart_android.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.Cancel;
import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;


public class CancelService {
    public DaoSession daoSession;

    CancelService() {

    }

    public CancelService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(Cancel cancel) {
        daoSession.getCancelDao().insertOrReplace(cancel);
    }

    public void insertList(ArrayList<Cancel> list) {
        daoSession.getCancelDao().insertInTx(list);
    }

    public void update(Cancel cancel) {
        daoSession.getCancelDao().update(cancel);
    }

    public void delete(Cancel cancel) {
        daoSession.getCancelDao().delete(cancel);
    }

    public void deleteAll() {
        daoSession.getCancelDao().deleteAll();
    }

    public ArrayList<Cancel> selectAllCancel() {
        return (ArrayList<Cancel>) daoSession.getCancelDao().loadAll();
    }


    public boolean isEmpty() {
        return (daoSession.getCancelDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getCancelDao().count();
    }
}
