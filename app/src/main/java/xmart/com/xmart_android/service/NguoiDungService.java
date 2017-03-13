package xmart.com.xmart_android.service;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;
import xmart.com.xmart_android.db.NguoiDung;

public class NguoiDungService {
    public DaoSession daoSession;

    NguoiDungService() {

    }

    public NguoiDungService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(NguoiDung nguoiDung) {
        daoSession.getNguoiDungDao().insertOrReplace(nguoiDung);
    }

    public void insertList(ArrayList<NguoiDung> list) {
        daoSession.getNguoiDungDao().insertInTx(list);
    }

    public void update(NguoiDung nguoiDung) {
        daoSession.getNguoiDungDao().update(nguoiDung);
    }

    public void delete(NguoiDung nguoiDung) {
        daoSession.getNguoiDungDao().delete(nguoiDung);
    }

    public void deleteAll() {
        daoSession.getNguoiDungDao().deleteAll();
    }

    public ArrayList<NguoiDung> selectAllNguoiDung() {
        return (ArrayList<NguoiDung>) daoSession.getNguoiDungDao().loadAll();
    }


    public boolean isEmpty() {
        return (daoSession.getNguoiDungDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getNguoiDungDao().count();
    }

}
