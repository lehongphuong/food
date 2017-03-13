package xmart.com.xmart_android.data;

/**
 * Created by LehongphuongCntt on 2/19/2017.
 */

public class ItemSP {
    private String image;
    private String name;
    private Integer priceNew;
    private Integer oldPrice;

    public ItemSP(){

    }

    public ItemSP(String image, String name, Integer priceNew, Integer oldPrice) {
        this.image = image;
        this.name = name;
        this.priceNew = priceNew;
        this.oldPrice = oldPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(Integer priceNew) {
        this.priceNew = priceNew;
    }

    public Integer getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Integer oldPrice) {
        this.oldPrice = oldPrice;
    }
}
