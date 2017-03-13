package xmart.com.xmart_android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "PRODUCT_NEW".
*/
public class ProductNewDao extends AbstractDao<ProductNew, String> {

    public static final String TABLENAME = "PRODUCT_NEW";

    /**
     * Properties of entity ProductNew.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "Id", true, "ID");
        public final static Property OwnerId = new Property(1, String.class, "OwnerId", false, "OWNER_ID");
        public final static Property SKU = new Property(2, String.class, "SKU", false, "SKU");
        public final static Property Name = new Property(3, String.class, "Name", false, "NAME");
        public final static Property Price = new Property(4, String.class, "Price", false, "PRICE");
        public final static Property Desc = new Property(5, String.class, "Desc", false, "DESC");
        public final static Property UnitId = new Property(6, String.class, "UnitId", false, "UNIT_ID");
        public final static Property UnitName = new Property(7, String.class, "UnitName", false, "UNIT_NAME");
        public final static Property CategoryId = new Property(8, String.class, "CategoryId", false, "CATEGORY_ID");
        public final static Property CategoryName = new Property(9, String.class, "CategoryName", false, "CATEGORY_NAME");
        public final static Property Expired = new Property(10, String.class, "Expired", false, "EXPIRED");
        public final static Property Stock = new Property(11, String.class, "Stock", false, "STOCK");
        public final static Property Image = new Property(12, String.class, "Image", false, "IMAGE");
    };


    public ProductNewDao(DaoConfig config) {
        super(config);
    }
    
    public ProductNewDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PRODUCT_NEW\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: Id
                "\"OWNER_ID\" TEXT," + // 1: OwnerId
                "\"SKU\" TEXT," + // 2: SKU
                "\"NAME\" TEXT," + // 3: Name
                "\"PRICE\" TEXT," + // 4: Price
                "\"DESC\" TEXT," + // 5: Desc
                "\"UNIT_ID\" TEXT," + // 6: UnitId
                "\"UNIT_NAME\" TEXT," + // 7: UnitName
                "\"CATEGORY_ID\" TEXT," + // 8: CategoryId
                "\"CATEGORY_NAME\" TEXT," + // 9: CategoryName
                "\"EXPIRED\" TEXT," + // 10: Expired
                "\"STOCK\" TEXT," + // 11: Stock
                "\"IMAGE\" TEXT);"); // 12: Image
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PRODUCT_NEW\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ProductNew entity) {
        stmt.clearBindings();
 
        String Id = entity.getId();
        if (Id != null) {
            stmt.bindString(1, Id);
        }
 
        String OwnerId = entity.getOwnerId();
        if (OwnerId != null) {
            stmt.bindString(2, OwnerId);
        }
 
        String SKU = entity.getSKU();
        if (SKU != null) {
            stmt.bindString(3, SKU);
        }
 
        String Name = entity.getName();
        if (Name != null) {
            stmt.bindString(4, Name);
        }
 
        String Price = entity.getPrice();
        if (Price != null) {
            stmt.bindString(5, Price);
        }
 
        String Desc = entity.getDesc();
        if (Desc != null) {
            stmt.bindString(6, Desc);
        }
 
        String UnitId = entity.getUnitId();
        if (UnitId != null) {
            stmt.bindString(7, UnitId);
        }
 
        String UnitName = entity.getUnitName();
        if (UnitName != null) {
            stmt.bindString(8, UnitName);
        }
 
        String CategoryId = entity.getCategoryId();
        if (CategoryId != null) {
            stmt.bindString(9, CategoryId);
        }
 
        String CategoryName = entity.getCategoryName();
        if (CategoryName != null) {
            stmt.bindString(10, CategoryName);
        }
 
        String Expired = entity.getExpired();
        if (Expired != null) {
            stmt.bindString(11, Expired);
        }
 
        String Stock = entity.getStock();
        if (Stock != null) {
            stmt.bindString(12, Stock);
        }
 
        String Image = entity.getImage();
        if (Image != null) {
            stmt.bindString(13, Image);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ProductNew readEntity(Cursor cursor, int offset) {
        ProductNew entity = new ProductNew( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // OwnerId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // SKU
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Price
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Desc
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // UnitId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // UnitName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // CategoryId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // CategoryName
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // Expired
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // Stock
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // Image
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ProductNew entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setOwnerId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSKU(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPrice(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDesc(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUnitId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUnitName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCategoryId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCategoryName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setExpired(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setStock(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setImage(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(ProductNew entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(ProductNew entity) {
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