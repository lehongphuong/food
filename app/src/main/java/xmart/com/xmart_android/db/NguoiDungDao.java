package xmart.com.xmart_android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "NGUOI_DUNG".
*/
public class NguoiDungDao extends AbstractDao<NguoiDung, String> {

    public static final String TABLENAME = "NGUOI_DUNG";

    /**
     * Properties of entity NguoiDung.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "Id", true, "ID");
        public final static Property UserName = new Property(1, String.class, "UserName", false, "USER_NAME");
        public final static Property Token = new Property(2, String.class, "Token", false, "TOKEN");
        public final static Property FirstName = new Property(3, String.class, "FirstName", false, "FIRST_NAME");
        public final static Property LastName = new Property(4, String.class, "LastName", false, "LAST_NAME");
        public final static Property PhoneNumber = new Property(5, String.class, "PhoneNumber", false, "PHONE_NUMBER");
        public final static Property Birthday = new Property(6, String.class, "Birthday", false, "BIRTHDAY");
        public final static Property Gender = new Property(7, String.class, "Gender", false, "GENDER");
        public final static Property Image = new Property(8, String.class, "Image", false, "IMAGE");
        public final static Property HomeAddr = new Property(9, String.class, "HomeAddr", false, "HOME_ADDR");
        public final static Property HomeLatitude = new Property(10, String.class, "HomeLatitude", false, "HOME_LATITUDE");
        public final static Property HomeLongitude = new Property(11, String.class, "HomeLongitude", false, "HOME_LONGITUDE");
        public final static Property WorkAddr = new Property(12, String.class, "WorkAddr", false, "WORK_ADDR");
        public final static Property WorkLatitude = new Property(13, String.class, "WorkLatitude", false, "WORK_LATITUDE");
        public final static Property WorkLongitude = new Property(14, String.class, "WorkLongitude", false, "WORK_LONGITUDE");
        public final static Property Point = new Property(15, String.class, "Point", false, "POINT");
    };


    public NguoiDungDao(DaoConfig config) {
        super(config);
    }
    
    public NguoiDungDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NGUOI_DUNG\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: Id
                "\"USER_NAME\" TEXT," + // 1: UserName
                "\"TOKEN\" TEXT," + // 2: Token
                "\"FIRST_NAME\" TEXT," + // 3: FirstName
                "\"LAST_NAME\" TEXT," + // 4: LastName
                "\"PHONE_NUMBER\" TEXT," + // 5: PhoneNumber
                "\"BIRTHDAY\" TEXT," + // 6: Birthday
                "\"GENDER\" TEXT," + // 7: Gender
                "\"IMAGE\" TEXT," + // 8: Image
                "\"HOME_ADDR\" TEXT," + // 9: HomeAddr
                "\"HOME_LATITUDE\" TEXT," + // 10: HomeLatitude
                "\"HOME_LONGITUDE\" TEXT," + // 11: HomeLongitude
                "\"WORK_ADDR\" TEXT," + // 12: WorkAddr
                "\"WORK_LATITUDE\" TEXT," + // 13: WorkLatitude
                "\"WORK_LONGITUDE\" TEXT," + // 14: WorkLongitude
                "\"POINT\" TEXT);"); // 15: Point
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NGUOI_DUNG\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NguoiDung entity) {
        stmt.clearBindings();
 
        String Id = entity.getId();
        if (Id != null) {
            stmt.bindString(1, Id);
        }
 
        String UserName = entity.getUserName();
        if (UserName != null) {
            stmt.bindString(2, UserName);
        }
 
        String Token = entity.getToken();
        if (Token != null) {
            stmt.bindString(3, Token);
        }
 
        String FirstName = entity.getFirstName();
        if (FirstName != null) {
            stmt.bindString(4, FirstName);
        }
 
        String LastName = entity.getLastName();
        if (LastName != null) {
            stmt.bindString(5, LastName);
        }
 
        String PhoneNumber = entity.getPhoneNumber();
        if (PhoneNumber != null) {
            stmt.bindString(6, PhoneNumber);
        }
 
        String Birthday = entity.getBirthday();
        if (Birthday != null) {
            stmt.bindString(7, Birthday);
        }
 
        String Gender = entity.getGender();
        if (Gender != null) {
            stmt.bindString(8, Gender);
        }
 
        String Image = entity.getImage();
        if (Image != null) {
            stmt.bindString(9, Image);
        }
 
        String HomeAddr = entity.getHomeAddr();
        if (HomeAddr != null) {
            stmt.bindString(10, HomeAddr);
        }
 
        String HomeLatitude = entity.getHomeLatitude();
        if (HomeLatitude != null) {
            stmt.bindString(11, HomeLatitude);
        }
 
        String HomeLongitude = entity.getHomeLongitude();
        if (HomeLongitude != null) {
            stmt.bindString(12, HomeLongitude);
        }
 
        String WorkAddr = entity.getWorkAddr();
        if (WorkAddr != null) {
            stmt.bindString(13, WorkAddr);
        }
 
        String WorkLatitude = entity.getWorkLatitude();
        if (WorkLatitude != null) {
            stmt.bindString(14, WorkLatitude);
        }
 
        String WorkLongitude = entity.getWorkLongitude();
        if (WorkLongitude != null) {
            stmt.bindString(15, WorkLongitude);
        }
 
        String Point = entity.getPoint();
        if (Point != null) {
            stmt.bindString(16, Point);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public NguoiDung readEntity(Cursor cursor, int offset) {
        NguoiDung entity = new NguoiDung( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // UserName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Token
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // FirstName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // LastName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // PhoneNumber
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // Birthday
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // Gender
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // Image
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // HomeAddr
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // HomeLatitude
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // HomeLongitude
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // WorkAddr
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // WorkLatitude
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // WorkLongitude
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // Point
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NguoiDung entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setToken(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFirstName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLastName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPhoneNumber(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBirthday(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGender(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setImage(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setHomeAddr(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setHomeLatitude(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setHomeLongitude(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setWorkAddr(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setWorkLatitude(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setWorkLongitude(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setPoint(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(NguoiDung entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(NguoiDung entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
