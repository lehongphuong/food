package xmart.com.xmart_android.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xmart.com.xmart_android.db.Categories;
import xmart.com.xmart_android.db.DaoMaster;
import xmart.com.xmart_android.db.DaoSession;

/**
 * Created by LehongphuongCntt on 2/25/2017.
 */

public class CategoriesService {
    public DaoSession daoSession;

    CategoriesService() {

    }

    public CategoriesService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "food", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insert(Categories categories) {
        daoSession.getCategoriesDao().insertOrReplace(categories);
    }

    public void insertList(ArrayList<Categories> list) {
        daoSession.getCategoriesDao().insertInTx(list);
    }

    public void update(Categories categories) {
        daoSession.getCategoriesDao().update(categories);
    }

    public void delete(Categories categories) {
        daoSession.getCategoriesDao().delete(categories);
    }

    public void deleteAll() {
        daoSession.getCategoriesDao().deleteAll();
    }

    public ArrayList<Categories> selectAllCategories() {
        return (ArrayList<Categories>) daoSession.getCategoriesDao().loadAll();
    }


    public boolean isEmpty() {
        return (daoSession.getCategoriesDao().count()) == 0 ? true : false;
    }

    public int getSize() {
        return (int) daoSession.getCategoriesDao().count();
    }
}
