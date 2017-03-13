package xmart.com.xmart_android.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LehongphuongCntt on 9/13/2016.
 */
public class Database extends SQLiteOpenHelper {

    public Database(Context context, String nameDatabase) {
        super(context, nameDatabase, null, 1);
//        Database database = new Database(context, "setting.sqlite");
//        Database database1 = new Database(context, "subject.sqlite");
//        Database database2 = new Database(context, "setting.sqlite");

//        queryData("CREATE TABLE subject (ID INTEGER PRIMARY KEY,IMAGE NVARCHAR(50),NAME NVARCHAR(50))");
//        queryData("CREATE TABLE lesson (ID INTEGER PRIMARY KEY,idsub VARCHAR(20),english NVARCHAR(500),vietnam NVARCHAR(500))");
//        queryData("CREATE TABLE setting (ID INTEGER PRIMARY KEY,changColor NVARCHAR(10),HISTORY NVARCHAR(2),NOT NVARCHAR(2),FINISH NVARCHAR(2),ACHIEVEMENT NVARCHAR(2)");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        queryData("CREATE TABLE SINHVIEN (ID INTEGER PRIMARY KEY,NAME VARCHAR(100)");
    }

    public Cursor getData(String sql) {
        Cursor c=null;
        try {
            SQLiteDatabase db = getWritableDatabase();
             c = db.rawQuery(sql, null);
        }catch (Exception ex){
            Log.d("Get data error ", ex.toString());
        }
        return c;
    }

    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        }catch (Exception ex){
            Log.d("Query data error ", ex.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
