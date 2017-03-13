package xmart.com.xmart_android.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;
import xmart.com.xmart_android.db.Process;

public class ProcessService {
    public DaoSession daoSession;

    ProcessService() {

    }

    public ProcessService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(Process process) {
        daoSession.getProcessDao().insertOrReplace(process);
    }

    public void insertList(ArrayList<Process> list) {
        daoSession.getProcessDao().insertInTx(list);
    }

    public void update(Process process) {
        daoSession.getProcessDao().update(process);
    }

    public void delete(Process process) {
        daoSession.getProcessDao().delete(process);
    }

    public void deleteAll() {
        daoSession.getProcessDao().deleteAll();
    }

    public ArrayList<Process> selectAllProcess() {
        return (ArrayList<Process>) daoSession.getProcessDao().loadAll();
    }


    public boolean isEmpty() {
        return (daoSession.getProcessDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getProcessDao().count();
    }
}
