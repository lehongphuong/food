package xmart.com.xmart_android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


public class ProcessDao extends AbstractDao<Process, String> {

    public static final String TABLENAME = "PROCESS";

    /**
     * Properties of entity Process.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "Id", true, "ID");
        public final static Property UserId = new Property(1, String.class, "UserId", false, "USER_ID");
        public final static Property OwnerId = new Property(2, String.class, "OwnerId", false, "OWNER_ID");
        public final static Property Total = new Property(3, String.class, "Total", false, "TOTAL");
        public final static Property Status = new Property(4, String.class, "Status", false, "STATUS");
        public final static Property Ordered = new Property(5, String.class, "Ordered", false, "ORDERED");
        public final static Property Processed = new Property(6, String.class, "Processed", false, "PROCESSED");
        public final static Property Cancelled = new Property(7, String.class, "Cancelled", false, "CANCELLED");
        public final static Property Completed = new Property(8, String.class, "Completed", false, "COMPLETED");
        public final static Property ShopName = new Property(9, String.class, "ShopName", false, "SHOP_NAME");
    };


    public ProcessDao(DaoConfig config) {
        super(config);
    }

    public ProcessDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROCESS\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: Id
                "\"USER_ID\" TEXT," + // 1: UserId
                "\"OWNER_ID\" TEXT," + // 2: OwnerId
                "\"TOTAL\" TEXT," + // 3: Total
                "\"STATUS\" TEXT," + // 4: Status
                "\"ORDERED\" TEXT," + // 5: Ordered
                "\"PROCESSED\" TEXT," + // 6: Processed
                "\"CANCELLED\" TEXT," + // 7: Cancelled
                "\"COMPLETED\" TEXT," + // 8: Completed
                "\"SHOP_NAME\" TEXT);"); // 9: ShopName
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROCESS\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, xmart.com.xmart_android.db.Process entity) {
        stmt.clearBindings();

        String Id = entity.getId();
        if (Id != null) {
            stmt.bindString(1, Id);
        }

        String UserId = entity.getUserId();
        if (UserId != null) {
            stmt.bindString(2, UserId);
        }

        String OwnerId = entity.getOwnerId();
        if (OwnerId != null) {
            stmt.bindString(3, OwnerId);
        }

        String Total = entity.getTotal();
        if (Total != null) {
            stmt.bindString(4, Total);
        }

        String Status = entity.getStatus();
        if (Status != null) {
            stmt.bindString(5, Status);
        }

        String Ordered = entity.getOrdered();
        if (Ordered != null) {
            stmt.bindString(6, Ordered);
        }

        String Processed = entity.getProcessed();
        if (Processed != null) {
            stmt.bindString(7, Processed);
        }

        String Cancelled = entity.getCancelled();
        if (Cancelled != null) {
            stmt.bindString(8, Cancelled);
        }

        String Completed = entity.getCompleted();
        if (Completed != null) {
            stmt.bindString(9, Completed);
        }

        String ShopName = entity.getShopName();
        if (ShopName != null) {
            stmt.bindString(10, ShopName);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public xmart.com.xmart_android.db.Process readEntity(Cursor cursor, int offset) {
        xmart.com.xmart_android.db.Process entity = new xmart.com.xmart_android.db.Process( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // UserId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // OwnerId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Total
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Status
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Ordered
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // Processed
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // Cancelled
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // Completed
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // ShopName
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, xmart.com.xmart_android.db.Process entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOwnerId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTotal(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStatus(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOrdered(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setProcessed(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCancelled(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCompleted(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setShopName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }

    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(xmart.com.xmart_android.db.Process entity, long rowId) {
        return entity.getId();
    }

    /** @inheritdoc */
    @Override
    public String getKey(xmart.com.xmart_android.db.Process entity) {
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
