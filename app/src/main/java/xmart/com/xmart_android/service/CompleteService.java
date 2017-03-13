package xmart.com.xmart_android.service;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.Complete;
import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;

public class CompleteService {
    public DaoSession daoSession;

    CompleteService() {

    }

    public CompleteService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(Complete complete) {
        daoSession.getCompleteDao().insertOrReplace(complete);
    }

    public void insertList(ArrayList<Complete> list) {
        daoSession.getCompleteDao().insertInTx(list);
    }

    public void update(Complete complete) {
        daoSession.getCompleteDao().update(complete);
    }

    public void delete(Complete complete) {
        daoSession.getCompleteDao().delete(complete);
    }

    public void deleteAll() {
        daoSession.getCompleteDao().deleteAll();
    }

    public ArrayList<Complete> selectAllComplete() {
        return (ArrayList<Complete>) daoSession.getCompleteDao().loadAll();
    }


    public boolean isEmpty() {
        return (daoSession.getCompleteDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getCompleteDao().count();
    }
}
