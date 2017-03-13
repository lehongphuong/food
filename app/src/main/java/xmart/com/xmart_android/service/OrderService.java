package xmart.com.xmart_android.service;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;
import xmart.com.xmart_android.db.Order;

public class OrderService {
    public DaoSession daoSession;

    OrderService() {

    }

    public OrderService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(Order order) {
        daoSession.getOrderDao().insertOrReplace(order);
    }

    public void insertList(ArrayList<Order> list) {
        daoSession.getOrderDao().insertInTx(list);
    }

    public void update(Order order) {
        daoSession.getOrderDao().update(order);
    }

    public void delete(Order order) {
        daoSession.getOrderDao().delete(order);
    }

    public void deleteAll() {
        daoSession.getOrderDao().deleteAll();
    }

    public ArrayList<Order> selectAllOrder() {
        return (ArrayList<Order>) daoSession.getOrderDao().loadAll();
    }



    public boolean isEmpty() {
        return (daoSession.getOrderDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getOrderDao().count();
    }
}
