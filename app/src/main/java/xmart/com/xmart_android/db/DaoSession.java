package xmart.com.xmart_android.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig nguoiDungDaoConfig;
    private final DaoConfig orderDaoConfig;
    private final DaoConfig processDaoConfig;
    private final DaoConfig cancelDaoConfig;
    private final DaoConfig completeDaoConfig;
    private final DaoConfig cartItemDaoConfig;
    private final DaoConfig infoDaoConfig;
    private final DaoConfig categoriesDaoConfig;
    private final DaoConfig productNewDaoConfig;
    private final DaoConfig ownerDaoConfig;

    private final NguoiDungDao nguoiDungDao;
    private final OrderDao orderDao;
    private final ProcessDao processDao;
    private final CancelDao cancelDao;
    private final CompleteDao completeDao;
    private final CartItemDao cartItemDao;
    private final InfoDao infoDao;
    private final CategoriesDao categoriesDao;
    private final ProductNewDao productNewDao;
    private final OwnerDao ownerDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        nguoiDungDaoConfig = daoConfigMap.get(NguoiDungDao.class).clone();
        nguoiDungDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        processDaoConfig = daoConfigMap.get(ProcessDao.class).clone();
        processDaoConfig.initIdentityScope(type);

        cancelDaoConfig = daoConfigMap.get(CancelDao.class).clone();
        cancelDaoConfig.initIdentityScope(type);

        completeDaoConfig = daoConfigMap.get(CompleteDao.class).clone();
        completeDaoConfig.initIdentityScope(type);

        cartItemDaoConfig = daoConfigMap.get(CartItemDao.class).clone();
        cartItemDaoConfig.initIdentityScope(type);

        infoDaoConfig = daoConfigMap.get(InfoDao.class).clone();
        infoDaoConfig.initIdentityScope(type);

        categoriesDaoConfig = daoConfigMap.get(CategoriesDao.class).clone();
        categoriesDaoConfig.initIdentityScope(type);

        productNewDaoConfig = daoConfigMap.get(ProductNewDao.class).clone();
        productNewDaoConfig.initIdentityScope(type);

        ownerDaoConfig = daoConfigMap.get(OwnerDao.class).clone();
        ownerDaoConfig.initIdentityScope(type);

        nguoiDungDao = new NguoiDungDao(nguoiDungDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);
        processDao = new ProcessDao(processDaoConfig, this);
        cancelDao = new CancelDao(cancelDaoConfig, this);
        completeDao = new CompleteDao(completeDaoConfig, this);
        cartItemDao = new CartItemDao(cartItemDaoConfig, this);
        infoDao = new InfoDao(infoDaoConfig, this);
        categoriesDao = new CategoriesDao(categoriesDaoConfig, this);
        productNewDao = new ProductNewDao(productNewDaoConfig, this);
        ownerDao = new OwnerDao(ownerDaoConfig, this);

        registerDao(NguoiDung.class, nguoiDungDao);
        registerDao(Order.class, orderDao);
        registerDao(Process.class, processDao);
        registerDao(Cancel.class, cancelDao);
        registerDao(Complete.class, completeDao);
        registerDao(CartItem.class, cartItemDao);
        registerDao(Info.class, infoDao);
        registerDao(Categories.class, categoriesDao);
        registerDao(ProductNew.class, productNewDao);
        registerDao(Owner.class, ownerDao);
    }
    
    public void clear() {
        nguoiDungDaoConfig.getIdentityScope().clear();
        orderDaoConfig.getIdentityScope().clear();
        processDaoConfig.getIdentityScope().clear();
        cancelDaoConfig.getIdentityScope().clear();
        completeDaoConfig.getIdentityScope().clear();
        cartItemDaoConfig.getIdentityScope().clear();
        infoDaoConfig.getIdentityScope().clear();
        categoriesDaoConfig.getIdentityScope().clear();
        productNewDaoConfig.getIdentityScope().clear();
        ownerDaoConfig.getIdentityScope().clear();
    }

    public NguoiDungDao getNguoiDungDao() {
        return nguoiDungDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProcessDao getProcessDao() {
        return processDao;
    }

    public CancelDao getCancelDao() {
        return cancelDao;
    }

    public CompleteDao getCompleteDao() {
        return completeDao;
    }

    public CartItemDao getCartItemDao() {
        return cartItemDao;
    }

    public InfoDao getInfoDao() {
        return infoDao;
    }

    public CategoriesDao getCategoriesDao() {
        return categoriesDao;
    }

    public ProductNewDao getProductNewDao() {
        return productNewDao;
    }

    public OwnerDao getOwnerDao() {
        return ownerDao;
    }

}
