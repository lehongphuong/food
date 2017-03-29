package xmart.com.xmart_android.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;
import xmart.com.xmart_android.db.OrderOwner;

public class OrderOwnerService {
    public DaoSession daoSession;

    OrderOwnerService() {

    }

    public OrderOwnerService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(OrderOwner orderOwner) {
        daoSession.getOrderOwnerDao().insertOrReplace(orderOwner);
    }

    public void insertList(ArrayList<OrderOwner> list) {
        daoSession.getOrderOwnerDao().insertInTx(list);
    }

    public void update(OrderOwner orderOwner) {
        daoSession.getOrderOwnerDao().update(orderOwner);
    }

    public void delete(OrderOwner orderOwner) {
        daoSession.getOrderOwnerDao().delete(orderOwner);
    }

    public void deleteAll() {
        daoSession.getOrderOwnerDao().deleteAll();
    }

    public ArrayList<OrderOwner> selectAllOrderOwner() {
        return (ArrayList<OrderOwner>) daoSession.getOrderOwnerDao().loadAll();
    }



    public boolean isEmpty() {
        return (daoSession.getOrderOwnerDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getOrderOwnerDao().count();
    }
}
