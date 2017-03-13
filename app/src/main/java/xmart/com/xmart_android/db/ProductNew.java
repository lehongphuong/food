package xmart.com.xmart_android.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "PRODUCT_NEW".
 */
public class ProductNew {

    private String Id;
    private String OwnerId;
    private String SKU;
    private String Name;
    private String Price;
    private String Desc;
    private String UnitId;
    private String UnitName;
    private String CategoryId;
    private String CategoryName;
    private String Expired;
    private String Stock;
    private String Image;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ProductNew() {
    }

    public ProductNew(String Id) {
        this.Id = Id;
    }

    public ProductNew(String Id, String OwnerId, String SKU, String Name, String Price, String Desc, String UnitId, String UnitName, String CategoryId, String CategoryName, String Expired, String Stock, String Image) {
        this.Id = Id;
        this.OwnerId = OwnerId;
        this.SKU = SKU;
        this.Name = Name;
        this.Price = Price;
        this.Desc = Desc;
        this.UnitId = UnitId;
        this.UnitName = UnitName;
        this.CategoryId = CategoryId;
        this.CategoryName = CategoryName;
        this.Expired = Expired;
        this.Stock = Stock;
        this.Image = Image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String OwnerId) {
        this.OwnerId = OwnerId;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String UnitId) {
        this.UnitId = UnitId;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this.CategoryId = CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public String getExpired() {
        return Expired;
    }

    public void setExpired(String Expired) {
        this.Expired = Expired;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String Stock) {
        this.Stock = Stock;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}